/**
 * 壹钱包 Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.bill.test;

import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import sun.misc.Cleaner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.AbstractMap.SimpleEntry;
import java.util.*;

import static com.rust.bill.test.BillStyleConstants.DETAIL_COLUMN;
import static com.rust.bill.test.BillStyleConstants.TOTAL_COLUMN;
import static com.rust.bill.test.DateUtil.PATTERN_FULL_5;
import static com.rust.bill.test.DateUtil.PATTERN_YYYY_MM_DD_3;
import static java.math.BigDecimal.ZERO;

/**
 * @author FUTANGHANG004
 * @version $Id: Bill25FileGenerateServiceImpl, v 0.1 2018/3/28 FUTANGHANG004 Exp $
 */
public class Bill25FileGenerateServiceImpl extends AbstractGenerateFileServiceV2<Bill25> {

    public static final String DOT = File.separator;


    @Override
    protected List<File> getFilterFiles(final Bill25 form) throws Exception {

        // FIXME: 2018/3/29 for test
        String rootPath = System.getProperty("user.home") + DOT + "nas" + DOT;

        // String rootPath =
        // ApplicationContextConfig.get(MspConstants.MSP_PLATFORM_MERCHANT_SETTLEMENT_BILL_PATH);

        File rootFile = new File(rootPath);

        if (!rootFile.exists() || !rootFile.isDirectory()) {
            throw new RuntimeException("OPS配置的清结算筛选文件路径不存在,msp_platform_merchant_settlement_bill_dir");
        }

        List<File> rootFiles = getRootFiles(form, rootPath);
        System.out.println(("根据时间筛选出的清结算系统对账单目录:" + rootFiles));

        List<File> files = filterFiles(form, rootFiles, new ArrayList<File>());


        return files;
    }

    private List<File> filterFiles(final Bill25 bill25, List<File> rootFiles, final List<File> files) {
        for (File rootFile : rootFiles) {
            File[] files1 = rootFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        filterFiles(bill25, pathname, files);
                    } else {
                        return pathname.getParentFile().isDirectory() &&
                                pathname.getParentFile().getName().compareTo(bill25.getStartDate()) > -1 &&
                                pathname.getParentFile().getName().compareTo(bill25.getEndDate()) < 1 &&
                                pathname.getName().endsWith(".txt") &&
                                pathname.getName().startsWith(bill25.getMerchantNo());
                    }
                    return false;
                }

            });
            if (files1 != null) {
                files.addAll(Arrays.asList(files1));
            }
        }
        return files;
    }

    private List<File> filterFiles(final Bill25 bill25, File rootPath, final List<File> fileList) {
        rootPath.listFiles(new FileFilter() {
            public boolean accept(File dir) {
                if (dir.isDirectory()) {
                    filterFiles(bill25, dir, fileList);
                } else {
                    if (dir.getParentFile().isDirectory() &&
                            dir.getParentFile().getName().compareTo(bill25.getStartDate()) > -1 &&
                            dir.getParentFile().getName().compareTo(bill25.getEndDate()) < 1 &&
                            dir.getName().endsWith(".txt") &&
                            dir.getName().startsWith(bill25.getMerchantNo())) {
                        fileList.add(dir);
                    }
                }
                return true;
            }
        });
        return fileList;


    }
