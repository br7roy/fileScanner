/**
 * 壹钱包 Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.bill.test;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import sun.misc.Cleaner;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
    protected FluentIterable<File> getFilterFiles(final Bill25 form) throws Exception {

        // FIXME: 2018/3/29 for test
        String rootPath = System.getProperty("user.home") + DOT + "nas" + DOT;

        // String rootPath =
        // ApplicationContextConfig.get(MspConstants.MSP_PLATFORM_MERCHANT_SETTLEMENT_BILL_PATH);

        FluentIterable<File> files = filterFiles(form, rootPath);


        return files;
    }

    private FluentIterable<File> filterFiles(final Bill25 form, String rootPath) {
        return Files.fileTreeTraverser().breadthFirstTraversal(new File(rootPath)).filter(new Predicate<File>() {
            public boolean apply(File input) {
                return input.isFile() && input.getParentFile().isDirectory() && input.getParentFile().getName().compareTo(form.getStartDate()) > -1
                        && input.getParentFile().getName().compareTo(form.getEndDate()) < 1 && input.getName().endsWith(".txt") && input.getName().contains(form.getMerchantNo());
            }

        });
    }


    @Override
    protected void filterDataAndWriteCvs(Iterator<File> fileIterator, Bill25 form, File destFile) throws Exception {
        int allocate = 20000;
        FileOutputStream fos = new FileOutputStream(destFile);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        BigDecimal totalFeeAmt = ZERO;
        BigDecimal totalOrderAmt = ZERO;


        bos.write(Arrays.toString(DETAIL_COLUMN).substring(1, Arrays.toString(DETAIL_COLUMN).length() - 1).getBytes("GBK"));
        bos.write("\r\n".getBytes("GBK"));
        do {
            try {
                RandomAccessFile fis = new RandomAccessFile(fileIterator.next(), "rw");
                FileChannel channel = fis.getChannel();

                String enterStr = "\n";
                StringBuilder strBuf = new StringBuilder("");
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
                        line = strBuf.toString() + line;
                        if (StringUtils.isNotEmpty(line)) {
                            if (line.trim().startsWith("I") || line.trim().startsWith("O")) {
                                try {
                                    String[] temp = line.trim().replace("\t", "").split(",");


                                    // 商户号
                                    write(bos, form.getMerchantNo());
                                    //结算日期
                                    write(bos, temp[8]);
                                    //商户订单号
                                    write(bos, temp[14]);

                                    String yqbTxnSsn = temp[0];
                                    // TODO: Rust 2018/4/1 添加搜索方法，暂时写死
                                    String merchantOrderNo = "123123123";

                                    //商户订单号
                                    write(bos, merchantOrderNo);
                                    //YQB交易流水号
                                    write(bos, yqbTxnSsn);
                                    //交易时间
                                    write(bos, temp[1]);
                                    // 订单金额=收入金额(settleBillBeans.get(i).getIncomeAmt())
                                    BigDecimal orderAmt = DecimalUtil.format2BigDecimal(temp[10]);
                                    write(bos, orderAmt.toString());
                                    // 手续费=收入金额(settleBillBeans.get(i).getIncomeAmt())-返还手续费(settleBillBeans.get(i).getReturnFee())
                                    BigDecimal feeAmt = DecimalUtil.subtract(temp[10], temp[12]);
                                    write(bos, feeAmt.toString());
                                    // 结算金额=参与结算金额(settleBillBeans.get(i).getParticipateSettlementAmount())+返还手续费(settleBillBeans.get(i).getReturnFee())-收入金额(settleBillBeans.get(i).getIncomeAmt())
                                    write(bos, StringUtil.mix(temp[4], temp[12], temp[10]), false);
                                    totalFeeAmt = totalFeeAmt.add(feeAmt);
                                    totalOrderAmt = totalOrderAmt.add(orderAmt);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                write(bos, "\r\n", false);
                            }
                        }

                        strBuf.delete(0, strBuf.length());
                        fromIndex = endIndex + 1;
                    }
                    if (rSize > tempString.length()) {
                        strBuf.append(tempString.substring(fromIndex, tempString.length()));
                    } else {
                        strBuf.append(tempString.substring(fromIndex, rSize));
                    }

                }

                // 关闭通道和文件流
                channel.close();
                fis.close();

                unMap(mappedByteBuffer);


            } catch (IOException e) {
                throw new RuntimeException("genFinalSettleBean process fail:", e);
            }

        } while (fileIterator.hasNext());

        //向头部写入总计
        write(bos, "\r\n");
        write(bos, Arrays.toString(TOTAL_COLUMN).substring(1, Arrays.toString(TOTAL_COLUMN).length() - 1));
        write(bos, "\r\n");
        write(bos, form.getMerchantNo());
        write(bos, form.getStartDate());
        write(bos, form.getEndDate());
        write(bos, "元");
        write(bos, "人民币");
        write(bos, totalOrderAmt.toString());
        write(bos, totalFeeAmt.toString());
        write(bos, DateUtil.getCurrentDateTime(PATTERN_FULL_5));
        bos.close();

    }

    private void unMap(final MappedByteBuffer mappedByteBuffer) {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    Method method = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
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


    protected List<DetailSettleBillBean> genDetailList(List<SettleBillBean> settleBillBeans, Bill25 bill) {
        // TODO: Rust 2018/3/29 遍历 ,通过壹钱包交易号查找收单订单号，公式运算
        List<DetailSettleBillBean> detailSettleBillBeans = Lists.newArrayList();
        for (int i = 0; i < settleBillBeans.size(); i++) {
            DetailSettleBillBean detailSettleBillBean = new DetailSettleBillBean();
            String coreTxnSsn = settleBillBeans.get(i).getTransCoreTxnSsn();
            if (StringUtils.isEmpty(coreTxnSsn)) {
                throw new IllegalArgumentException("data wrong coreTxnSsn can not be null !");
            }
            // TODO: 通过壹钱包交易号查找收单订单号
            // 获取收单订单号
            String yqbTxnSsn = settleBillBeans.get(i).getTransCoreTxnSsn();
            if (StringUtils.isEmpty(yqbTxnSsn)) {
                throw new RuntimeException("empty yqbTxnSsn");
            }

            String checkoutOrderNo = "testCheckOutNo";
            if (StringUtils.isEmpty(checkoutOrderNo)) {
                throw new RuntimeException("empty checkoutOrderNo");
            }

            detailSettleBillBean.setMerchantOrderNo(settleBillBeans.get(i).getMerchantOrderNo());
            detailSettleBillBean.setCheckOutOrderNo(checkoutOrderNo);
            detailSettleBillBean.setMerchantNo(bill.getMerchantNo());
            // 手续费=收入金额(settleBillBeans.get(i).getIncomeAmt())-返还手续费(settleBillBeans.get(i).getReturnFee())
            detailSettleBillBean.setFeeAmt(DecimalUtil.subtract(settleBillBeans.get(i).getIncomeAmt(), settleBillBeans.get(i).getReturnFee()));
            // 订单金额=收入金额(settleBillBeans.get(i).getIncomeAmt())
            detailSettleBillBean.setOrderAmt(DecimalUtil.format2BigDecimal(settleBillBeans.get(i).getIncomeAmt()));
            // 结算金额=参与结算金额(settleBillBeans.get(i).getParticipateSettlementAmount())+返还手续费(settleBillBeans.get(i).getReturnFee())-收入金额(settleBillBeans.get(i).getIncomeAmt())
            detailSettleBillBean.setSettleAmt(StringUtil.mix(settleBillBeans.get(i).getParticipateSettlementAmount(), settleBillBeans.get(i).getReturnFee(), settleBillBeans.get(i).getIncomeAmt()));
            detailSettleBillBean.setSettleDate(settleBillBeans.get(i).getSettleDate());
            detailSettleBillBean.setTransDateTime(settleBillBeans.get(i).getTransDateTime());
            detailSettleBillBean.setYqbTransNo(settleBillBeans.get(i).getTransCoreTxnSsn());
            detailSettleBillBeans.add(detailSettleBillBean);
        }
        return detailSettleBillBeans;
    }

    protected TotalSettleBillBean genTotalData(List<DetailSettleBillBean> detailBills, Bill25 bill) {
        TotalSettleBillBean totalSettleBillBean = new TotalSettleBillBean();
        totalSettleBillBean.setStartDate(bill.getStartDate());
        totalSettleBillBean.setEndDate(bill.getEndDate());
        totalSettleBillBean.setMerchantNo(bill.getMerchantNo());
        totalSettleBillBean.setFileCrtDateTime(DateUtil.getCurrentDateTime(PATTERN_FULL_5));
        BigDecimal totalFeeAmt = ZERO;
        BigDecimal totalOrderAmt = ZERO;
        for (int i = 0; i < detailBills.size(); i++) {
            totalFeeAmt = totalFeeAmt.add(detailBills.get(i).getFeeAmt());
            totalOrderAmt = totalOrderAmt.add(detailBills.get(i).getOrderAmt());
        }
        totalSettleBillBean.setTotalFeeAmt(totalFeeAmt.toString());
        totalSettleBillBean.setOrderTotalAmt(totalOrderAmt.toString());

        return totalSettleBillBean;
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


}
