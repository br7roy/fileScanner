package com.rust.component;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: LogMonitorX
 * Author: Rust
 * Date: 2017/7/21
 * Description:
 */
public class LogMonitorX {
    public static void main(String[] args) throws IOException {
        // 原日志路径
        String filePath = "C:\\Users\\Administrator\\Desktop\\test2.log";
        // 生成日志路径
        String genPath = "C:\\Users\\Administrator\\Desktop\\result.log";
        LogMonitorX logMonitor = new LogMonitorX(filePath, genPath);
        // 控制台输出
//		logMonitor.genLog();
        // 生成文件
        logMonitor.outPutLog();
        System.out.println("=======success=======");
    }

    private static String inPutFilePath;
    private static String outPutLogPath;
    private static Map<String, Integer> uidMap = new TreeMap<>();
    private static Map<String, Integer> sortMap = new LinkedHashMap<>();
    private static Map<String, List<String>> costMap = new TreeMap<>();


    private LogMonitorX(String filePath, String _outPath) {
        File file = new File(filePath);
        if (!file.isFile()) {
            throw new RuntimeException("尝试从[" + filePath + "]读取原日志文件失败，请重新确认路径");
        }
        inPutFilePath = filePath;
        outPutLogPath = _outPath;
        filterLog();
    }

    /**
     * 筛选日志
     */
    private void filterLog() {
        List<String> list = new ArrayList();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(new File(inPutFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("尝试从[" + inPutFilePath + "]读取原日志文件失败，请重新确认路径");
        }

        try {
            String line,uid="",costId="";
            String uidReg = "(?<=uid=)[^=,]*",coutReg="(?<=custId=)[\\d]*";
            Pattern uidPa = Pattern.compile(uidReg);
            Pattern constPa = Pattern.compile(coutReg);
            Matcher matcher;
            while ((line=reader.readLine())!=null) {
                matcher = uidPa.matcher(line);
                while (matcher.find()) {
                    uid = matcher.group();
                }
                if(uid!=null&&!"null".equalsIgnoreCase(uid)){
                    if (uidMap.containsKey(uid)) {
                        int count = uidMap.get(uid) + 1;
                        uidMap.put(uid, count);
                    } else {
                        uidMap.put(uid, 1);
                    }
//                    System.out.println("uid:"+uid);
                }
                matcher = constPa.matcher(line);
                List<String> costList = costMap.get(uid);
                while (matcher.find()) {
                    costId = matcher.group();
                    if(costId!=null&&!"null".equalsIgnoreCase(costId)) {
                        if (costList==null){
                            costList = new ArrayList<>();
                            costMap.put(uid, costList);
                        }
                        if (!costList.contains(costId)) {
                            costList.add(costId);
                        }
//                        System.out.println("costId:"+costId);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("this log is illegal...");
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sortPushConsole();
    }

    /**
     * 将日志降序排列
     */
    private void sortPushConsole() {
        List<Map.Entry<String, Integer>> mapList = null;
        mapList = new LinkedList<>(uidMap.entrySet());
        mapList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        uidMap.clear();
        for (Map.Entry<String, Integer> stringStringEntry : mapList) {
            sortMap.put(stringStringEntry.getKey().replace("\r\n", ""), stringStringEntry.getValue());
        }
    }

    /**
     * 输出日志
     *
     * @throws IOException
     */
    private void outPutLog() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outPutLogPath)));
        StringBuffer line = new StringBuffer();
        if(sortMap!=null){
            line = new StringBuffer();
            System.out.println(sortMap.size());
            System.out.println(costMap.size());
            for (String uid : sortMap.keySet()) {
                line.append("uid:"+uid+";");
                if (costMap.get(uid) != null) {
                    line.append("costId:"+Arrays.toString(costMap.get(uid).toArray())+";");
                }
                line.append("count:"+sortMap.get(uid)+"\r\n");
            }
        }
        writer.write(line.toString());
        writer.flush();
        writer.close();
    }

}
