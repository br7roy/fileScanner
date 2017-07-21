package com.rust.component;

import java.io.*;
import java.util.*;

/**
 * FileName:    LogMonitor
 * Author:      Rust
 * Date:        2017/7/21
 * Description:
 */
public class LogMonitor {
    public static void main(String[] args) throws IOException {
        //原日志路径
        String filePath = "C:\\Users\\Administrator\\Desktop\\test.log";
        //生成日志路径
        String genPath = "C:\\Users\\Administrator\\Desktop\\result.log";
        LogMonitor logMonitor = new LogMonitor(filePath, genPath);

        //控制台输出
//        logMonitor.genLog();
        //生成文件
        logMonitor.outPutLog();

    }

    private static File file;
    private static String outPutLogPath;

    private static final String stK1 = "[c20,CLIENT_INFO]";
    private static final String stK2 = "uid=";

    private static StringBuilder uidResult;

    private static Map<String, String> uidMap = new TreeMap<>();
    private static Map<String, String> lastMap = new LinkedHashMap<>();


    private LogMonitor(String filePath, String _outPath) {
        file = new File(filePath);
        if (!file.isFile()) {
            throw new RuntimeException("尝试从[" + filePath + "]读取原日志文件失败，请重新确认路径");
        }
        outPutLogPath = _outPath;
        filterLog();
        genLog();
    }

    /**
     * 筛选日志
     */
    private void filterLog() {
        uidResult = new StringBuilder();
        int flg = -1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                if (s.contains(stK1) && s.contains(stK2)) {
                    try {
                        if (uidResult == null) {
                            uidResult.append(s.substring(s.indexOf(stK2)).substring(0, s.substring(s.indexOf(stK2)).indexOf(","))).append(",");
                        } else {
                            uidResult.append(System.lineSeparator()).append(s.substring(s.indexOf(stK2)).substring(0, s.substring(s.indexOf(stK2)).indexOf(","))).append(",");
                        }
                    } catch (RuntimeException e) {
                        if (flg != 0) {
                            System.out.println("检测到特殊日志无逗号结尾");
                            uidResult.append(System.lineSeparator()).append(s.substring(s.indexOf(stK2))).append(",");
                        }
                        ++flg;
                    }
                }
            }
            br.close();
        } catch (Exception ignored) {
        }
    }


    /**
     * 生成整理完的日志
     *
     * @return 一组Entry展示1.uid=value,2.uid出现的次数（未排序）
     */
    private void genLog() {
        String resStr = uidResult.toString();
        String[] strings = resStr.split(",");
        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                char c1 = o1.charAt(0);
                char c2 = o2.charAt(0);
                return c1 - c2;
            }
        });
        Set<String> setData = new HashSet<>();
        Collections.addAll(setData, strings);
        //计算相同数据出现的次数
        Map<String, Integer> countMap = new HashMap<>();
        for (String string : strings) {
            if (countMap.containsKey(string)) {
                //如果存在那么获取value自增
                int count = countMap.get(string) + 1;
                countMap.put(string, count);
            } else {
                countMap.put(string, 1);
            }
        }
        for (String data : setData) {
            uidMap.put(data + ",count", String.valueOf(countMap.get(data)));
        }
        sortPushConsole();
    }

    /**
     * 将日志降序排列
     */
    private void sortPushConsole() {
        List<Map.Entry<String, String>> mapList = null;
        mapList = new ArrayList<>(uidMap.entrySet());
        Collections.sort(mapList, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }

        });
        for (Map.Entry<String, String> stringStringEntry : mapList) {
            lastMap.put(stringStringEntry.getKey(), stringStringEntry.getValue());
        }
        System.out.println("lastMap:" + lastMap);
    }

    /**
     * 输出日志
     *
     * @throws IOException
     */
    private void outPutLog() throws IOException {
        if (lastMap.size() == 0 | lastMap.isEmpty() | lastMap == null) {
            sortPushConsole();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(outPutLogPath);
        PrintWriter printWriter = new PrintWriter(fileOutputStream);
        for (Map.Entry<?, ?> stringEntry : lastMap.entrySet()) {
            printWriter.write(stringEntry.getKey() + ":" + stringEntry.getValue());
        }
        printWriter.close();
        fileOutputStream.close();
    }


}


