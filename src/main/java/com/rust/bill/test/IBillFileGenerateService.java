package com.rust.bill.test;


/**
 * Desc:账单生成接口
 * Create by: JINZHONGYUAN811
 * Create date: 2017/9/7 17:44
 */
public interface IBillFileGenerateService {

    void generateFile(String recordId) throws Exception;

}
