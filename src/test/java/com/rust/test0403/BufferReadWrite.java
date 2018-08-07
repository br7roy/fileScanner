/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.test0403;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * FileName:    BufferReadWrite
 * Author:      Rust
 * Date:        2018/4/14
 * Description:
 */
public class BufferReadWrite {
	private static String dot = File.separator;
	private static String root = System.getProperty("user.home") + dot;


	/**
	 * RandomAccessFile使用1KB进行写入
	 * @throws IOException
	 */
	@Test
	public void test1kbWriteWithRandomAccessFile() throws IOException {

		String srcFilePath = root +"desktop"+dot+ "900000164395_20180328_227708.txt";

		// String srcFilePath = root +"desktop"+dot+ "raf.txt";

		String destFilePath = root+"desktop"+dot+"buff1.txt";

		RandomAccessFile rafi = new RandomAccessFile(srcFilePath, "r");

		RandomAccessFile rafo = new RandomAccessFile(destFilePath, "rw");


		//1kb
		byte[] buff = new byte[1024 * 8];

		long start = System.currentTimeMillis();

		int len = rafi.read(buff);

		while (len > 0) {
			if (len == buff.length) {
				rafo.write(buff);
				// System.out.println(new String(buff,"GBK"));
			} else {
				rafo.write(buff, 0, len);
				// System.out.println(new String(buff,0,len,"GBK"));
			}
			len = rafi.read(buff);
		}
		rafi.close();
		rafo.close();

		//4.664s
		System.out.println("Spend: " + (double) (System.currentTimeMillis() - start) / 1000 + "s");
	}


	/**
	 * 使用fileChannel ByteBuffer写入文件
	 * @throws IOException
	 */
	@Test
	public void testByteBufferWrite() throws IOException {


		String srcFilePath = root +"desktop"+dot+ "900000164395_20180328_227708.txt";

		// String srcFilePath = root +"desktop"+dot+ "raf.txt";

		String destFilePath = root+"desktop"+dot+"buff1.txt";

		RandomAccessFile rafi = new RandomAccessFile(srcFilePath, "r");

		RandomAccessFile rafo = new RandomAccessFile(destFilePath, "rw");

		FileChannel fci = rafi.getChannel();

		FileChannel fco = rafo.getChannel();

		ByteBuffer inBuffer = ByteBuffer.allocate(1024 * 8);

		ByteBuffer outBuffer = ByteBuffer.allocate(1024 * 8);

		byte[] buff = new byte[1024 * 8];

		long start = System.currentTimeMillis();

/*		int len = fci.read(inBuffer);

		while (len > 0) {
			outBuffer.rewind();
			inBuffer.flip();
			byte b = inBuffer.get();
				outBuffer.put(b);
			outBuffer.flip();
			while (outBuffer.remaining()>0) {
				fco.write(outBuffer);
			}
			while (outBuffer.remaining() <= 0) {
				outBuffer.clear();
			}
			len = fci.read(inBuffer);
		}*/


		int len = rafi.read(buff);

		while (len > 0) {
			outBuffer.rewind();
			if (len == buff.length) {

				outBuffer.put(buff);
			}
			else {
				outBuffer.put(buff, 0, len);
			}
			outBuffer.flip();
			while (outBuffer.hasRemaining()) {

				fco.write(outBuffer);
			}
			len = rafi.read(buff);
		}


		fco.close();
		rafi.close();
		rafo.close();

		//4.648s
		System.out.println("Spend: " + (double) (System.currentTimeMillis() - start) / 1000 + "s");



	}

	/**
	 * 使用MappedByteBufferWrite写入文件
	 */
	@Test
	public void testMappedByteBufferWrite() throws IOException {
		String srcFilePath = root +"desktop"+dot+ "900000164395_20180328_227708.txt";

		// String srcFilePath = root +"desktop"+dot+ "raf.txt";

		String destFilePath = root+"desktop"+dot+"buff1.txt";

		long allocate = 100 * 1024 * 8L;

		RandomAccessFile rafi = new RandomAccessFile(srcFilePath, "r");

		RandomAccessFile rafo = new RandomAccessFile(destFilePath, "rw");

		FileChannel fco = rafo.getChannel();

		MappedByteBuffer mbbo = fco.map(FileChannel.MapMode.READ_WRITE, 0, allocate);

		byte[] buff = new byte[1024 * 8];

		long start = System.currentTimeMillis();

		int len = rafi.read(buff);

		if (len > 0) {
			if (len == buff.length) {
				mbbo.put(buff);
			} else {
				mbbo.put(buff, 0, len);
			}
			mbbo.flip();
			while (mbbo.hasRemaining()) {
				fco.write(mbbo);
			}
			len = rafi.read(buff);
		}
		//5.591s
		System.out.println("Spend: " + (double) (System.currentTimeMillis() - start) / 1000 + "s");
		fco.close();
		rafi.close();
		rafo.close();




	}





}
