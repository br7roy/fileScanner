/*
* Package com.rust.test0414 
* FileName: GoogleFilesName
* Author:   Rust
* Date:     2018/4/14 23:57
*/
package com.rust.test0414;

import com.google.common.io.Files;

import java.io.File;

/**
 * FileName:    GoogleFilesName
 * Author:      Rust
 * Date:        2018/4/14
 * Description:
 */
public class GoogleFilesName {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Administrator\\nfsc\\pafb_pspt_id948713_vol1\\test.log");
        if (!file.exists()) {
            System.out.println(false);
            System.exit(0);
        }
        String otherPath = file.getPath();
        System.out.println(otherPath);
        System.out.println(file.getName());
        System.out.println(Files.getFileExtension(file.getPath()));
        System.out.println(file.getPath() + Files.getNameWithoutExtension(file.getPath()));
        System.out.println(file.getPath().replaceAll("[.][^.]+$", ""));

    }
}
