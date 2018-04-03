/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.bill.test;

/**
 *清结算系统结算日志筛选关键字支出金额
 * @author FUTANGHANG004
 * @version $Id: SettleBillBean, v 0.1 2018/3/27  FUTANGHANG004 Exp $
 */
public class SettleBillBean {
	/**
	 * 交易核心流水号
	 */
	private String transCoreTxnSsn;

	/**
	 * 交易时间
	 */
	private String transDateTime;

	/**
	 * 对账日期
	 */
	private String reconciliationDateTime;

	/**
	 * 交易类型
	 */
	private String txnType;

	/**
	 * 参与结算金额
	 */
	private String participateSettlementAmount;

	/**
	 * 支出金额
	 */
	private String amountPayout;

	/**
	 *手续费标识
	 */
	private String feeId;

	/**
	 * 业务场景
	 */
	private String bussinessScense;

	/**
	 * 结算日期
	 */
	private String settleDate;

	/**
	 * 会员号
	 */
	private String memberNo;

	/**
	 * 收入金额
	 */
	private String incomeAmt;

	/**
	 * 退货原消费交易核心订单号
	 */
	private String reverseCoreTxnSsn;

	/**
	 * 返还手续费
	 */
	private String returnFee;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 商户订单号
	 */
	private String merchantOrderNo;

	/**
	 * 业务订单号
	 */
	private String businessOrderNo;

	/**
	 * 应用订单号
	 */
	private String applicationOrderNo;

	/**
	 * 商品明细
	 */
	private String productDetail;

	/**
	 * 非积分金额
	 */
	private String nonScoreAmt;

	/**
	 * 积分金额
	 */
	private String scoreAmt;


    public String getTransCoreTxnSsn() {
        return transCoreTxnSsn;
    }

    public void setTransCoreTxnSsn(String transCoreTxnSsn) {
        this.transCoreTxnSsn = transCoreTxnSsn;
    }

    public String getTransDateTime() {
        return transDateTime;
    }

    public void setTransDateTime(String transDateTime) {
        this.transDateTime = transDateTime;
    }

    public String getReconciliationDateTime() {
        return reconciliationDateTime;
    }

    public void setReconciliationDateTime(String reconciliationDateTime) {
        this.reconciliationDateTime = reconciliationDateTime;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getParticipateSettlementAmount() {
        return participateSettlementAmount;
    }

    public void setParticipateSettlementAmount(String participateSettlementAmount) {
        this.participateSettlementAmount = participateSettlementAmount;
    }

    public String getAmountPayout() {
        return amountPayout;
    }

    public void setAmountPayout(String amountPayout) {
        this.amountPayout = amountPayout;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public String getBussinessScense() {
        return bussinessScense;
    }

    public void setBussinessScense(String bussinessScense) {
        this.bussinessScense = bussinessScense;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getIncomeAmt() {
        return incomeAmt;
    }

    public void setIncomeAmt(String incomeAmt) {
        this.incomeAmt = incomeAmt;
    }

    public String getReverseCoreTxnSsn() {
        return reverseCoreTxnSsn;
    }

    public void setReverseCoreTxnSsn(String reverseCoreTxnSsn) {
        this.reverseCoreTxnSsn = reverseCoreTxnSsn;
    }

    public String getReturnFee() {
        return returnFee;
    }

    public void setReturnFee(String returnFee) {
        this.returnFee = returnFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMerchantOrderNo() {
        return merchantOrderNo;
    }

    public void setMerchantOrderNo(String merchantOrderNo) {
        this.merchantOrderNo = merchantOrderNo;
    }

    public String getBusinessOrderNo() {
        return businessOrderNo;
    }

    public void setBusinessOrderNo(String businessOrderNo) {
        this.businessOrderNo = businessOrderNo;
    }

    public String getApplicationOrderNo() {
        return applicationOrderNo;
    }

    public void setApplicationOrderNo(String applicationOrderNo) {
        this.applicationOrderNo = applicationOrderNo;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getNonScoreAmt() {
        return nonScoreAmt;
    }

    public void setNonScoreAmt(String nonScoreAmt) {
        this.nonScoreAmt = nonScoreAmt;
    }

    public String getScoreAmt() {
        return scoreAmt;
    }

    public void setScoreAmt(String scoreAmt) {
        this.scoreAmt = scoreAmt;
    }

    @Override
    public String toString() {
        return "SettleBillBean{" +
                "transCoreTxnSsn='" + transCoreTxnSsn + '\'' +
                ", transDateTime='" + transDateTime + '\'' +
                ", reconciliationDateTime='" + reconciliationDateTime + '\'' +
                ", txnType='" + txnType + '\'' +
                ", participateSettlementAmount='" + participateSettlementAmount + '\'' +
                ", amountPayout='" + amountPayout + '\'' +
                ", feeId='" + feeId + '\'' +
                ", bussinessScense='" + bussinessScense + '\'' +
                ", settleDate='" + settleDate + '\'' +
                ", memberNo='" + memberNo + '\'' +
                ", incomeAmt='" + incomeAmt + '\'' +
                ", reverseCoreTxnSsn='" + reverseCoreTxnSsn + '\'' +
                ", returnFee='" + returnFee + '\'' +
                ", remark='" + remark + '\'' +
                ", merchantOrderNo='" + merchantOrderNo + '\'' +
                ", businessOrderNo='" + businessOrderNo + '\'' +
                ", applicationOrderNo='" + applicationOrderNo + '\'' +
                ", productDetail='" + productDetail + '\'' +
                ", nonScoreAmt='" + nonScoreAmt + '\'' +
                ", scoreAmt='" + scoreAmt + '\'' +
                '}';
    }
}
