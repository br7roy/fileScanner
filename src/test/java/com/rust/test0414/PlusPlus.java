/*
* Package com.rust.test0414 
* FileName: PlusPlus
* Author:   Rust
* Date:     2018/4/14 23:48
*/
package com.rust.test0414;

/**
 * FileName:    PlusPlus
 * Author:      Rust
 * Date:        2018/4/14
 * Description:
 */
public class PlusPlus {
    public static void main(String[] args) {
        int start = 0;
        int end = 0;
        start = end + 1;
        System.out.println(start);

        start = 0;
        end = 0;

        start = ++end;
        System.out.println(start);
        start = 0;
        end = 0;
        start = end++;
        System.out.println(start);


    }
}
