/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.test0403;

import java.io.File;
import java.io.FileFilter;

import org.junit.Test;

import com.rust.bill.test.Bill25;

/**
 *
 * @author FUTANGHANG004
 * @version $Id: FilterFile, v 0.1 2018/4/3  FUTANGHANG004 Exp $
 */
public class FilterFile {
	@Test
	public void test(){

		String path = "D:\\Users\\futanghang004\\nas";


		Bill25 bill25 = new Bill25();
		bill25.setBillType("25");
		bill25.setMerchantNo("900000164395");
		bill25.setStartDate("20180101");
		bill25.setEndDate("20180130");
		bill25.setNeedZip(true);
		FileFilter f = new SimpleFilter(bill25);

		File file = new File(path);
		File[] files = file.listFiles(f);

		assert files != null;
		System.out.println(files.length);


	}

	class SimpleFilter implements FileFilter {
		Bill25 form;

		public SimpleFilter(Bill25 form) {
			this.form = form;
		}

		@Override
		public boolean accept(File input) {
			boolean flg = input.isFile() && input.getParentFile().isDirectory() && input.getParentFile().getName().compareTo(form.getStartDate()) > -1
					&& input.getParentFile().getName().compareTo(form.getEndDate()) < 1 && input.getName().endsWith(".txt") && input.getName().contains(form.getMerchantNo());

			return flg;
		}
	}
}
