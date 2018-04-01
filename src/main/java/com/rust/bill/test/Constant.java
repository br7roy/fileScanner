package com.rust.bill.test;

/**
 * Desc:Todo
 * Create by: JINZHONGYUAN811
 * Create date: 2017/9/7 9:58
 */
public interface Constant {

    /**
     * 下划线字符
     */
    String CHAR_UNDERLINE="_";
    String CHAR_TAB="\t";
    String CHAR_EMPTY_STRING="";


    /**
     * 保险商户标识
     */
    String _Y_PAYMENT_INSURANCE_MARKING = "Y";//如果是 Y 是保险商户，如果是 N 不是保险商户
    /**
     * 保险商户标识
     */
    String _N_PAYMENT_INSURANCE_MARKING = "N";//如果是 Y 是保险商户，如果是 N 不是保险商户

    String PSPT_MCPT_NOTICE = "mcpt_notice"; // 公告缓存key

    String PSPT_MCPT_LOGIN_WRONG_TIME = "mcpt_login_wrong_time"; // 登陆用户名密码错误次数缓存key

    String VERTICAL_SEPARATOR = "|";
    String DELIMITER_COMMA = ",";

    //account type
    String ACCOUNT_TYPE_BASIC = "03";  //基本账户 或者 待结算账户
    String ACCOUNT_TYPE_CASH = "05"; //现金账户
    String ACCOUNT_TYPE_PAYMENT = "07"; //付款账户

    //Session Key
    String SESSION_USER = "sessionUser";
    String SESSION_STATUS = "sessionStatus";
    String PERMISSION_LIST = "permissionList";

    //key for risk token gen
    String RISK_TOKEN_GEN_KEY = "riskless16723788pinganfuweb";
    String RISK_DEV_ID_TOKEN = "RiskDevIdToken";
    String TERMINAL = "aaa";

    //key for soft mark
    String SOFTMARK_DEV_ID_TOKEN = "PSPTMAC";

    //业务渠道
    //手机渠道
    String MOBILE = "M";
    //互联网渠道
    String WEB = "W";
    //刷卡机渠道
    String POS = "P";
    //互联网渠道
    String HQB_WEB = "W";

    //绑定电邮
    String BIND_EMAIL = "E";
    //绑定手机
    String BIND_MOBILE = "M";
    //个人
    String PERSON = "C";
    //商户
    String MERCHANT = "B";


    //Time Related
    String START_HHMMSS = "00:00:00";
    String END_HHMMSS = "23:59:59";

    //Pure String
    String STRING_EMPTY = "";
    String STRING_RESULT_FLAG = "resultFlag";
    String STRING_SUCCESS = "success";
    String STRING_FAILED = "failed";
    String STRING_PASSWORD_SAME = "passwordSame";
    String STRING_PASSWORD_NEWPASSWORD_SAME = "passwordnewpasswordSame";
    String STRING_NO_DATA = "nodata";
    String STRING_SERVICE_ERROR = "serviceError";
    String STRING_PAYMENT_PASSWORD_ERROR = "paymentPasswordError";
    String PAYMENT_PASSWORD_NUMBER_ERROR = "paymentNumberError";

    String STRING_ZERO = "0";
    String STRING_YES = "Y"; //means Yes.
    String STRING_NO = "N";  //means No.
    String STRING_ADMIN = "A";  //means Admin.

    //快捷卡签约状态   add by yanjiawei 2014.06.27
    String FASTPAY_FLAG = "1";
    String NOT_FASTPAY_FLAG = "0";
    String WITHDRAW_FLAG = "1";
    String NOT_WITHDRAW_FLAG = "0";
    String SIGN_STATUS_Y = "Y"; //已签约
    String SIGN_STATUS_P = "P";  //签约进行中
    String SIGN_STATUS_N = "N"; //未签约

    //预警状态 T-有效  U-无效
    String EARLY_EFFECTIVE = "T";
    String EARLY_INVALID = "U";
    String CALCULATE_BASIS = "01"; //金额

