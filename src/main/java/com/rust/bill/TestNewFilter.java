/*
* Package com.rust.bill.test 
* FileName: TestNewFilter
* Author:   Rust
* Date:     2018/4/4 0:05
*/
package com.rust.bill;

import com.rust.bill.test.Bill25;
import org.junit.Test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * FileName:    TestNewFilter
 * Author:      Rust
 * Date:        2018/4/4
 * Description:
 */
public class TestNewFilter {
    @Test
    public void testFilterWithJDK(){
        String rootPath = "C:\\Users\\Administrator\\has";
        File rootFile = new File(rootPath);
        Bill25 bill25 = new Bill25();
        bill25.setMerchantNo("900000164395");
        bill25.setStartDate("20180101");
        bill25.setEndDate("20180110");
        long start = System.currentTimeMillis();
        List<File> list = doFilter(rootFile, new ArrayList<File>(), bill25);
        System.out.println("time:" + (System.currentTimeMillis() - start)+ "size:" + list.size());
    }

    private List<File> doFilter(File rootFile, List<File> fileList, Bill25 bill25) {
        rootFile.listFiles(new FileFilter() {
            @Override
            public boolean accept(File dir) {
                if (dir.isDirectory()) {
                    doFilter(dir, fileList, bill25);
                } else {
                    if (dir.getParentFile().isDirectory() &&
                            dir.getParentFile().getName().compareTo(bill25.getStartDate()) > -1 &&
                            dir.getParentFile().getName().compareTo(bill25.getEndDate()) < 1 &&
                            dir.getName().endsWith(".txt") &&
                            dir.getName().startsWith(bill25.getMerchantNo())) {
                        fileList.add(dir);
                    }
                }
                return true;
            }
        });
        return fileList;
    }


}
