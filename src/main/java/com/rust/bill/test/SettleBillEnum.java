/**
 * 壹钱包
 * Copyright (c) 2013-2018 壹钱包版权所有.
 */
package com.rust.bill.test;

/**
 *
 * @author FUTANGHANG004
 * @version $Id: SettleBillEnum, v 0.1 2018/4/2  FUTANGHANG004 Exp $
 */
public enum SettleBillEnum {
	/**
	 * 交易核心流水号
	 */
	 transCoreTxnSsn,

	/**
	 * 交易时间
	 */
	 transDateTime,

	/**
	 * 对账日期
	 */
	 reconciliationDateTime,

	/**
	 * 交易类型
	 */
	 txnType,

	/**
	 * 参与结算金额
	 */
	 participateSettlementAmount,

	/**
	 * 支出金额
	 */
	 amountPayout,

	/**
	 *手续费标识
	 */
	 feeId,

	/**
	 * 业务场景
	 */
	 bussinessScense,

	/**
	 * 结算日期
	 */
	 settleDate,

	/**
	 * 会员号
	 */
	 memberNo,

	/**
	 * 收入金额
	 */
	 incomeAmt,

	/**
	 * 退货原消费交易核心订单号
	 */
	 reverseCoreTxnSsn,

	/**
	 * 返还手续费
	 */
	 returnFee,

	/**
	 * 备注
	 */
	 remark,

	/**
	 * 商户订单号
	 */
	 merchantOrderNo,

	/**
	 * 业务订单号
	 */
	 businessOrderNo,

	/**
	 * 应用订单号
	 */
	 applicationOrderNo,

	/**
	 * 商品明细
	 */
	 productDetail,

	/**
	 * 非积分金额
	 */
	 nonScoreAmt,

	/**
	 * 积分金额
	 */
	 scoreAmt,;

	SettleBillEnum() {
	}

	public static void main(String[] args) {
		System.out.println(SettleBillEnum.transDateTime.ordinal());
		System.out.println(SettleBillEnum.scoreAmt.ordinal());
		System.out.println(SettleBillEnum.returnFee.toString());


	}




}