    //it is used for encrypt flag: 1 means this password has been encrypted for 1 time,
    //need to encrypted again in back end service.
    String STRING_ONE = "1";

    //It is used for reset type temporarily.
    String STRING_TWO = "2";
    String STRING_THREE = "3";
    String STRING_FOUR = "4";

    String STRING_EIGHT = "8";
    //活钱宝赎回银行卡
    String HQB_REDEEM_BANKCARD = "17";
    //default type. It is used for csv type report file.
    String STRING_ALL = "ALL";
    String STRING_THREE_DASH = "---";

    String STRING_TRUE = "true";
    String STRING_UNKNOWN = "UNKNOWN";
    String STRING_COMMA = ",";


    String LIST_TYPE_TRANSACTION = "transaction";
    String LIST_TYPE_REFUND = "refund";

    String USER_TYPE_ANONYMOUS = "9999999999999999";
    /**
     * 产品码-线下充值
     */
    String PRODUCT_CODE_FUND = "100010010000";
    /**
     * 产品码-线上充值
     */
    String PRODUCT_CODE_FUND_07 = "100010050000";
    String PRODUCT_CODE_FUND_05 = "100010040000";
    // transType
    /**
     * 交易类型-批量代收
     */
    String TRANS_TYPE_BATCH_COLLECTION = "63";
    /**
     * 交易类型-批量代付
     */
    String TRANS_TYPE_BATCH_PAY = "64";
    /**
     * 交易类型-线上退款
     */
    String TRANS_TYPE_REFUND = "38";
    /**
     * 交易类型-线下退款
     */
    String TRANS_TYPE_REFUND_OFFLINE = "50";
    //operator type
    String OPERATOR_TYPE_MERCHANT = "B";
    String OPERATOR_TYPE_CUTOMER = "C";

    //operator status
    String OPERATOR_STATUS_NORMAL = "T";
    String OPERATOR_STATUS_DISABLED = "C";
    String OPERATOR_STATUS_LOCKED = "U";
    // String OPERATOR_STATUS_ALL = STRING_EMTPY;

    //search condition.
    String CONDITION_TYPE = "t";
    String CONDITION_STATUS = "s";
    String CONDTION_RANGE = "r";
    String CONDTION_SIZE_PER_PAGE = "c";
    String CONDITION_CURRENT_PAGE = "p";

    String HEAD_MERCHANT_NAME = "商户名称";
    String HEAD_MERCHANT_NO = "商户号";
    String HEAD_TRANS_ID = "壹钱包交易号";
    String HEAD_MERCHANT_ORDER_NO = "商户订单号";
    String HEAD_ORIGINAL_ORDER_NO = "原商户订单号";
    String HEAD_CREATE_TIME = "交易创建时间";
    String HEAD_COMPLETE_TIME = "交易完成时间";
    String HEAD_TRANS_TYPE = "交易类型";
    String HEAD_TRANS_AMOUNT = "交易金额";
    String HEAD_TRANS_FEE = "手续费（元）";
    String HEAD_SELLTE_AMOUNT = "结算金额（元）";
    String HEAD_TRANS_STATUS = "交易状态";
    String HEAD_PAYMENT_BANK = "支付银行";
    String HEAD_TRANDTO = "交易对方";
    String HEAD_DES = "备注";
    String HEAD_FUND_STATUS = "资金状态";

    String HEAD_CUSTOMER_ID = "账户名";
    String HEAD_ORDERNO = "订单号";
    String HEAD_TRANSID = "交易单号";
    String HEAD_TRANSTIME = "交易时间";
    String HEAD_TRANSAMT = "交易金额";
    String HEAD_DEDUCTIONAMOUNT = "折扣金额";

    String HEAD_FUND_FROM = "资金来源";
    String HEAD_AMOUNT = "金额（元）";
    String HEAD_CURRENCY = "币种";
    String HEAD_STATUS = "状态";
    String HEAD_OPERATION = "操作";
    String HEAD_CUSTOMERID = "会员ID";
    String HEAD_BANK = "支付银行";


