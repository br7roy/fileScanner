/*
* Package com.rust.bill 
* FileName: Test
* Author:   Rust
* Date:     2018/3/28 23:15
*/
package com.rust.bill;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * FileName:    Test
 * Author:      Rust
 * Date:        2018/3/28
 * Description:
 */
public class TestOther {
    @Test
    public void test() throws IOException {

        String dot = File.separator;
        String path = System.getProperty("user.home") + dot + "DeskTop" + dot + "900000000006_20171108_230757.txt";

        File file = new File(path);

        System.out.println(file.getCanonicalPath());

    }

}
