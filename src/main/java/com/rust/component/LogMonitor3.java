package com.rust.component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator
 * on 2017-07-24
 */
public class LogMonitor3 {
    private static String inPutPath;
    private static String outPutPath;
    private String reg = "\\[c20,CLIENT_INFO\\]\\[sid=[0-9]+]uid=[0-9a-zA-Z],";

    public LogMonitor3(String _inPutPath, String _outPutPath) {
        File file = new File(_inPutPath);
        if (!file.isFile()) {
            throw new RuntimeException("请确认读取文件路径:[" + _inPutPath + "]");
        }
        inPutPath = _inPutPath;
        outPutPath = _outPutPath;
        readLogFile();

    }

    private void readLogFile() {
        List<String> list = new ArrayList<>();
        try {
             list = Files.readAllLines(Paths.get(inPutPath), Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.out.println("读取文件路径错误");
        }
        for (String s : list) {
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(s.trim());
            if (matcher.find()) {
                System.out.println("hehe");
                System.out.println(s);
                break;
            }








        }





    }

    public static void main(String[] args) {
        // 原日志路径
        String filePath = "C:\\Users\\Administrator\\Desktop\\test5.log";
        // 生成日志路径
        String genPath = "C:\\Users\\Administrator\\Desktop\\result.log";
        new LogMonitor3(filePath, genPath);

    }
}