    String HEAD_BASE_ORDER_NO = "原商户订单号";
    String HEAD_REFUND_ORDER_NO = "退款订单号";
    String HEAD_REFUND_AMOUNT = "退款金额（元）";
    String HEAD_REFUND_STATUS = "退款状态";

    //response code
    String CODE_CIP_NOFOUND = "020005";
    String CODE_SUCCESS = "000000";
    String CODE_NO_DATA = "025070";
    String CODE_WRONG_PASSWORD = "020032";
    String CODE_PASSWORD_NEWPASSWORD_SAME = "020038";
    String CODE_LOGIN_PASSWORD_LOCK = "020040";
    String CODE_PASSWORD_SAME = "020041";
    String CODE_WRONG_LOGIN_STATUS = "020112";
    String CODE_CHANGE_MOBILE = "020181";
    String CODE_SIGNING = "020215";
    String CODE_NOT_EXIST = "020101";
    String CODE_VERIFY = "911500";
    String CODE_REJECT = "911700";
    String CODE_REJECT_RULE = "911701";
    String CODE_FAIL = "999999";
    String CODE_VALIDATE_FAIL = "999998";  //数据校验失败

    //log type
    String LOG_CONTROLLER = "pinganfuweb.controller";
    String LOG_SERVICE = "pinganfuweb.service";
    String LOG_PERFORMANCE_IN = "pinganfuweb.performance.in"; //service in log
    String LOG_PERFORMANCE_OUT = "pinganfuweb.performance.out"; //service out log


    //action
    String ACTION_MODIFY = "modify";
    String ACTION_RESET = "reset";
    String ACTION_SET = "set";
    String ACTION_GET = "get";
    String ACTION_PRE = "pre";
    String ACTION_LIST = "list";
    String ACTION_SEARCH = "search";
    String ACTION_DOWNLOAD = "download";

    String ACTION_REFUND = "refund";
    String ACTION_DETAIL = "detail";
    String ACTION_REFUND_SUCCESS = "refundSuccess";
    String ACTION_REFUND_FAILED = "refundFailed";
    String ACTION_PASSWORD_ERROR = "passwordError";
    String ACTION_NUMBER_ERROR = "numberError";

    //pay bank type by jinlong
    String DEBIT_CARD_TYPE = "D";
    String CREDIT_CARD_TYPE = "C";
    //招商银行卡bankCode
    String CMB = "CMB";

    //default bank code and id
    String DEFAULT_BANK_CODE = "PAB";
    String DEFAULT_BANK_ID = "1043";
    String DEFAULT_BANK_NAME = "平安银行";

    String FORMERROR_MOBILEORBANKNO = "formError.mobileOrBankNo";


    String QUERY_HEALTHCARD_SERVICE = "transcore-transQueryList-service";


    String QUERY_CONTBUSINESS_SERVICE = "mcp-contbusiness-service";

    String CMA_CMA_TRANS_FUND_FACADE = "cma_cma_trans_fund_facade";
    String BATCH_DOWNLOAD_NUM_CMA = "batch_download_num_cma";
    // String MERCHANT_NUM_CMA_ENABLED = "merchant_num_cma_enabled";

    /**
     * 代收付下载文件每页取的默认条数
     */
    int DOWNLOAD_PAGE_SIZE = 500;
    /** 内部商户号  */ /*this attribute has been removed.
    // String CONF_PARAM_INNER_MERCHANT_NO = "param-inner-merchant-no";

    /**MCP任务单接口查询商户号是否有自助提现类型 */
    // String MCP_TASK_QUERY_SERVICE = "mcp-task-query-service";

    /**MKT活动平台接口查询积分代码*/
    // String MKT_POINT_SERVICE = "mkt_point_service";

