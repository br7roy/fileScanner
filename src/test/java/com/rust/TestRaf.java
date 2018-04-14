/*
* Package com.rust 
* FileName: TestRaf
* Author:   Rust
* Date:     2018/4/1 20:34
*/
package com.rust;

import org.junit.Test;

import java.io.*;

/**
 * FileName:    TestRaf
 * Author:      Rust
 * Date:        2018/4/1
 * Description:
 */
public class TestRaf {
    @Test
    public void testWrite() throws IOException {
        String path = System.getProperty("user.home") + File.separator + "desktop" + File.separator + "raf.cvs";
        File file = new File(path);


        RandomAccessFile raf = new RandomAccessFile(file, "rw");

        raf.write(("test1" + ",").getBytes("GBK"));
        raf.write(("test2"+",").getBytes("GBK"));
        raf.write("\r\n".getBytes("GBK"));
        raf.write(("test3" + ",").getBytes("GBK"));
        raf.write(("test4"+",").getBytes("GBK"));
        raf.seek(0L);
        raf.write(("test5" + ",").getBytes("GBK"));
        raf.write(("test6"+",").getBytes("GBK"));
        raf.write("\r\n".getBytes("GBK"));






    }
}
