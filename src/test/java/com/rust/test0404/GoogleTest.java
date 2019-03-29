/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.test0404;

import org.junit.Test;

import com.google.common.base.Preconditions;

/**
 *
 * @author FUTANGHANG004
 * @version $Id: GoogleTest, v 0.1 2018/4/4  FUTANGHANG004 Exp $
 */
public class GoogleTest {
    @Test
    public void test() {
        boolean flg = true;
        flg = false;

        // (value expect ,if not expect throw message
        Preconditions.checkState(flg, "flg is not true");

    }
}
