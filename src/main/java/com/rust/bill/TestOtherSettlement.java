package com.rust.bill; /**
 * 壹钱包 Copyright (c) 2013-2018 壹钱包版权所有.
 */

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import com.rust.bill.test.Bill25FileGenerateServiceImpl;
import com.rust.bill.test.IBillFileGenerateService;
import com.rust.bill.test.SettleBillBean;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import static com.google.common.base.Charsets.UTF_8;


/**
 * @author FUTANGHANG004
 * @version $Id: Test, v 0.1 2018/3/27 FUTANGHANG004 Exp $
 */
public class TestOtherSettlement {
    String dot = File.separator;
    String path = System.getProperty("user.home") + dot + "DeskTop" + dot + "900000000006_20171108_230757.txt";
    String path2 = System.getProperty("user.home") + dot + "DeskTop" + dot + "nas" + dot;

    @Test
    public void test() {


        final String startDate = "20180101";
        final String endDate = "20180110";

        FluentIterable<File> files = Files.fileTreeTraverser().breadthFirstTraversal(new File(path2)).filter(new Predicate<File>() {
            public boolean apply(File input) {
                return input.isFile() &&
                        input.getParentFile().isDirectory() &&
                        input.getParentFile().getName().compareTo(startDate) > -1 &&
                        input.getParentFile().getName().compareTo(endDate) < 1 &&
                        input.getName().endsWith(".txt") &&
                        input.getName().contains("900000000006");
            }
        });

        for (File file : files) {
            System.out.println(file.getPath());
        }
        final List<SettleBillBean> settleBillBeans = Lists.newArrayList();
        Iterator<File> iterator = files.iterator();
        long startTime = System.currentTimeMillis();
        do {
            try {
                 Files.readLines(iterator.next(), UTF_8, new LineProcessor<List<SettleBillBean>>() {

                    public boolean processLine(String line) throws IOException {
                        if (!("".equals(line) || line == null || line.trim().equals(""))) {
                            if (line.trim().startsWith("I"))
                                try {
                                    String[] temp = line.trim().replace("\t", "").split(",");
                                    Class clazz = SettleBillBean.class;
                                    Object obj = clazz.newInstance();
                                    Field[] fields = clazz.getDeclaredFields();
                                    if (fields.length != temp.length) {
                                        throw new IllegalArgumentException("file data wrong!");
                                    }

                                    for (int i = 0; i < fields.length; ++i) {
                                        fields[i].setAccessible(true);
                                        fields[i].set(obj, temp[i]);
                                    }
                                    settleBillBeans.add(SettleBillBean.class.cast(obj));
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                        }
                        return true;
                    }

                    public List<SettleBillBean> getResult() {
                        return settleBillBeans;
                    }
                });


/*                System.out.println(stringList);
                System.out.println(stringList.size());*/
                
            } catch (IOException e) {
                e.printStackTrace();
            }


        } while (iterator.hasNext());

        System.out.println(settleBillBeans);
        System.out.println("TestOtherSettlement.test" + settleBillBeans.size());
        System.out.println("cost" + (System.currentTimeMillis() - startTime));
    }

    @Test
    public void test2() {


        final String startDate = "20180101";
        final String endDate = "20180110";

        FluentIterable<File> files = Files.fileTreeTraverser().breadthFirstTraversal(new File(path2)).filter(new Predicate<File>() {
            public boolean apply(File input) {
                return input.isFile() &&
                        input.getParentFile().isDirectory() &&
                        input.getParentFile().getName().compareTo(startDate) > -1 &&
                        input.getParentFile().getName().compareTo(endDate) < 1 &&
                        input.getName().endsWith(".txt") &&
                        input.getName().contains("900000000006");
            }
        });

        for (File file : files) {
            System.out.println(file.getPath());
        }
        final List<SettleBillBean> settleBillBeans = Lists.newArrayList();
        long startTime = System.currentTimeMillis();
        for (File file : files) {
            try {
                Files.readLines(file, UTF_8, new LineProcessor<List<SettleBillBean>>() {

                    public boolean processLine(String line) throws IOException {
                        if (!("".equals(line) || line == null || line.trim().equals(""))) {
                            if (line.trim().startsWith("I"))
                                try {
                                    String[] temp = line.trim().replace("\t", "").split(",");
                                    Class clazz = SettleBillBean.class;
                                    Object obj = clazz.newInstance();
                                    Field[] fields = clazz.getDeclaredFields();
                                    if (fields.length != temp.length) {
                                        throw new IllegalArgumentException("file data wrong!");
                                    }

                                    for (int i = 0; i < fields.length; ++i) {
                                        fields[i].setAccessible(true);
                                        fields[i].set(obj, temp[i]);
                                    }
                                    settleBillBeans.add(SettleBillBean.class.cast(obj));
                                } catch (InstantiationException e) {
                                    e.printStackTrace();
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                        }
                        return true;
                    }

                    public List<SettleBillBean> getResult() {
                        return settleBillBeans;
                    }
                });


/*                System.out.println(stringList);
                System.out.println(stringList.size());*/

            } catch (IOException e) {
                e.printStackTrace();
            }
        }




        System.out.println(settleBillBeans);
        System.out.println("TestOtherSettlement.test" + settleBillBeans.size());
        System.out.println("cost" + (System.currentTimeMillis() - startTime));



    }

    @Test
    public void testFilterFiles() {

        final String startDate = "20180101";
        final String endDate = "20180110";

        FluentIterable<File> files = Files.fileTreeTraverser().breadthFirstTraversal(new File(path2)).filter(new Predicate<File>() {
            public boolean apply(File input) {
                return input.isFile() &&
                        input.getParentFile().isDirectory() &&
                        input.getParentFile().getName().compareTo(startDate) > -1 &&
                        input.getParentFile().getName().compareTo(endDate) < 1 &&
                        input.getName().endsWith(".txt") &&
                        input.getName().contains("900000000006");
            }
        });

        for (File file : files) {
            System.out.println(file.getPath());
        }
        System.out.println(files.size());

    }

    @Test
    public void doTestGenFile() throws Exception {
        IBillFileGenerateService iBillFileGenerateService = new Bill25FileGenerateServiceImpl();
        iBillFileGenerateService.generateFile("123123");
    }


}