    /**MCP签约相关接口查询产品码*/
    // String MCP_PRODUCT_QUERY_SERVICE = "mcp-product-query-service";
    /**
     * 批量代收在合同平台的产品码 新增200030020000,200030020001,200010020001
     */
    String BATCH_COLLECTION_PRODUCT_CODE = "200010020000,200030020000,200030020001,200010020001";
    /**
     * 批量代付在合同平台的产品码新增200030020000,200030020001,200020020001
     */
    String BATCH_PAYMENT_PRODUCT_CODE = "200020020000,200030020000,200030020001,200020020001";
    /**
     * 协议代扣在合同平台的产品码
     */
    String FAST_PAY_CONTRACT_PRODUCT_CODE = "100040010000";
    /**
     * 协议代扣在合同平台的支付类型
     */
    String FAST_PAY_CONTRACT_PAY_TYPE = "13";
    /**
     * 商户券业务在合同平台的产品码
     */
    String CARD_VOUCHERS_PRODUCT_CODE = "100070010000";
    /**
     * 创新保险业务在合同平台的产品码
     */
    String INSURANCE_PRODUCT_CODE = "100040010005";
    /**
     * 理财商城业务在合同平台的产品码
     */
    String YIMALL_PRODUCT_CODE = "100040010004";

    //手机农钱包渠道
    String MOBILE_NQB_CHANNEL = "M-100004";
    /**
     * Ajax
     */
    String TRANSCATION_NULL = "c000001";//查询订单信息为空
    String EXCEPTION_CASHIER = "c000002";//交易核心返回数据异常
    String TRANSCATION_STATU_NO_SUCCESS = "c000003";//查询订单信息交易状态为非成功

    //added by trsun on 20140505->
    /**
     * 员定制配置  configure type
     * definition under http://192.168.0.111/pinganfu/pingandoc/文档库/概要设计/会员系统/接口文档
     */
    String CIP_CONFIG_TYPE_PAYMENT_CONFIG = "0001";//支付配置
    String CIP_CONFIG_TYPE_FAST_PAY = "0002";//马上付
    String CIP_CONFIG_TYPE_SERVICE_VERSION = "0003";//服务协议版本号
    String CIP_CONFIG_TYPE_PASSWORD_FREE = "0004";//服务协议版本号

    /**
     * 员定制配置 status
     * definition under http://192.168.0.111/pinganfu/pingandoc/文档库/概要设计/会员系统/接口文档
     */
    String CIP_STATUS_ACTIVE = "T";//有效
    String CIP_STATUS_INACTIVE = "U";//马上付
    //<-end of trsun on 20140505

    String CIP_SIGN_STATUS_SIGNED = "Y";//已签约
    String CIP_SIGN_STATUS_TO_SIGN = "N";//未签约
    String CIP_SIGN_STATUS_SIGNING = "P";//签约进行中

    String PAF_WEB_FROM_SOURCE = "pinganfuweb-1.0";

    /**
     * verification level
     */
    //not verified
    String CIP_VERIFICATION_LEVEL_NONE = "D";
    //weak real name
    String CIP_VERIFICATION_LEVEL_WEAK = "C";
    //middle real name
    String CIP_VERIFICATION_LEVEL_MIDDLE = "B";
    //strong real name
    String CIP_VERIFICATION_LEVEL_STRONG = "A";

    //add by chenxin on 20140804
    /*
     * 发送短信验证码
     * businessId
     * 
     * */
    String GOUTONG_BUSINESS_ID = "1001";
    //<-end of chenxin on 20140804

    //added by trsun on 20140602->
    /**
     * 风控验证中心 - 风控检查点  checkPointCode
     * definition under XXX/风控-验证中心对外接口文档v1.4
     */
    String RISK_CHECK_POINT_CODE_PSPT = "CP021";//门户支付验证策略查询
    //<-end of trsun on 20140602

    //added by chenxin on 20140721->
    /**
     * 风控验证中心 - 风控检查点  checkPointCode
     * definition under XXX/风控验证更换手机号码查询接口
     */
    String RISK_CHECK_POINT_CODE_CHANGE_MOBILE_PSPT = "CP118";//门户更换手机号码组合方式查询
    String RISK_CHECK_POINT_CODE_USER_MESSAGE_PSPT = "CP101";//门户更换手机号码组合方式查询
    //<-end of chenxin on 20140721

