package com.rust.bill.test;

import com.google.common.collect.FluentIterable;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;

import static com.rust.bill.test.BillStyleConstants.DETAIL_COLUMN;
import static com.rust.bill.test.BillStyleConstants.TOTAL_COLUMN;

/**
 * 预生成账单及B门户下载中心基类
 * @author FUTANGHANG004
 * @version $Id: AbstractGenerateFileServiceV2, v 0.1 2018/3/30  FUTANGHANG004 Exp $
 */
public abstract class AbstractGenerateFileServiceV2<T> implements Constant, IBillFileGenerateService {
	private static final String WHERE = "AbstractGenerateFileServiceV2";
    public static final String FILE_SPLIT_SIGN = ",";

    // data chunk be written per time
    private static final int DATA_CHUNK = 128 * 1024 * 1024;

    // total data size is 2G
    private static final long LEN = 2L * 1024 * 1024 * 1024L;

    /**
	 * 生成账单文件
	 *
	 * @param recordId
	 * @throws Exception
	 */
	public void generateFile(String recordId) throws Exception {
		//校验任务是否存在，获取账单类型及商户号
        MspBillDownloadRecordDTO dto = new MspBillDownloadRecordDTO();
        //取form查询条件
		T form = getForm(dto);
		String errMsg = validForm(form);
		if (!StringUtils.isEmpty(errMsg)) {
			return;
		}

		File destFile = genDestFile(form);
		dto.setFileUrl(destFile.getPath());
		// TODO: Rust 2018/3/29

        FluentIterable<File> settleBillBeans = getFilterFiles(form);

        if (settleBillBeans.size() == 0) {
            System.out.println("not file wait to generate");
            return;
        }
        if (!destFile.exists()) {
            Files.createParentDirs(destFile);
        }else {
            destFile.delete();
        }
        System.out.println("write starting...");
        long start = System.currentTimeMillis();

        filterDataAndWriteCvs(settleBillBeans.iterator(), form, destFile);


        System.out.println("write finish cost:" + (System.currentTimeMillis() - start));
    }

    protected abstract void filterDataAndWriteCvs(Iterator<File> iterator, T form, File destFile) throws Exception;

