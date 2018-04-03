/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.bill.filechannel;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *
 * @author FUTANGHANG004
 * @version $Id: FCWrite, v 0.1 2018/4/2  FUTANGHANG004 Exp $
 */
public class FCWrite {
	@Test
	public void fcWrite() throws IOException {
		String dot = File.separator;
		String path = System.getProperty("user.home") + dot + "desktop" + dot + "raf.txt";
		String data = "hahahahhahahah";



		RandomAccessFile raf = new RandomAccessFile(new File(path), "rw");
		FileChannel foc = raf.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		byteBuffer.put(data.getBytes("UTF-8"));
		byteBuffer.flip();
		while (byteBuffer.hasRemaining()) {
			foc.write(byteBuffer);
		}
		byteBuffer.flip();
		byteBuffer.put("frontsssssddd".getBytes("UTF-8"));
		byteBuffer.flip();
		raf.seek(0L);
		while (byteBuffer.hasRemaining()) {
			foc.write(byteBuffer);
		}

		foc.close();











	}
}
