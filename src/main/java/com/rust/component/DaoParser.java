package com.rust.component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/7/25.
 */
public class DaoParser {

    public static void main(String[] args) {
        // Map<method,int[0]=count,int[1]=timeSum>
        long startTime = System.currentTimeMillis();
        Map<String, int[]> map = new HashMap<String, int[]>();
        String sep_method = "method=";
        String sep_timeUsed = "timeUsed: ";

        String pathFile = "C:\\Users\\Administrator\\Desktop\\testDao.log";
        String outFile = "C:\\Users\\Administrator\\Desktop\\daoResult2.log";

        try {
            for (String line : Files.readAllLines(Paths.get(pathFile))) {
                int idx = line.indexOf(sep_method) + sep_method.length();

                String key = line.substring(idx);
                int[] entry = map.get(key);

                if (entry == null) {
                    entry = new int[2];
                    map.put(key, entry);
                }

                entry[0] = entry[0] + 1;

                int idx2 = line.indexOf(sep_timeUsed) + sep_timeUsed.length();
                int idx3 = line.indexOf(";", idx2 + 1);

                int timeUsed = Integer.parseInt(line.substring(idx2, idx3));

                entry[1] = entry[1] + timeUsed;
            }





            Comparator<Map.Entry<String, int[]>> comparator = Comparator.comparingInt(left -> left.getValue()[0]);
            List<Map.Entry<String, int[]>> retList = map.entrySet().stream().sorted(comparator).collect(Collectors.toList());
            List<String> lineList = retList.stream().map(entry -> "method=" + entry.getKey().replace("\r\n", "") + ", count=" + entry.getValue()[0] + ", timeSum=" + entry.getValue()[1] ).collect(Collectors.toList());


            Files.write(Paths.get(outFile),lineList, StandardOpenOption.CREATE,StandardOpenOption.WRITE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("process finish..." + "耗时:[" + (System.currentTimeMillis() - startTime) + "]");
    }
}