/*    private FluentIterable<File> filterFiles(final Bill25 form, String rootPath) {
        return Files.fileTreeTraverser().breadthFirstTraversal(new File(rootPath)).filter(new Predicate<File>() {
            public boolean apply(File input) {
                return input.isFile() && input.getParentFile().isDirectory() && input.getParentFile().getName().compareTo(form.getStartDate()) > -1
                        && input.getParentFile().getName().compareTo(form.getEndDate()) < 1 && input.getName().endsWith(".txt") && input.getName().contains(form.getMerchantNo());
            }

        });
    }*/


    @Override
    protected SimpleEntry<Integer, List<File>> filterDataAndWriteCvs(Iterator<File> fileIterator, Bill25 form, File destFile, int upperLimit) throws Exception {

        int allocate = 200000;

        int tempRow = 0;

        int totalRow = 0;

        int suffix = -1;

        List<File> files = new ArrayList<File>(Collections.singleton(destFile));


        RandomAccessFile raf = new RandomAccessFile(destFile, "rw");


        BigDecimal totalFeeAmt = ZERO;
        BigDecimal totalOrderAmt = ZERO;

        reserveSpace(raf);

        writeRaf(raf, Arrays.toString(DETAIL_COLUMN).substring(1, Arrays.toString(DETAIL_COLUMN).length() - 1), false);
        newLine(raf);

        try {

            while (fileIterator.hasNext()) {
                RandomAccessFile fis = new RandomAccessFile(fileIterator.next(), "rw");
                FileChannel channel = fis.getChannel();

                String enterStr = "\n";
                StringBuilder strBud = new StringBuilder("");
                // 构建一个只读的MappedByteBuffer
                final MappedByteBuffer mappedByteBuffer = channel.map(MapMode.PRIVATE, 0, allocate);
                byte[] bs = new byte[allocate];
                while (channel.read(mappedByteBuffer) != -1) {
                    //当前rBuffer的指针位置
                    int rSize = mappedByteBuffer.position();
                    //把position设为0，mark设为-1，不改变limit的值
                    mappedByteBuffer.rewind();
                    //从position=0的位置开始相对读，读bs.length(bufSize)个byte，并写入dst下标从offset(0)到offset+length的区域
                    mappedByteBuffer.get(bs);
                    //position = 0;limit = capacity;mark = -1;  有点初始化的味道，但是并不影响底层byte数组的内容
                    mappedByteBuffer.clear();
                    String tempString = new String(bs, 0, rSize);

                    int fromIndex = 0;
                    int endIndex = 0;
                    while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
                        //one line
                        String line = tempString.substring(fromIndex, endIndex);
                        line = strBud.toString() + line;
                        if (StringUtils.isNotEmpty(line)) {
                            if (line.trim().startsWith("I") || line.trim().startsWith("O")) {
                                String[] temp = line.trim().replace("\t", "").split(",");
                                // if ("100040090000".equals(temp[SettleBillEnum.bussinessScense.ordinal()])) {
                                    ++tempRow;
                                    try {


                                        // 商户号
                                        writeRaf(raf, CHAR_TAB + form.getMerchantNo());
                                        //结算日期
                                        writeRaf(raf, temp[SettleBillEnum.settleDate.ordinal()]);
                                        //商户订单号
                                        writeRaf(raf, CHAR_TAB + temp[SettleBillEnum.merchantOrderNo.ordinal()]);

                                        String yqbTxnSsn = temp[SettleBillEnum.transCoreTxnSsn.ordinal()];
                                        // TODO: Rust 2018/4/1 添加搜索方法，暂时写死
                                        String checkoutOrderNo = "123123123";

                                        //收单订单号
                                        writeRaf(raf, CHAR_TAB + checkoutOrderNo);
                                        //YQB交易流水号
                                        writeRaf(raf, yqbTxnSsn);
                                        //交易时间
                                        writeRaf(raf, CHAR_TAB + temp[SettleBillEnum.transDateTime.ordinal()]);

                                        BigDecimal orderAmt = DecimalUtil.format2BigDecimal(temp[SettleBillEnum.incomeAmt.ordinal()]);
                                        // 手续费
                                        BigDecimal feeAmt = ZERO;
                                        // 结算金额
                                        String settleAmt = "0.00";
                                        // if ("62".equals(temp[SettleBillEnum.txnType.ordinal()]) ||
                                        //         "L1".equals(temp[SettleBillEnum.txnType.ordinal()]) ||
                                        //         "I1".equals(temp[SettleBillEnum.txnType.ordinal()])) {
                                            //	如果是正交易
                                            //手续费=支出金额
                                            feeAmt = DecimalUtil.format2BigDecimal(temp[SettleBillEnum.amountPayout.ordinal()]);
                                            //结算金额=参与结算金额-支出金额
                                            settleAmt = StringUtil.subtract(temp[SettleBillEnum.participateSettlementAmount.ordinal()], temp[SettleBillEnum.amountPayout.ordinal()]);
                                        // } else if ("38".equals(temp[SettleBillEnum.txnType.ordinal()]) ||
                                        //         "L2".equals(temp[SettleBillEnum.txnType.ordinal()]) ||
                                        //         "I2".equals(temp[SettleBillEnum.txnType.ordinal()])) {
                                        //     // 如果是反交易
                                        //     // 手续费=返还手续费
                                        //     feeAmt = DecimalUtil.format2BigDecimal(temp[SettleBillEnum.returnFee.ordinal()]);
                                        //     // 结算金额=参与结算金额-返还手续费
                                        //     settleAmt = StringUtil.subtract(temp[SettleBillEnum.participateSettlementAmount.ordinal()], temp[SettleBillEnum.returnFee.ordinal()]);
                                        // }

/*                                 // 手续费=支出金额(settleBillBeans.get(i).getAmountPayout())-返还手续费(settleBillBeans.get(i).getReturnFee())
                                    BigDecimal feeAmt = DecimalUtil.subtract(temp[SettleBillEnum.amountPayout.ordinal()], temp[SettleBillEnum.returnFee.ordinal()]);
                                    //结算金额=参与结算金额-手续费
                                    String settleAmt = StringUtil.subtract(temp[4], feeAmt);
                                    // 订单金额=手续费+结算金额
                                    BigDecimal orderAmt = DecimalUtil.add(feeAmt, settleAmt);*/

                                        writeRaf(raf, orderAmt.toString());
                                        writeRaf(raf, feeAmt.toString());
                                        writeRaf(raf, settleAmt, false);
                                        totalFeeAmt = totalFeeAmt.add(feeAmt);
                                        totalOrderAmt = totalOrderAmt.add(orderAmt);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    newLine(raf);
                                    //    一行写完
                                    //    明细行数等于上限值
                                    if (tempRow == upperLimit) {
                                        //    写入头部数据
                                        raf.seek(0L);

                                        // writeRaf(raf, "\r\n");
                                        writeRaf(raf, "                         合并支付结算单", false);
                                        newLine(raf);
                                        writeRaf(raf, Arrays.toString(TOTAL_COLUMN).substring(1, Arrays.toString(TOTAL_COLUMN).length() - 1), false);
                                        newLine(raf);
                                        writeRaf(raf, CHAR_TAB + form.getMerchantNo());
                                        writeRaf(raf, DateUtil.getDateTimeByString6(form.getStartDate()));
                                        writeRaf(raf, DateUtil.getDateTimeByString6(form.getEndDate()));
                                        writeRaf(raf, "元");
                                        writeRaf(raf, "人民币");
                                        writeRaf(raf, totalOrderAmt.toString());
                                        writeRaf(raf, totalFeeAmt.toString());
                                        writeRaf(raf, DateUtil.getCurrentDateTime(PATTERN_FULL_5));
                                        //关闭现输出流
                                        raf.close();
                                        //更换输出流
                                        File file = new File(destFile.getPath().replaceAll("[.][^.]+$", "") + CHAR_UNDERLINE + ++suffix + ".csv");
                                        raf = new RandomAccessFile(file, "rw");
                                        //总计数归0
                                        totalOrderAmt = ZERO;
                                        totalFeeAmt = ZERO;
                                        //加入生成文件集合
                                        files.add(file);
                                        //预留头部位置
                                        reserveSpace(raf);
                                        //写入明细列
                                        writeRaf(raf, Arrays.toString(DETAIL_COLUMN).substring(1, Arrays.toString(DETAIL_COLUMN).length() - 1), false);
                                        newLine(raf);
                                        //纳入总计数
                                        totalRow += tempRow;
                                        //重置行数
                                        tempRow = 0;
                                    }
                                // }
                            }
                        }

                        strBud.delete(0, strBud.length());
                        fromIndex = endIndex + 1;
                    }
                    if (rSize > tempString.length()) {
                        strBud.append(tempString, fromIndex, tempString.length());
                    } else {
                        strBud.append(tempString, fromIndex, rSize);
                    }
                }

                // 关闭通道和文件流
                channel.close();
                fis.close();

                unMap(mappedByteBuffer);


            }
        } catch (IOException e) {
            throw new RuntimeException("genFinalSettleBean process fail:", e);
        }

        //向头部写入总计
        raf.seek(0L);

        // writeRaf(raf, "\r\n");
        writeRaf(raf, "                         合并支付结算单", false);
        newLine(raf);
        writeRaf(raf, Arrays.toString(TOTAL_COLUMN).substring(1, Arrays.toString(TOTAL_COLUMN).length() - 1), false);
        newLine(raf);
        writeRaf(raf, CHAR_TAB + form.getMerchantNo());
        writeRaf(raf, DateUtil.getDateTimeByString6(form.getStartDate()));
        writeRaf(raf, DateUtil.getDateTimeByString6(form.getEndDate()));
        writeRaf(raf, "元");
        writeRaf(raf, "人民币");
        writeRaf(raf, totalOrderAmt.toString());
        writeRaf(raf, totalFeeAmt.toString());
        writeRaf(raf, DateUtil.getCurrentDateTime(PATTERN_FULL_5));
        raf.close();
        System.out.println("Bill25FileGenerateServiceImpl.filterDataAndWriteCvs" + (totalRow + tempRow));
        return new SimpleEntry<Integer, List<File>>((totalRow + tempRow), files);
    }

    private void newLine(RandomAccessFile raf) throws IOException {
        writeRaf(raf, "\n", false);
    }

    private void reserveSpace(RandomAccessFile raf) throws IOException {
        writeRaf(raf, "    ,    ,    ,    ,    ");
        newLine(raf);
        writeRaf(raf, "    ,    ,    ,    ,    ,    ,    ,    ");
        newLine(raf);
        writeRaf(raf, "    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ");
        newLine(raf);
        writeRaf(raf, "    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,        ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ,    ");
        newLine(raf);
    }

    private void unMap(final MappedByteBuffer mappedByteBuffer) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                try {
                    Method method = mappedByteBuffer.getClass().getMethod("cleaner");
                    method.setAccessible(true);
                    Cleaner cleaner = (Cleaner) method.invoke(mappedByteBuffer, new Object[0]);
                    cleaner.clean();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }


    @Override
    public Bill25 getForm(MspBillDownloadRecordDTO dto) {
/*    String queryForm = dto.getSearchCondition();

    Bill25 bill25 = null;
    try {
        bill25 = JSONObject.parseObject(queryForm, Bill25.class);
    } catch (Exception e) {
      throw new RuntimeException("bill download data wrong ,searchCondition can not transform 2 BIll25!", e);
    }

    if (bill25 == null) {
      throw new RuntimeException("bill download data wrong ,bill25 is null!");
    }
    bill25.setMerchantNo(dto.getMerchantNo());
      bill25.setBillType("25");

    if (StringUtils.isEmpty(bill25.getStartDate()) || StringUtils.isEmpty(bill25.getEndDate())) {
      throw new RuntimeException("bill download data wrong ,bill25 startDate or endDate is null!");
    }*/
        Bill25 bill25 = new Bill25();
        bill25.setBillType("25");
        bill25.setMerchantNo("900000164395");
        bill25.setStartDate("20180101");
        bill25.setEndDate("20180130");
        bill25.setNeedZip(true);

        return bill25;
    }

    @Override
    public String validForm(Bill25 bill) {
        StringBuilder errorMSg = new StringBuilder();
        if (StringUtils.isEmpty(bill.getMerchantNo())) {
            errorMSg.append("商户号为空;");
        }
        if (StringUtils.isEmpty(bill.getBillType())) {
            errorMSg.append("账单类型为空;");
        }
        if (StringUtils.isEmpty(bill.getStartDate())) {
            errorMSg.append("开始时间为空;");
        }
        if (StringUtils.isEmpty(bill.getEndDate())) {
            errorMSg.append("结束时间为空;");
        }
        try {
            Date startDate = getStartDate(bill);
            Date endDate = getEndDate(bill);
            if (startDate.after(endDate)) {
                errorMSg.append("开始时间不能晚于结束时间;");
            }
        } catch (Exception e) {
            errorMSg.append("开始时间或结束时间格式不正确！");
        }
        return errorMSg.toString();
    }

    @Override
    public Date getStartDate(Bill25 bill) {
        return DateUtil.getDateTimeByString3(bill.getStartDate());
    }

    @Override
    public Date getEndDate(Bill25 bill) {
        return DateUtil.getDateTimeByString3(bill.getEndDate());
    }

    @Override
    public File genDestFile(Bill25 bill25) {

        String path = System.getProperty("user.home") + DOT + "nfsc" + DOT + "pafb_pspt_id948713_vol1" + DOT + "test.csv";

        String yyyyMMdd = DateUtil.getCurrentDateTime(PATTERN_YYYY_MM_DD_3);
        /*
         * String path; String nasPath =
         * ApplicationContextConfig.get(MspConstants.MSP_PLATFORM_MERCHANT_SETTLEMENT_BILL_PATH) + DOT +
         * "01_bill" + DOT + yyyyMMdd.substring(0,4) + DOT + yyyyMMdd.substring(4, 6) + DOT +
         * yyyyMMdd.substring(6) + DOT + bill25.getMerchantNo() + DOT; if (StringUtil.isBlank(nasPath)) {
         * throw new RuntimeException("getTargetFileName nasPath is blank!"); } path = nasPath + "bill" +
         * Constant.CHAR_UNDERLINE + MERGE_PAY_SETTLE_BILL.getCode() + Constant.CHAR_UNDERLINE +
         * MERGE_PAY_SETTLE_BILL.getDesc() + Constant.CHAR_UNDERLINE + bill25.getRecordId() + ".csv";
         */

        return new File(path);
    }

    /**
     * 根据开始时间和结束时间筛选文件目录
     *
     * @param bill25
     * @param rootPath
     * @return
     */
    private List<File> getRootFiles(Bill25 bill25, String rootPath) {
        List<File> rootFiles = new ArrayList<File>();
        Date startDate = Preconditions.checkNotNull(DateUtil.parseString2Date(bill25.getStartDate(), PATTERN_YYYY_MM_DD_3), "parseString2Date error");
        Date endDate = Preconditions.checkNotNull(DateUtil.parseString2Date(bill25.getEndDate(), PATTERN_YYYY_MM_DD_3), "parseString2Date error");

        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);
        while (startDate.getTime() <= endDate.getTime()) {
            File file = new File(rootPath + DOT + DateUtil.formatDate(tempStart.getTime(), PATTERN_YYYY_MM_DD_3));
            if (file.isDirectory() && file.exists()) {
                rootFiles.add(file);
            }
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            startDate = tempStart.getTime();
        }
        return rootFiles;
    }

}
