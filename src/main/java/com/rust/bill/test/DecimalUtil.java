/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.bill.test;

import java.math.BigDecimal;

/**
 *
 * @author FUTANGHANG004
 * @version $Id: DecimalUtil, v 0.1 2018/3/29  FUTANGHANG004 Exp $
 */
public class DecimalUtil {

	public static BigDecimal subtract(String a, String b) {
		BigDecimal bigDecimal = new BigDecimal(a);
		BigDecimal bigDecimal1 = new BigDecimal(b);
		return bigDecimal.subtract(bigDecimal1);
	}

	public static BigDecimal format2BigDecimal(String s) {
		return new BigDecimal(s);
	}


}
