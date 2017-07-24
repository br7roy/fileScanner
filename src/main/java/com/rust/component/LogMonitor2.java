package com.rust.component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * FileName: LogMonitor Author: Rust Date: 2017/7/21 Description:
 */
public class LogMonitor2 {
    public static void main(String[] args) throws IOException {
        // 原日志路径
        String filePath = "C:\\Users\\Administrator\\Desktop\\test2.log";
        // 生成日志路径
        String genPath = "C:\\Users\\Administrator\\Desktop\\result.log";
        LogMonitor2 logMonitor = new LogMonitor2(filePath, genPath);

        // 控制台输出
//		logMonitor.genLog();
        // 生成文件
        logMonitor.outPutLog();
    }

    private static String inPutFilePath;
    private static String outPutLogPath;

    private static final String STK1 = "com.jjhgame.gf.basic.BasicLogger.info(BasicLogger.java:68) - [c20,CLIENT_INFO]";
    private static final String STK2 = "as same client uid, change thread for this client. existing cust:";

    private static final Integer CUST_ID_LENGTH = 15;

    private static final String reg = "\r\n";


    private static Map<String, Integer> uidMap = new TreeMap<>();
//    private static Map<String, Result> custMap = new TreeMap<>();
    private static List<Result> custList = new ArrayList<>();
    private static Map<String, Integer> lastMap = new LinkedHashMap<>();
    private static Map<String, Integer> finalMap = new LinkedHashMap<>();
    private static Map<String, Integer> aaMap = new LinkedHashMap<>();

    private String reg1 = "\\[c20,CLIENT_INFO\\]\\[sid=[0-9]+\\]uid=[0-9a-zA-Z-]+,";


    private LogMonitor2(String filePath, String _outPath) {
        File file = new File(filePath);
        if (!file.isFile()) {
            throw new RuntimeException("尝试从[" + filePath + "]读取原日志文件失败，请重新确认路径");
        }
        inPutFilePath = filePath;
        outPutLogPath = _outPath;
        filterLog();
//        genLog();

    }

    /**
     * 筛选日志
     */
    private void filterLog() {
        List<String> list = new ArrayList();
        try {
            list = Files.readAllLines(Paths.get(inPutFilePath));

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("尝试从[" + inPutFilePath + "]读取原日志文件失败，请重新确认路径");
        }


        try {

            for (String line : list) {
                if (line.contains(STK1)) {
                    //第一种情况以STK1为关键字查找uuid
                    int begin = line.indexOf("uid=");
                    int end = line.indexOf(",", begin + 1);

                    if (uidMap.containsKey(line.substring(begin, end))) {
                        int count = uidMap.get(line.substring(begin, end)) + 1;

                        uidMap.put(line.substring(begin, end), count);
                    } else {
                        uidMap.put(line.substring(begin, end), 1);
                    }

                }
                if (line.contains(STK2)) {
                    int uidBegin = line.indexOf("uid=");
                    int uidEnd = line.indexOf(",", uidBegin + 1);
                    int custBegin = line.indexOf("cust: [") + "cust: [".length();
                    int custEnd = line.indexOf(",", custBegin + 1);
                    Set set = new HashSet();
                    set.add(line.substring(custBegin, custEnd));
                    Result result = new Result();
                    result.setUid(line.substring(uidBegin, uidEnd));
                    result.setCustIdSet(set);
//                    custMap.put(line.substring(uidBegin, uidEnd), result);
                    custList.add(result);

                }
            }
        } catch (Exception e) {

        }
//        System.out.println("uidMap:" + uidMap);
//        System.out.println("custMap:" + custMap);
//        sortPushConsole();
        addUserId();
    }


    /**
     * 将日志降序排列
     */
    private void sortPushConsole() {
        List<Map.Entry<String, Integer>> mapList = null;
        mapList = new ArrayList<>(finalMap.entrySet());
        Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        for (Map.Entry<String, Integer> stringStringEntry : mapList) {
            aaMap.put(stringStringEntry.getKey().replace("\r\n", ""), stringStringEntry.getValue());
        }
//		System.out.println("lastMap:" + lastMap);
//        addUserId();
    }

    private void addUserId() {
        // lastMap,retMap
        System.out.println("uidMap:"+uidMap);
        System.out.println("custList:"+custList);
        boolean contain = false;
        for (Result result : custList) {

//        }
//        for (Object o : custMap.keySet()) {
            for (Map.Entry<String, Integer> entryList : uidMap.entrySet()) {
                if (uidMap.containsKey(result.getUid()) && !isEmptyArray(result.getCustIdSet())) {
                    contain = true;
                }
                if (contain) {
//                    System.out.println("有匹配");
                    finalMap.put(entryList.getKey() + "<有>" + result.getCustIdSet(), entryList.getValue());
                } else {
//					System.out.println("无匹配");
                    finalMap.put(entryList.getKey()+"<无>",entryList.getValue());
                }
            }
        }
        sortPushConsole();

    }

    /**
     * 输出日志
     *
     * @throws IOException
     */
    private void outPutLog() throws IOException {
        if (aaMap.size() == 0 | aaMap.isEmpty() | aaMap == null) {
            sortPushConsole();
        }
        uidMap.clear();
        lastMap.clear();
        FileOutputStream fileOutputStream = new FileOutputStream(outPutLogPath);
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        for (Map.Entry<?, ?> stringEntry : aaMap.entrySet()) {
            printWriter.write(stringEntry.getKey().toString().replace(reg, "") + ",count:"
                    + stringEntry.getValue().toString().replace(reg, "") + reg);
        }
        printWriter.close();
        fileOutputStream.close();
    }


   public static boolean isEmptyArray(Set o){
           return o == null || o.size() <= 0;
   }

     class Result {
     private String uid;

     private Set<String> custIdSet;

     public String getUid() {
     return uid;
     }

     public void setUid(String uid) {
     this.uid = uid;
     }

     public Set<String> getCustIdSet() {
     return custIdSet;
     }

     public void setCustIdSet(Set<String> custIdSet) {
     this.custIdSet = custIdSet;
     }

     }
}
