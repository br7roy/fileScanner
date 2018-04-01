/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.bill.test;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *合并支付结算单详情字段
 * @author FUTANGHANG004
 * @version $Id: DetailSettleBillBean, v 0.1 2018/3/28  FUTANGHANG004 Exp $
 */
public class DetailSettleBillBean implements Serializable {
	private static final long serialVersionUID = 1L;
	//	"商户号", "结算日期", "商户订单号", "收单订单号", "壹钱包交易号", "交易时间", "订单金额", "手续费", "结算金额"
	/**
	 *
	 */
	private String merchantNo;

	/**
	 * 结算日期
	 * 产品prd(20171128)
	 */
	private String settleDate;

	/**
	 * 商户订单号
	 */
	private String merchantOrderNo;

	/**
	 * 收单订单号
	 */
	private String checkOutOrderNo;

	/**
	 * 壹钱包交易号
	 */
	private String yqbTransNo;

	/**
	 * 交易时间
	 * prd(20171121183029)
	 */
	private String transDateTime;

	/**
	 * 订单金额
	 */
	private BigDecimal orderAmt;

	/**
	 * 手续费
	 */
	private BigDecimal feeAmt;

	/**
	 * 结算金额
	 */
	private String settleAmt;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	public String getCheckOutOrderNo() {
		return checkOutOrderNo;
	}

	public void setCheckOutOrderNo(String checkOutOrderNo) {
		this.checkOutOrderNo = checkOutOrderNo;
	}

	public String getYqbTransNo() {
		return yqbTransNo;
	}

	public void setYqbTransNo(String yqbTransNo) {
		this.yqbTransNo = yqbTransNo;
	}

	public String getTransDateTime() {
		return transDateTime;
	}

	public void setTransDateTime(String transDateTime) {
		this.transDateTime = transDateTime;
	}

	public BigDecimal getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(BigDecimal orderAmt) {
		this.orderAmt = orderAmt;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public String getSettleAmt() {
		return settleAmt;
	}

	public void setSettleAmt(String settleAmt) {
		this.settleAmt = settleAmt;
	}
}
