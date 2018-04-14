/*
* Package com.rust 
* FileName: TestBuffer
* Author:   Rust
* Date:     2018/3/30 22:24
*/
package com.rust;

import com.google.common.io.Files;
import org.junit.Test;
import sun.misc.Cleaner;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.List;

import static org.apache.commons.lang.CharEncoding.UTF_8;

/**
 * FileName:    TestBuffer
 * Author:      Rust
 * Date:        2018/3/30
 * Description:
 */
public class TestBuffer {
    public static final String dot = File.separator;
    public static final String smallPath = System.getProperty("user.home") + dot + "nas" + dot + "20180101" + dot + "920000164391_20180328_227708 - 副本.txt";
    public static final String bigPath = System.getProperty("user.home") + dot + "nas" + dot + "20180101" + dot + "900000164395_20180328_227708.txt";
    public static final String outPath = System.getProperty("user.home") + dot + "nas" + dot + "out" + "out.txt";

    @Test
    public void testBufferReader() throws IOException {
        long start = System.currentTimeMillis();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(bigPath)));
        // StringBuilder stringBuilder = new StringBuilder();
        int len = 0;
        for (String buff; (buff = bufferedReader.readLine()) != null; ) {
            // stringBuilder.append(buff);
            ++len;
        }
        // System.out.println(stringBuilder.toString());

        System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", System.currentTimeMillis() - start));

    }

    @Test
    public void testGuava() throws IOException {
        long start = System.currentTimeMillis();
        File file = new File(bigPath);
        List<String> strings = Files.readLines(file, Charset.forName(UTF_8));

        System.out.println(strings);
        long end = System.currentTimeMillis();
        System.out.println(String.format("\n===>文件大小：%s 字节", file.length()));
        System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", end - start));
    }


    /**
     * 大文件速度最快方式
     *
     * @throws IOException
     */
    @Test
    public void testMappedByteBuffer() throws IOException {
        long start = System.currentTimeMillis();

        int allocate = 1024;

        RandomAccessFile fis = new RandomAccessFile(new File(bigPath), "rw");
        FileChannel channel = fis.getChannel();
        long size = channel.size();

        // 构建一个只读的MappedByteBuffer
        final MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, size);
        // 如果文件不大,可以选择一次性读取到数组
        // byte[] all = new byte[(int)size];
        // mappedByteBuffer.get(all, 0, (int)size);
        // 打印文件内容
        // System.out.println(new String(all));

        // 如果文件内容很大,可以循环读取,计算应该读取多少次
        byte[] bytes = new byte[allocate];
        long cycle = size / allocate;
        int mode = (int) (size % allocate);
        for (int i = 0; i < cycle; i++) {
            // 每次读取allocate个字节
            mappedByteBuffer.get(bytes);
            // 打印文件内容,关闭打印速度会很快
            //  System.out.println(new String(bytes));
        }
        if (mode > 0) {
            bytes = new byte[mode];
            mappedByteBuffer.get(bytes);

            // 打印文件内容,关闭打印速度会很快
            //  System.out.print(new String(bytes));
        }

        // 关闭通道和文件流
        channel.close();
        fis.close();

        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    Method method = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
                    method.setAccessible(true);
                    sun.misc.Cleaner cleaner = (Cleaner) method.invoke(mappedByteBuffer, new Object[0]);
                    cleaner.clean();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        long end = System.currentTimeMillis();
        System.out.println(String.format("\n===>文件大小：%s 字节", size));
        System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", end - start));

    }

    /**
     * 使用byteBuffer性能比mappedByteBuffer差
     * @throws IOException
     */
    @Test
    public void testByteBuffer() throws IOException {
        long startTime = System.currentTimeMillis();
        int bufSize = 100;
        File fin = new File(TestBuffer.bigPath);
        File fout = new File(TestBuffer.outPath);

        FileChannel fcin = new FileInputStream(fin).getChannel();
        ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);

        // FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel();
        // ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);


        readFileByLine(bufSize, fcin, rBuffer);
        System.out.println(String.format("\n===>文件大小：%s 字节", fcin.size()));
        System.out.print("OK!!! cost:" + (System.currentTimeMillis() - startTime) + "毫秒");


    }

    private static String readFileByLine(int bufSize, FileChannel fcin, ByteBuffer rBuffer) {
        String enterStr = "\n";
        StringBuffer strBuf = new StringBuffer("");
        try {
            byte[] bs = new byte[bufSize];

            int size = 0;

            //while((size = fcin.read(buffer)) != -1){
            // 还有内容
            while (fcin.read(rBuffer) != -1) {
                //当前rBuffer的指针位置
                int rSize = rBuffer.position();
                //把position设为0，mark设为-1，不改变limit的值
                rBuffer.rewind();
                //从position=0的位置开始相对读，读bs.length(bufSize)个byte，并写入dst下标从offset(0)到offset+length的区域
                rBuffer.get(bs);
                //position = 0;limit = capacity;mark = -1;  有点初始化的味道，但是并不影响底层byte数组的内容
                rBuffer.clear();
                String tempString = new String(bs, 0, rSize);
                // System.out.println(tempString);
                //System.out.print("<200>");

                int fromIndex = 0;
                int endIndex = 0;
                while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
                    //一行读完，有\n
                    String line = tempString.substring(fromIndex, endIndex);
                    line = new String(strBuf.toString() + line);
                    // System.out.println(line);
                    //System.out.print("</over/>");
                    //write to anthone file
                    // writeFileByLine(fcout, wBuffer, line);


                    strBuf.delete(0, strBuf.length());
                    fromIndex = endIndex + 1;
                }
                if (rSize > tempString.length()) {
                    strBuf.append(tempString.substring(fromIndex, tempString.length()));
                } else {
                    strBuf.append(tempString.substring(fromIndex, rSize));
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return strBuf.toString();
    }

    public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line) {
        try {
            //write on file head
            //fcout.write(wBuffer.wrap(line.getBytes()));
            //wirte append file on foot
            fcout.write(wBuffer.wrap(line.getBytes()), fcout.size());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 使用优化的MappedByteBuffer来处理逐行读取文件
     */
    @Test
    public void enhanceMappedByteBuffer4ReadLine() throws IOException {

        long start = System.currentTimeMillis();

        int allocate = 20000;

        RandomAccessFile fis = new RandomAccessFile(new File(bigPath), "rw");
        FileChannel channel = fis.getChannel();
        long size = channel.size();

        String enterStr = "\n";
        StringBuffer strBuf = new StringBuffer("");
        // 构建一个只读的MappedByteBuffer
        final MappedByteBuffer mappedByteBuffer = channel.map(MapMode.PRIVATE, 0, allocate);
        byte[] bs = new byte[allocate];
        while (channel.read(mappedByteBuffer) != -1) {
            //当前rBuffer的指针位置
            int rSize = mappedByteBuffer.position();
            //把position设为0，mark设为-1，不改变limit的值
            mappedByteBuffer.rewind();
            //从position=0的位置开始相对读，读bs.length(bufSize)个byte，并写入dst下标从offset(0)到offset+length的区域
            mappedByteBuffer.get(bs);
            //position = 0;limit = capacity;mark = -1;  有点初始化的味道，但是并不影响底层byte数组的内容
            mappedByteBuffer.clear();
            String tempString = new String(bs, 0, rSize);

            int fromIndex = 0;
            int endIndex = 0;
            while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
                //一行读完，有\n
                String line = tempString.substring(fromIndex, endIndex);
                line = new String(strBuf.toString() + line);
                // System.out.println(line);
                //System.out.print("</over/>");
                //write to anthone file
                // writeFileByLine(fcout, wBuffer, line);


                strBuf.delete(0, strBuf.length());
                fromIndex = endIndex + 1;
            }
            if (rSize > tempString.length()) {
                strBuf.append(tempString.substring(fromIndex, tempString.length()));
            } else {
                strBuf.append(tempString.substring(fromIndex, rSize));
            }




        }



        // 关闭通道和文件流
        channel.close();
        fis.close();

        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            @Override
            public Object run() {
                try {
                    Method method = mappedByteBuffer.getClass().getMethod("cleaner", new Class[0]);
                    method.setAccessible(true);
                    sun.misc.Cleaner cleaner = (Cleaner) method.invoke(mappedByteBuffer, new Object[0]);
                    cleaner.clean();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });

        long end = System.currentTimeMillis();
        System.out.println(String.format("\n===>文件大小：%s 字节", size));
        System.out.println(String.format("===>读取并打印文件耗时：%s毫秒", end - start));





    }
}