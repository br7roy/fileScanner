/**
 * 壹钱包
 * Copyright (c) 2013-2017 壹钱包版权所有.
 */
package com.rust.bill.test;

/**
 * @author jinzhongyuan811
 * @version $Id: BaseBill , v 0.1 2018年01月02日  jinzhongyuan811 Exp $
 */
public class BaseBill {


    /**
     * recordId
     */
    protected String recordId;
    /**
     * 商户号
     */
    protected String merchantNo;

    /**
     * 会员编号
     */
    protected String customerId;


    /**
     * operatorId
     */
    protected String operatorId;

    /**
     * 开始时间
     */
    protected String startDate;
    /**
     * 结束时间
     */
    protected String endDate;

    /**
     * 账单类型
     */
    protected String billType;

    /**
     * 是否需要压缩 默认需要压缩，若不需要压缩，子类需重新set方法
     */
    public boolean needZip = true;

    /**
     * 是否需要汇总统计 默认不需要统计
     */
    protected boolean needStatistic = false;

    @Override
    public String toString() {
        return "BaseBill{" +
                "recordId='" + recordId + '\'' +
                ", merchantNo='" + merchantNo + '\'' +
                ", customerId='" + customerId + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", billType='" + billType + '\'' +
                ", needZip=" + needZip +
                ", needStatistic=" + needStatistic +
                '}';
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public boolean getNeedZip() {
        return needZip;
    }

    public void setNeedZip(boolean needZip) {
        this.needZip = needZip;
    }

    public boolean getNeedStatistic() {
        return needStatistic;
    }

    public void setNeedStatistic(boolean needStatistic) {
        this.needStatistic = needStatistic;
    }
}