    String SYSTEM_NAME = "pspt";
    String SYSTEM_NAME_ERROR = "pinganfuweb";
    String SYSTEM_NAME_PSPT = "PSPT";

    /**
     * 水电煤银联号
     */
    String MERCHANTCUSTOMERID = "cbp_merchantCustomerId";
    /**
     * 水电煤默认城市
     */

    /**
     * 非实名绑卡的key
     */
    String NOT_REALNAEM_PROCESS = "notRealNameProcess";


    String OPS_USE_DEV_MOCK = "use_dev_mock";
    /**
     * 01-净金额结算
     */
    String SETTLEMENT_MODE_01 = "01";
    /**
     * 02-收支两条线
     */
    String SETTLEMENT_MODE_02 = "02";

    /**
     * 金额位数1
     */
    Long NUMBER_1000000 = 1000000l;

    /**
     * 金额位数2
     */
    Long NUMBER_100 = 100l;
    /**
     * 金额位数3
     */
    Long NUMBER_10 = 10l;

    /**
     * 优惠券的长度
     */
    int YH_COUNT = 10;

    /**
     * 前端输入金额最大值限制额度
     */
    int MAX_LIMIT_AMOUNT = 1000000;

    /**
     * 和Account相关
     */
    String ACCOUNT_FREEZE = "99";
    String ACCOUNT_PAY_STOP = "1";
    /**
     * 批量下载的条数
     */
    String BATCH_DOWN_NUM = "batch_down_num";

    /**
     * 对账单下载线程最大值
     */
    String BILL_DOWNLOAD_THREAD_MAXIMUM = "bill_download_thread_maximum";

    /**
     * 对账单下载时间间隔
     */
    String BILL_DOWNLOAD_INTERVAL = "bill_download_interval";
    /**
     * 错误码查询场景--绑卡
     */
    String BIND_CARD = "bindCard";

    String HQB_OPERATION = "hqb_operation";

    /**
     * 保存续期宝信息的key
     */
    String XQB = "xqb";

    /**
     * 壹卡会注册渠道
     */
    String EKA_CHANNEL = "T-100009";


    /**
     * 航意险注册渠道
     */
    String HYX_CHANNEL = "T-100013";
    /**
     * 新用户注册领取老用户赠送的飞常幸运（航意险）。(T-100017可能是电话号码，是否拨号?)
     */
    String HYX_CHANNEL_EXTENDED = "T-100017";
    /**
     * 新用户注册领取老用户买赠的飞常幸运（航意险）。(T-100018)
     */
    String HYX_CHANNEL_GIVED = "T-100018";
    /**
     * 续期宝产品码
     */
    String XQB_PRODUCT_CODE = "100020090000";
    /**
     * 活钱宝产品码
     */
    String HQB_PRODUCT_CODE = "100020030000";


    /**
     * 清结算文件的SFTP IP
     */
    String MERCHANT_STATEMENT_URL = "merchantstatement.url";

    /**
     * 清结算文件的SFTP 端口
     */
    String MERCHANT_STATEMENT_PORT = "merchantstatement.port";

    /**
     * 清结算文件的 用户名
     */
    String MERCHANT_STATEMENT_USERNAME = "merchantstatement.username";

    /**
     * 清结算文件的密码
     */
    String MERCHANT_STATEMENT_PASSWORD = "merchantstatement.password";

    /**
     * 清结算文件的SFTP IP
     */
    String MERCHANT_CMA_STATEMENT_IP = "merchant_cma_statement.ip";

    /**
     * 清结算文件的SFTP 端口
     */
    String MERCHANT_CMA_STATEMENT_PORT = "merchant_cma_statement.port";

    /**
     * 清结算文件的 用户名
     */
    String MERCHANT_CMA_STATEMENT_USERNAME = "merchant_cma_statement.username";

    /**
     * 清结算文件的密码
     */
    String MERCHANT_CMA_STATEMENT_PASSWORD = "merchant_cma_statement.password";