    private void writeCvs(File destFile, TotalSettleBillBean totalDataBean, List<DetailSettleBillBean> detailSettleBillBeans) throws IOException {
        if (!destFile.exists()) {
            Files.createParentDirs(destFile);
        }else {
            destFile.delete();
        }
        System.out.println("write starting...");
        long start = System.currentTimeMillis();


/*        FileChannel foc = new FileOutputStream(destFile).getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        writeFileByLine(foc, byteBuffer, Arrays.toString(TOTAL_COLUMN).substring(1, Arrays.toString(TOTAL_COLUMN).length() - 1));
        writeFileByLine(foc, byteBuffer, "\r\n");
        writeFileByLine(foc,byteBuffer,totalDataBean.getMerchantNo() + FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, totalDataBean.getStartDate()+FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, totalDataBean.getEndDate()+FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, totalDataBean.getUnit()+FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, totalDataBean.getCurrency()+FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, totalDataBean.getOrderTotalAmt()+FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, totalDataBean.getTotalFeeAmt()+FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, totalDataBean.getTotalFeeAmt()+FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, totalDataBean.getFileCrtDateTime()+FILE_SPLIT_SIGN);
        writeFileByLine(foc, byteBuffer, "\r\n");
        writeFileByLine(foc, byteBuffer, "\r\n");
        writeFileByLine(foc, byteBuffer, Arrays.toString(DETAIL_COLUMN).substring(1, Arrays.toString(DETAIL_COLUMN).length() - 1));
        writeFileByLine(foc, byteBuffer, "\r\n");
        for (int i = 0; i < detailSettleBillBeans.size(); i++) {
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getMerchantNo()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getSettleDate()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getMerchantOrderNo()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getCheckOutOrderNo()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getYqbTransNo()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getTransDateTime()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getOrderAmt()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getFeeAmt()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, detailSettleBillBeans.get(i).getSettleAmt()+FILE_SPLIT_SIGN);
            writeFileByLine(foc, byteBuffer, "\r\n");

        }*/




















        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
        bos.write(Arrays.toString(TOTAL_COLUMN).substring(1, Arrays.toString(TOTAL_COLUMN).length() - 1).getBytes("GBK"));
        bos.write("\r\n".getBytes("GBK"));
        write(bos, totalDataBean.getMerchantNo());
        write(bos, totalDataBean.getStartDate());
        write(bos, totalDataBean.getEndDate());
        write(bos, totalDataBean.getUnit());
        write(bos, totalDataBean.getCurrency());
        write(bos, totalDataBean.getOrderTotalAmt());
        write(bos, totalDataBean.getTotalFeeAmt());
        write(bos, totalDataBean.getFileCrtDateTime());
        bos.write("\r\n".getBytes("GBK"));
        bos.write("\r\n".getBytes("GBK"));
        bos.write(Arrays.toString(DETAIL_COLUMN).substring(1, Arrays.toString(DETAIL_COLUMN).length() - 1).getBytes("GBK"));
        bos.write("\r\n".getBytes("GBK"));
        for (int i = 0; i < detailSettleBillBeans.size(); i++) {
            write(bos, detailSettleBillBeans.get(i).getMerchantNo());
            write(bos, detailSettleBillBeans.get(i).getSettleDate());
            write(bos, detailSettleBillBeans.get(i).getMerchantOrderNo());
            write(bos, detailSettleBillBeans.get(i).getCheckOutOrderNo());
            write(bos, detailSettleBillBeans.get(i).getYqbTransNo());
            write(bos, detailSettleBillBeans.get(i).getTransDateTime());
            bos.write((detailSettleBillBeans.get(i).getOrderAmt()+FILE_SPLIT_SIGN).getBytes("GBK"));
            bos.write((detailSettleBillBeans.get(i).getFeeAmt()+FILE_SPLIT_SIGN).getBytes("GBK"));
            write(bos, detailSettleBillBeans.get(i).getSettleAmt());
            bos.write("\r\n".getBytes("GBK"));
        }


/*        Files.write(Arrays.toString(TOTAL_COLUMN).substring(1,Arrays.toString(TOTAL_COLUMN).length()-1), destFile, Charset.forName("GBK"));
		Files.append("\r\n", destFile, Charset.forName("GBK"));
		Files.append(totalDataBean.getMerchantNo()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
		Files.append(totalDataBean.getStartDate()+FILE_SPLIT_SIGN,destFile,Charset.forName("GBK"));
		Files.append(totalDataBean.getEndDate()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
		Files.append(totalDataBean.getUnit()+FILE_SPLIT_SIGN,destFile,Charset.forName("GBK"));
		Files.append(totalDataBean.getCurrency()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
		Files.append(totalDataBean.getOrderTotalAmt()+FILE_SPLIT_SIGN,destFile,Charset.forName("GBK"));
		Files.append(totalDataBean.getTotalFeeAmt()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
		Files.append(totalDataBean.getFileCrtDateTime()+FILE_SPLIT_SIGN,destFile,Charset.forName("GBK"));
		Files.append("\r\n", destFile, Charset.forName("GBK"));
		Files.append("\r\n", destFile, Charset.forName("GBK"));
        Files.append(Arrays.toString(DETAIL_COLUMN).substring(1, Arrays.toString(DETAIL_COLUMN).length() - 1), destFile, Charset.forName("GBK"));
        Files.append("\r\n", destFile, Charset.forName("GBK"));
        for (int i = 0; i < detailSettleBillBeans.size(); i++) {
			Files.append(detailSettleBillBeans.get(i).getMerchantNo()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append(detailSettleBillBeans.get(i).getSettleDate()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append(detailSettleBillBeans.get(i).getMerchantOrderNo()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append(detailSettleBillBeans.get(i).getCheckOutOrderNo()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append(detailSettleBillBeans.get(i).getYqbTransNo()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append(detailSettleBillBeans.get(i).getTransDateTime()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append(detailSettleBillBeans.get(i).getOrderAmt()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append(detailSettleBillBeans.get(i).getFeeAmt()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append(detailSettleBillBeans.get(i).getSettleAmt()+FILE_SPLIT_SIGN, destFile, Charset.forName("GBK"));
			Files.append("\r\n", destFile, Charset.forName("GBK"));
		}*/
        System.out.println(String.format("write end cost:%s", +(System.currentTimeMillis() - start) + "ms"));
    }

    // protected abstract List<SettleBillBean> genFinalSettleBean(Iterator<File> fileIterator)throws Exception;

