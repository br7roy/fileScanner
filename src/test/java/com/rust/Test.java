package com.rust;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
* Created by Administrator
* on 2017-07-21
*/
public class Test {

   public  String test() throws IOException {

       File file = new File(this.getClass().getClassLoader().getResource("test.log").getPath());

       FileInputStream inputStream = new FileInputStream(file);

       BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

       String len;
       StringBuffer buffer = new StringBuffer();

       while ((len = reader.readLine()) != null) {

		   buffer.append(System.getProperty("line.separator")).append(len);
       }
       System.out.println(buffer);

       inputStream.close();
       reader.close();


       return buffer.toString();
   }
   public static void main(String[] args) throws IOException {
       String hehe = "17.07.19 15:01:11,478 INFO [actRun-4] com.jjhgame.gf.basic.BasicLogger.info(BasicLogger.java:68) - [c20,CLIENT_INFO][sid=10]uid=e00cf12a3abfc56160a34fe3d76743707ec5a745, sessionId=10, clientIp=116.236.195.38";
//
//        System.out.println(hehe.substring(hehe.indexOf("uid=")));
       System.out.println(new Test().test());
//        String uid = "uid=fd4347d814bb9c85a85e592298df941d";
       String key = "uid=";
       int uuidLength = 40;
//        System.out.println(uid.substring(uid.indexOf("uid=") + key.length(), key.length() + 32));
       StringBuilder uidResult = new StringBuilder();
       StringBuilder temResult = new StringBuilder();
//        temResult.append(hehe.substring(hehe.indexOf(key)));
//        System.out.println("tempResult:" + temResult);
       System.out.println(hehe.substring(hehe.indexOf(key)).substring(0, hehe.substring(hehe.indexOf(key)).indexOf(",")));
//        System.out.println(uidResult.append(hehe.substring(hehe.indexOf(key),(hehe.substring(hehe.indexOf(key)).indexOf(",")))));
       Map map = new HashMap();
       map.put("haha", "hehe");
   }




}