    /** 创新保险文件的下载目录 */
    // String CONF_NAS_INSURANCE_DOWN= "merchant_nas_insurance_down";

    /**
     * 创新保险文件的SFTP IP
     */
    String MERCHANT_INSURANCE_URL = "merchantinsurance.url";

    /**
     * 创新保险文件的SFTP 端口
     */
    String MERCHANT_INSURANCE_PORT = "merchantinsurance.port";

    /**
     * 创新保险文件的 用户名
     */
    String MERCHANT_INSURANCE_USERNAME = "merchantinsurance.username";

    /**
     * 创新保险文件的密码
     */
    String MERCHANT_INSURANCE_PASSWORD = "merchantinsurance.password";

    /** 理城商城 yimall的下载目录 */
    // String CONF_NAS_YIMALL_DOWN= "merchant_nas_yimall_down";

    /**
     * 理城商城 yimall的SFTP IP
     */
    String MERCHANT_YIMALL_URL = "merchantyimall.url";

    /**
     * 理城商城 yimall的SFTP 端口
     */
    String MERCHANT_YIMALL_PORT = "merchantyimall.port";

    /**
     * 理城商城 yimall的 用户名
     */
    String MERCHANT_YIMALL_USERNAME = "merchantyimall.username";

    /**
     * 理城商城 yimall的密码
     */
    String MERCHANT_YIMALL_PASSWORD = "merchantyimall.password";


    /**
     * 第三方绑定关系类型
     */
    //00 - 万里通
    String ACR_THIRD_TYPE_WANGLITONG = "00";
    //01 - 陆金所
    String ACR_THIRD_TYPE_LUJINSUO = "01";
    //02 - 小贷
    String ACR_THIRD_TYPE_XIAODAI = "02";
    //04 - 一账通
    String ACR_THIRD_TYPE_YIZHANGTONG = "04";
    //05 - 个人商户（捷银）
    String ACR_THIRD_TYPE_JEIYIN = "05";
    //06 - 个人商户（壹卡会）
    String ACR_THIRD_TYPE_YIKAHUI = "06";
    //09 - 微信
    String ACR_THIRD_TYPE_WEIXIN = "09";

    /**
     * 绑定发起源
     */
    // 00-壹钱包发起
    String ACR_BIND_SPONSOR_FROM_YQB = "00";
    //01-外部系统发起
    String ACR_BIND_SPONSOR_FROM_OUTSYSTEM = "01";

    /**
     * 绑定调用方
     */
    String ACR_FINANCIALMALL_PC = "02";

    String ACR_FINANCIALMALL_APP = "01";

    /**
     * acr 绑定状态
     */
    String ACR_BIND_STATUS_BINDING = "B";

    /**
     * 注册方式--邮箱
     */
    String REG_EMAIL = "E";
    /**
     * 注册方式--手机
     */
    String REG_MOBILE = "M";
    /**
     * 重置支付密码类型---4--非实名重置
     */
    String NOTREALNAME_TYPE = "4";
    /**
     * 重置支付密码类型---1--实名重置
     */
    String REALNAME_TYPE = "1";

    /**
     * 协议代扣业务id
     */
    String BUSINESS_ID_FASTPAY_CONTRACT = "fastpayContract";


    /**
     * 批量代收付交易类型-代收
     */
    String MPMT_TRANS_TYPE_BDS = "BDS";
    /**
     * 批量代收付交易类型-代发
     */
    String MPMT_TRANS_TYPE_BDF = "BDF";

    /**
     * 问题上报-产品大类
     */
    String PRODUCT_TYPE = "PRODUCT";
    /**
     * 问题上报-问题类型
     */
    String PROBLEM_TYPE = "PROBLEM";

    /**
     *外部对账单-商圈入驻
     */
    String BILL_TYPE_SQRZ="SQRZ";

    /**
     *外部对账单-商圈入驻付汇
     */
    String BILL_TYPE_SQRZFH="SQRZFH";
    /**
     *外部对账单-自营采购单
     */
    String BILL_TYPE_ZYCGD="ZYCGD";
}
