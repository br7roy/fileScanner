/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.bill.test;

import java.io.Serializable;

/**
 *合并支付结算单汇总字段
 * @author FUTANGHANG004
 * @version $Id: TotalSettleBillBean, v 0.1 2018/3/27  FUTANGHANG004 Exp $
 */
public class TotalSettleBillBean implements Serializable {
	private static final long serialVersionUID = 1L;
	//	"商户号", "起始时间", "终止时间", "单位", "币种", "订单总金额", "手续费总金额", "文件生成时间"


	public TotalSettleBillBean() {
		this.currency = "人民币";
		this.unit = "元";
	}

	/**
	 * 商户号
	 */
	private String merchantNo;
	/**
	 * 起始时间
	 * prd上格式（2017年07月01日 00:00:00）
	 */
	private String startDate;
	/**
	 * 终止时间
	 * prd上格式（2017年07月01日 00:00:00）
	 */
	private String endDate;

	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 币种
	 */
	private String currency;

	/**
	 * 订单总金额
	 */
	private String orderTotalAmt;

	/**
	 * 手续费总金额
	 */
	private String totalFeeAmt;

	/**
	 * 文件生成时间
	 * prd格式（2017年08月16日 13:46:06）
	 */
	private String fileCrtDateTime;

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getStartDate() {
		return startDate;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOrderTotalAmt() {
		return orderTotalAmt;
	}

	public void setOrderTotalAmt(String orderTotalAmt) {
		this.orderTotalAmt = orderTotalAmt;
	}

	public String getTotalFeeAmt() {
		return totalFeeAmt;
	}

	public void setTotalFeeAmt(String totalFeeAmt) {
		this.totalFeeAmt = totalFeeAmt;
	}

	public String getFileCrtDateTime() {
		return fileCrtDateTime;
	}

	public void setFileCrtDateTime(String fileCrtDateTime) {
		this.fileCrtDateTime = fileCrtDateTime;
	}
}
