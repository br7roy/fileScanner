package com.rust.component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Administrator
 * on 2017-08-02
 */
public class LogMonitorY {
    public static void main(String[] args) {

        String filiePath = "C:\\Users\\Administrator\\Desktop\\test5.log";
        String outPath = "";


        Map<String, Result> map = new LinkedHashMap<>();


        try {
            List<String> list = Files.readAllLines(Paths.get(filiePath));

            for (String line : list) {
                if (line.contains("(BasicLogger.java:68) - [c1,VISITOR_LOGIN]")) {

                    int custBgIndex = line.indexOf("custId=") + "custId".length();
//                    int custEdIndex = custBgIndex + 8;
                    int uidBgIndex = line.indexOf("uid=") + "uid=".length();
                    String uid = line.substring(uidBgIndex, line.indexOf(",", uidBgIndex));

                    Result result = map.get(uid);

                    if (result == null) {
                        result = new Result();
                        Set set = new HashSet();
                        String c = line.substring(custBgIndex, line.indexOf(",", custBgIndex));
                        set.add(c);
                        result.setCustIds(set);
                        result.setUid(uid);
                        if (0 == result.getUidCount()) {
                            result.setUidCount(1);
                        } else {
                            result.setUidCount(result.getUidCount() + 1);
                        }

                        map.put(uid, result);
                    } else {
                        if (0 != result.getUidCount()) {
                            result.setUidCount(result.getUidCount() + 1);
                        }
                        result.setUid(uid);
                        Set set = result.getCustIds();
                        set.add(line.substring(custBgIndex, line.indexOf(",", custBgIndex)));
                        result.setCustIds(set);
                        map.put(uid, result);
                    }

                }


            }
            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    static class Result {
        String uid;
        int uidCount;
        Set<String> custIds;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getUidCount() {
            return uidCount;
        }

        public void setUidCount(int uidCount) {
            this.uidCount = uidCount;
        }

        public Set<String> getCustIds() {
            return custIds;
        }

        public void setCustIds(Set<String> custIds) {
            this.custIds = custIds;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "uidCount=" + uidCount +
                    ", custIds=" + custIds +
                    '}';
        }
    }
}
