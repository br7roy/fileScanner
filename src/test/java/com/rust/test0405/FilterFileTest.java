/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.test0405;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Stopwatch;
import com.rust.bill.test.Bill25;
import com.rust.bill.test.DateUtil;

import static com.rust.bill.test.DateUtil.PATTERN_YYYY_MM_DD_3;

/**
 *
 * @author FUTANGHANG004
 * @version $Id: FilterFileTest, v 0.1 2018/4/8  FUTANGHANG004 Exp $
 */
public class FilterFileTest {
    public static final String DOT = File.separator;
    private static String rootPath = System.getProperty("user.home") + DOT + "nas" + DOT;
    @Test
    public void testFilterFile() {
        File rootFile = new File(rootPath);
        Bill25 bill25 = new Bill25();
        bill25.setMerchantNo("900000164395");
        bill25.setStartDate("20180101");
        bill25.setEndDate("20180110");
        List<File> files = new ArrayList<File>();
        Stopwatch watch = Stopwatch.createUnstarted();
        //方法1
        watch.start();
        files.addAll(filterFiles(bill25, rootFile, new ArrayList<File>()));
        watch.stop();
        System.out.println("cost:" + watch);
        System.out.println(files.size());
        files.clear();

        //方法2
        watch.reset();
        watch.start();
        List<File> rootFiles = getRootFiles( bill25,rootPath);
        System.out.println("rootFiles:" + rootFiles);
        List<File> tempFiles = filterFiles(bill25, rootFiles, files);
        //files.addAll(tempFiles);
        watch.stop();
        System.out.println("cost:" + watch);
        System.out.println(files.size());

    }

    private List<File> filterFiles(final Bill25 bill25, List<File> rootFiles, final List<File> files) {
        for (File rootFile : rootFiles) {
            File[] files1 = rootFile.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        filterFiles(bill25, pathname, files);
                    } else {
                        return pathname.getParentFile().isDirectory() &&
                                pathname.getParentFile().getName().compareTo(bill25.getStartDate()) > -1 &&
                                pathname.getParentFile().getName().compareTo(bill25.getEndDate()) < 1 &&
                                pathname.getName().endsWith(".txt") &&
                                pathname.getName().startsWith(bill25.getMerchantNo());
                    }
                    return false;
                }

            });
            if (files1 != null) {
                files.addAll(Arrays.asList(files1));
            }
        }
        return files;
    }

    private List<File> getRootFiles(Bill25 bill25, String rootPath) {

        List<File> rootFiles = new ArrayList<File>();

        Date startDate = DateUtil.parseString2Date(bill25.getStartDate(), PATTERN_YYYY_MM_DD_3);

        Date endDate = DateUtil.parseString2Date(bill25.getEndDate(), PATTERN_YYYY_MM_DD_3);

        List<Date> result = new ArrayList<Date>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(startDate);
        while (startDate.getTime() <= endDate.getTime()) {
            File file = new File(rootPath + DateUtil.formatDate(tempStart.getTime(), PATTERN_YYYY_MM_DD_3));
            if (file.isDirectory() && file.exists()) {
                rootFiles.add(file);
            }
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
            startDate = tempStart.getTime();
        }

        return rootFiles;
    }


    private List<File> filterFiles(final Bill25 bill25, File rootPath, final List<File> fileList) {
        rootPath.listFiles(new FileFilter() {
            @Override
            public boolean accept(File dir) {
                if (dir.isDirectory()) {
                    filterFiles(bill25, dir, fileList);
                } else {
                    if (dir.getParentFile().isDirectory() &&
                            dir.getParentFile().getName().compareTo(bill25.getStartDate()) > -1 &&
                            dir.getParentFile().getName().compareTo(bill25.getEndDate()) < 1 &&
                            dir.getName().endsWith(".txt") &&
                            dir.getName().startsWith(bill25.getMerchantNo())) {
                        return fileList.add(dir);
                    }
                }
                return false;
            }
        });
        return fileList;


    }


@Test
    public void demo() {

    String start = "20180129";

    String end = "20180201";

    Date startDate = DateUtil.parseString2Date(start, PATTERN_YYYY_MM_DD_3);

    Date endDate = DateUtil.parseString2Date(end, PATTERN_YYYY_MM_DD_3);


    List<Date> result = new ArrayList<Date>();
    Calendar tempStart = Calendar.getInstance();
    tempStart.setTime(startDate);
    while(startDate.getTime()<=endDate.getTime()){
        result.add(tempStart.getTime());
        tempStart.add(Calendar.DAY_OF_YEAR, 1);
        startDate = tempStart.getTime();
    }
    for (Date date : result) {
        System.out.println(DateUtil.formatDate(date, PATTERN_YYYY_MM_DD_3));
    }


}

}
