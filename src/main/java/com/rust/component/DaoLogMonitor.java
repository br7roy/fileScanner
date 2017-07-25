package com.rust.component;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FileName: LogMonitorX
 * Author: Rust
 * Date: 2017/7/25
 * Description:
 */
public class DaoLogMonitor {

    public static void main(String[] args) throws IOException {
        long startTime = System.currentTimeMillis();
        // 原日志路径
        String filePath = "C:\\Users\\Administrator\\Desktop\\testDao.log";
        // 生成日志路径
        String genPath = "C:\\Users\\Administrator\\Desktop\\daoResult.log";
        DaoLogMonitor daoLogMonitor = new DaoLogMonitor(filePath, genPath);
        daoLogMonitor.outPutLog();
        System.out.println("process finish..." + "耗时:[" + (System.currentTimeMillis() - startTime) + "]");

    }


    private File file = null;
    private  String inPutFilePath;
    private  String outPutLogPath;
    private static Map<String, Integer> methodMap = new TreeMap<>();
    private static Map<String, Integer> sortMethodMap = new LinkedHashMap<>();
    private static Map<String, Integer> costMap = new TreeMap<>();


    public DaoLogMonitor(String _filePath, String _outPath) {
            File file = new File(_filePath);
            if (!file.isFile()) {
                throw new RuntimeException("尝试从[" + _filePath + "]读取原日志文件失败，请重新确认路径");
            }
        this.file = file;
            inPutFilePath = _filePath;
            outPutLogPath = _outPath;
            filterLog();
    }

    /**
     * 筛选日志
     */
    private void filterLog() {

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("尝试从[" + inPutFilePath + "]读取原日志文件失败，请重新确认路径");
        }
        try {

            String line, method = "";
            Integer timeUsed;
            String methodReg = "method=[\\s\\S]*";
            String timeUsedReg = "timeUsed: [0-9]\\d";
            Pattern methodPa = Pattern.compile(methodReg);
            Pattern timeUsedPa = Pattern.compile(timeUsedReg);
            Matcher matcher;
            while ((line = bufferedReader.readLine()) != null) {
                matcher = methodPa.matcher(line);
                while (matcher.find()) {
                    method = matcher.group();
                }
                if (!isBlank(method)) {
                    if (methodMap.containsKey(method)) {
                        int count = methodMap.get(method) + 1;
                        methodMap.put(method, count);
                    } else {
                        methodMap.put(method, 1);
                    }
                }
                matcher = timeUsedPa.matcher(line);
                Integer curTime = costMap.get(method);
                while (matcher.find()) {
                    timeUsed = Integer.parseInt(matcher.group().replace("timeUsed: ", ""));

                    if (curTime == null) {
                        costMap.put(method, timeUsed);
                    } else {
                        costMap.put(method, curTime + timeUsed);
                    }

                }

            }

        } catch (Exception e) {
            System.out.println("this log is illegal...");
        }
        try {
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sortPushConsole();
    }



    /**
     * 将日志降序排列
     */
    private void sortPushConsole() {
        List<Map.Entry<String, Integer>> mapList = null;
        mapList = new LinkedList<>(methodMap.entrySet());
        mapList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        methodMap.clear();
        for (Map.Entry<String, Integer> stringStringEntry : mapList) {
            sortMethodMap.put(stringStringEntry.getKey().replace("\r\n", ""), stringStringEntry.getValue());
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
        if(sortMethodMap!=null){
            line = new StringBuffer();
            System.out.println(sortMethodMap.size());
            System.out.println(costMap.size());
            for (String method : sortMethodMap.keySet()) {
                line.append("method:").append(method).append(";");
                line.append("count:").append(sortMethodMap.get(method)).append(";");
                line.append("timeUsed:").append(costMap.get(method)).append(";").append("\r\n");
            }
        }
        writer.write(line.toString());
        writer.flush();
        writer.close();
    }












    static boolean isBlank(String var) {

        return null == var || "".equals(var) || "null".equalsIgnoreCase(var);
    }

     static boolean isEmptyMap(Map<?, ?> value) {
        return value == null || value.size() <= 0;
    }
//    static boolean isNotBlanInt(int $i) {
//        return i=0
//    }

}
