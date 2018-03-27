package com.rust.bill; /**
 * 壹钱包 Copyright (c) 2013-2018 壹钱包版权所有.
 */

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;

import static com.sun.xml.internal.ws.commons.xmlutil.Converter.UTF_8;


/**
 * @author FUTANGHANG004
 * @version $Id: Test, v 0.1 2018/3/27 FUTANGHANG004 Exp $
 */
public class TestSettlement {

    @Test
    public void test() {

        String dot = File.separator;
        String path = System.getProperty("user.home") + dot + "DeskTop" + dot + "900000000006_20171108_230757.txt";

        try {
            List<SettleBillBean> stringList = Files.readLines(new File(path), Charset.forName(UTF_8), new LineProcessor<List<SettleBillBean>>() {
                List<SettleBillBean> settleBillBeans = Lists.newArrayList();

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
                            } catch (InstantiationException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                    }
                    return true;
                }

                public List<SettleBillBean> getResult() {
                    return settleBillBeans;
                }
            });


            System.out.println(stringList);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