    /**
	 * 获取原始结算单数据
	 * @param form
	 * @return
	 */
	protected abstract FluentIterable<File> getFilterFiles(T form) throws Exception;

/*	*//**
	 * 获取结算账单明细
	 * @param settleBillBeans
	 * @return
	 *//*
	protected abstract List<DetailSettleBillBean> genDetailList(List<SettleBillBean> settleBillBeans, T form);

	*//**
	 * 获取结算账单总计
	 * @param detailBills
	 * @return
	 *//*
	protected abstract TotalSettleBillBean genTotalData(List<DetailSettleBillBean> detailBills, T form);*/
	/**
	 * 根据请求流水号得到查询表单
	 *
	 * @param dto
	 * @return
	 */
	public abstract T getForm(MspBillDownloadRecordDTO dto);
	/**
	 * 校验form请求参数，返回errMsg
	 *
	 * @param form
	 * @return
	 */
	public abstract String validForm(T form);


	public Object transForm(Object obj, String merchantNo) {
		Bill25 bill25 = (Bill25) obj;
		if (StringUtils.isNotEmpty(merchantNo)) {
			bill25.setMerchantNo(merchantNo);
		}
		return bill25;
	}




	public abstract Date getStartDate(Bill25 bill);

	public abstract Date getEndDate(Bill25 bill);

	/**
	 * 生成目标文件
	 * @param form
	 * @return
	 */
	public abstract File genDestFile(T form);

	/**
	 * 子类有特殊处理可重写
	 *
	 * @param map
	 * @return
	 */
	public String validDto(Map<String, Object> map) {
		StringBuilder errMsg = new StringBuilder();
		String merchantNo = (String) map.get("merchantNo");
		if (StringUtils.isBlank(merchantNo)) {
			errMsg.append("merchantNo为空;");
		}
		Date startDate = DateUtil.getDateTimeByString5((String) map.get("startDate"));
		Date endDate = DateUtil.getDateTimeByString5((String) map.get("endDate"));
		if (null == startDate || null == endDate) {
			errMsg.append("startDate或endDate格式不对;");
		}
		if (endDate.before(startDate)) {
			errMsg.append("endDate在startDate前;");
		}
		if (DateUtil.getDateDifference(startDate, endDate) > 31) {
			errMsg.append("报表查询时间大于31天.");
		}
		return errMsg.toString();
	}

    protected void writeRaf(RandomAccessFile raf, String data, boolean force) throws IOException {
        doWriteRaf(raf, data, force);
    }
    protected void writeRaf(RandomAccessFile raf, String data) throws IOException {
        doWriteRaf(raf, data, true);
    }

    private void doWriteRaf(RandomAccessFile raf, String data, boolean force) throws IOException {
        if (force) {
            raf.write((data+FILE_SPLIT_SIGN).getBytes("GBK"));
        }else {
            raf.write(data.getBytes("GBK"));
        }
    }

    protected  void write(BufferedOutputStream bos, String data,boolean force) throws IOException {
        doWriteNewLine(bos, data, force);
    }
    protected  void write(BufferedOutputStream bos, String data) throws IOException {
        doWriteNewLine(bos, data, true);
    }

    private void doWriteNewLine(BufferedOutputStream bos, String data,boolean force) throws IOException {
        if (force) {
            bos.write((data + FILE_SPLIT_SIGN).getBytes("GBK"));
        }else {
            bos.write(data.getBytes("GBK"));
        }
    }

    protected static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line) {
        try {
            //write on file head
            //fcout.write(wBuffer.wrap(line.getBytes()));
            //wirte append file on foot
            fcout.write(ByteBuffer.wrap(line.getBytes()), fcout.size());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 在MappedByteBuffer释放后再对它进行读操作的话就会引发jvm crash，在并发情况下很容易发生
     * 正在释放时另一个线程正开始读取，于是crash就发生了。所以为了系统稳定性释放前一般需要检
     * 查是否还有线程在读或写
     * @param mappedByteBuffer
     */
    public static void unmap(final MappedByteBuffer mappedByteBuffer) {
        try {
            if (mappedByteBuffer == null) {
                return;
            }

            mappedByteBuffer.force();
            AccessController.doPrivileged(new PrivilegedAction<Object>() {
                @Override
                @SuppressWarnings("restriction")
                public Object run() {
                    try {
                        Method getCleanerMethod = mappedByteBuffer.getClass()
                                .getMethod("cleaner");
                        getCleanerMethod.setAccessible(true);
                        sun.misc.Cleaner cleaner =
                                (sun.misc.Cleaner) getCleanerMethod
                                        .invoke(mappedByteBuffer, new Object[0]);
                        cleaner.clean();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println("clean MappedByteBuffer completed");
                    return null;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
