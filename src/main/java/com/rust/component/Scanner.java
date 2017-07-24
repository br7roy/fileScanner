package com.rust.component;

import java.io.*;
import java.util.*;

/**
 * FileName:    Scanner
 * Author:      Rust
 * Date:        2017/7/21
 * Description:
 */
public class Scanner {



    public static void main(String[] args) throws IOException {
        String filePath = "C:\\Users\\Administrator\\Desktop\\test.log";
        String genPath = "C:\\Users\\Administrator\\Desktop\\result.log";
        new Scanner(filePath);
        System.out.println(uidResult);
//        System.out.println(list);

        //控制台输出
//        System.out.println(mix(list, count(list)));
        //生成文件
//        outPut(mix(list, count(list)), genPath);


    }


    private static File file;

    private static final String stK1 = "[c20,CLIENT_INFO]";
    private static final String stK2 = "uid=";
    private static final String startIndex = "uid=";
    private static final String endIndex = ", sessionId=";

    private static StringBuilder uidResult;

    private static List<Object> list = new ArrayList();
    private static List fiList = new ArrayList();


    public Scanner(String filePath) {
        file = new File(filePath);
        txt2String();
    }

    private static String txt2String() {
        StringBuilder result = new StringBuilder();
         uidResult = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                if (s.contains(stK1) && s.contains(stK2)) {
                    uidResult.append(System.lineSeparator()).append(s);
                    try {
                        list.add(s.substring(s.indexOf(startIndex)).substring(0, s.substring(s.indexOf(startIndex)).indexOf(",")));
                    } catch (RuntimeException e) {
                        System.out.println("检测到特殊日志无逗号结尾");
                        list.add(s.substring(s.indexOf(startIndex)));
                    }
                }
            }
            br.close();
        } catch (Exception e) {
        }
        mixCount(uidResult);

        return uidResult.toString();
    }

    private static void mixCount(StringBuilder stringBuilder) {








    }


    public static List count(List list) {
        Map<Object, Integer> map = new HashMap<>();
        for (Object obj : list) {
            if (map.containsKey(obj)) {
                map.put(obj, map.get(obj).intValue() + 1);
            } else {
                map.put(obj, 1);
            }
        }
        return new ArrayList<Integer>(map.values());
    }

    private static List mix(List... list) {
        Iterator iterator = list[0].iterator();
        Iterator iterator1 = list[list.length - 1].iterator();
        while (iterator.hasNext() && iterator1.hasNext()) {
            fiList.add(iterator.next() + ",count：" + iterator1.next());
        }
        return fiList;

    }

    public static void outPut(List list, String outputFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        for (Object o : list) {
            printWriter.write(o.toString() + "\r\n");
        }
        printWriter.close();
        fileOutputStream.close();
    }



}
