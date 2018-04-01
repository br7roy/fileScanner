/*
* Package com.rust.bill.test 
* FileName: StringUtil
* Author:   Rust
* Date:     2018/3/29 21:37
*/
package com.rust.bill.test;

import java.math.BigDecimal;

/**
 * FileName:    StringUtil
 * Author:      Rust
 * Date:        2018/3/29
 * Description:
 */
public class StringUtil {
    public static String mix(String a,String b ,String c) {
        BigDecimal bigDecimal = new BigDecimal(a);
        BigDecimal bigDecimal1 = new BigDecimal(b);
        BigDecimal bigDecimal2 = new BigDecimal(c);
        return bigDecimal.add(bigDecimal1).subtract(bigDecimal2).toString();
    }
}
