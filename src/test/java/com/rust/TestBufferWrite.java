/*
* Package com.rust 
* FileName: TestBufferWrite
* Author:   Rust
* Date:     2018/4/1 13:05
*/
package com.rust;

import java.io.*;

/**
 * FileName:    TestBufferWrite
 * Author:      Rust
 * Date:        2018/4/1
 * Description:
 */
public class TestBufferWrite {
    public static void main(String[] args) throws IOException {
        File file = new File(System.getProperty("user.home") + File.separator + "desktop" + File.separator + "write.csv");
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }


        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
        TestDomain testDomain = new TestDomain();
        testDomain.setDesc("hehe");
        testDomain.setName("123123");
        testDomain.setOther("hohoho");
        TestDomain testDomain2 = new TestDomain();
        testDomain2.setDesc("aeraer");
        testDomain2.setName("678678");
        testDomain2.setOther("ghjfghj");
        bufferedOutputStream.write((testDomain.getDesc() + "," + testDomain.getName() + "," + testDomain.getOther() + "\r\n").getBytes("GBK"));
        bufferedOutputStream.write((testDomain2.getDesc() + "," + testDomain2.getName() + "," + testDomain2.getOther() + "\r\n").getBytes("GBK"));
        bufferedOutputStream.flush();



    }
    static class TestDomain implements Serializable{
        private static final long serialVersionUID = -8022738988552790902L;
        private String name;
        private String desc;
        private String other;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }
    }

}
