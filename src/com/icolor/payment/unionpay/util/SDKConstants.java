package com.icolor.payment.unionpay.util;

public class SDKConstants
{
	public final static String ORG_RES = "org_response";

	public final static String COLUMN_DEFAULT = "-";

	public final static String KEY_DELIMITER = "#";

	/** memeber variable: blank. */
	public static final String BLANK = "";

	/** member variabel: space. */
	public static final String SPACE = " ";

	/** memeber variable: unline. */
	public static final String UNLINE = "_";

	/** memeber varibale: star. */
	public static final String STAR = "*";

	/** memeber variable: line. */
	public static final String LINE = "-";

	/** memeber variable: add. */
	public static final String ADD = "+";

	/** memeber variable: colon. */
	public final static String COLON = "|";

	/** memeber variable: point. */
	public final static String POINT = ".";

	/** memeber variable: comma. */
	public final static String COMMA = ",";

	/** memeber variable: slash. */
	public final static String SLASH = "/";

	/** memeber variable: div. */
	public final static String DIV = "/";

	/** memeber variable: left . */
	public final static String LB = "(";

	/** memeber variable: right. */
	public final static String RB = ")";

	/** memeber variable: rmb. */
	public final static String CUR_RMB = "RMB";

	/** memeber variable: .page size */
	public static final int PAGE_SIZE = 10;

	/** memeber variable: String ONE. */
	public static final String ONE = "1";

	/** memeber variable: String ZERO. */
	public static final String ZERO = "0";

	/** memeber variable: number six. */
	public static final int NUM_SIX = 6;

	/** memeber variable: equal mark. */
	public static final String EQUAL = "=";

	/** memeber variable: operation ne. */
	public static final String NE = "!=";

	/** memeber variable: operation le. */
	public static final String LE = "<=";

	/** memeber variable: operation ge. */
	public static final String GE = ">=";

	/** memeber variable: operation lt. */
	public static final String LT = "<";

	/** memeber variable: operation gt. */
	public static final String GT = ">";

	/** memeber variable: list separator. */
	public static final String SEP = "./";

	/** memeber variable: Y. */
	public static final String Y = "Y";

	/** memeber variable: AMPERSAND. */
	public static final String AMPERSAND = "&";

	/** memeber variable: SQL_LIKE_TAG. */
	public static final String SQL_LIKE_TAG = "%";

	/** memeber variable: @. */
	public static final String MAIL = "@";

	/** memeber variable: number zero. */
	public static final int NZERO = 0;

	public static final String LEFT_BRACE = "{";

	public static final String RIGHT_BRACE = "}";

	/** memeber variable: string true. */
	public static final String TRUE_STRING = "true";
	/** memeber variable: string false. */
	public static final String FALSE_STRING = "false";

	/** memeber variable: forward success. */
	public static final String SUCCESS = "success";
	/** memeber variable: forward fail. */
	public static final String FAIL = "fail";
	/** memeber variable: global forward success. */
	public static final String GLOBAL_SUCCESS = "$success";
	/** memeber variable: global forward fail. */
	public static final String GLOBAL_FAIL = "$fail";

	public static final String CONTENT_TYPE = "Content-type";
	public static final String APP_XML_TYPE = "application/xml;charset=utf-8";
	public static final String APP_FORM_TYPE = "application/x-www-form-urlencoded;charset=";

	/******************************************** 5.0报文接口定义 ********************************************/
	/** 版本�?. */
	public static final String param_version = "version";
	/** 证书ID. */
	public static final String PARAM_CERTID = "certId";
	/** 签名. */
	public static final String PARAM_SIGNATURE = "signature";
	/** 编码方式. */
	public static final String PARAM_ENCODING = "encoding";
	/** 交易类型. */
	public static final String param_txnType = "txnType";
	/** 交易子类. */
	public static final String param_txnSubType = "txnSubType";
	/** 业务类型. */
	public static final String param_bizType = "bizType";
	/** 前台通知地址 . */
	public static final String param_frontUrl = "frontUrl";
	/** 后台通知地址. */
	public static final String param_backUrl = "backUrl";
	/** 接入类型. */
	public static final String param_accessType = "accessType";
	/** 收单机构代码. */
	public static final String param_acqInsCode = "acqInsCode";
	/** 商户类别. */
	public static final String param_merCatCode = "merCatCode";
	/** 商户类型. */
	public static final String param_merType = "merType";
	/** 商户代码. */
	public static final String param_merId = "merId";
	/** 商户名称. */
	public static final String param_merName = "merName";
	/** 商户�?�?. */
	public static final String param_merAbbr = "merAbbr";
	/** 二级商户代码. */
	public static final String param_subMerId = "subMerId";
	/** 二级商户名称. */
	public static final String param_subMerName = "subMerName";
	/** 二级商户�?�?. */
	public static final String param_subMerAbbr = "subMerAbbr";
	/** Cupsecure 商户代码. */
	public static final String param_csMerId = "csMerId";
	/** 商户订单. */
	public static final String PARAM_ORDERID = "orderId";
	/** 交易时间. */
	public static final String param_txnTime = "txnTime";
	/** 发�?�时�?. */
	public static final String param_txnSendTime = "txnSendTime";
	/** 订单超时时间间隔. */
	public static final String param_orderTimeoutInterval = "orderTimeoutInterval";
	/** 支付超时时间. */
	public static final String param_payTimeoutTime = "payTimeoutTime";
	/** 默认支付方式. */
	public static final String param_defaultPayType = "defaultPayType";
	/** 支持支付方式. */
	public static final String param_supPayType = "supPayType";
	/** 支付方式. */
	public static final String param_payType = "payType";
	/** 自定义支付方�?. */
	public static final String param_customPayType = "customPayType";
	/** 物流标识. */
	public static final String param_shippingFlag = "shippingFlag";
	/** 收货地址-国家. */
	public static final String param_shippingCountryCode = "shippingCountryCode";
	/** 收货地址-�?. */
	public static final String param_shippingProvinceCode = "shippingProvinceCode";
	/** 收货地址-�?. */
	public static final String param_shippingCityCode = "shippingCityCode";
	/** 收货地址-地区. */
	public static final String param_shippingDistrictCode = "shippingDistrictCode";
	/** 收货地址-详细. */
	public static final String param_shippingStreet = "shippingStreet";
	/** 商品总类. */
	public static final String param_commodityCategory = "commodityCategory";
	/** 商品名称. */
	public static final String param_commodityName = "commodityName";
	/** 商品URL. */
	public static final String param_commodityUrl = "commodityUrl";
	/** 商品单价. */
	public static final String param_commodityUnitPrice = "commodityUnitPrice";
	/** 商品数量. */
	public static final String param_commodityQty = "commodityQty";
	/** 是否预授�?. */
	public static final String param_isPreAuth = "isPreAuth";
	/** 币种. */
	public static final String param_currencyCode = "currencyCode";
	/** 账户类型. */
	public static final String param_accType = "accType";
	/** 账号. */
	public static final String param_accNo = "accNo";
	/** 支付卡类�?. */
	public static final String param_payCardType = "payCardType";
	/** 发卡机构代码. */
	public static final String param_issInsCode = "issInsCode";
	/** 持卡人信�?. */
	public static final String param_customerInfo = "customerInfo";
	/** 交易金额. */
	public static final String param_txnAmt = "txnAmt";
	/** 余额. */
	public static final String param_balance = "balance";
	/** 地区代码. */
	public static final String param_districtCode = "districtCode";
	/** 附加地区代码. */
	public static final String param_additionalDistrictCode = "additionalDistrictCode";
	/** 账单类型. */
	public static final String param_billType = "billType";
	/** 账单号码. */
	public static final String param_billNo = "billNo";
	/** 账单月份. */
	public static final String param_billMonth = "billMonth";
	/** 账单查询要素. */
	public static final String param_billQueryInfo = "billQueryInfo";
	/** 账单详情. */
	public static final String param_billDetailInfo = "billDetailInfo";
	/** 账单金额. */
	public static final String param_billAmt = "billAmt";
	/** 账单金额符号. */
	public static final String param_billAmtSign = "billAmtSign";
	/** 绑定标识�?. */
	public static final String param_bindId = "bindId";
	/** 风险级别. */
	public static final String param_riskLevel = "riskLevel";
	/** 绑定信息条数. */
	public static final String param_bindInfoQty = "bindInfoQty";
	/** 绑定信息�?. */
	public static final String param_bindInfoList = "bindInfoList";
	/** 批次�?. */
	public static final String param_batchNo = "batchNo";
	/** 总笔�?. */
	public static final String param_totalQty = "totalQty";
	/** 总金�?. */
	public static final String param_totalAmt = "totalAmt";
	/** 文件类型. */
	public static final String param_fileType = "fileType";
	/** 文件名称. */
	public static final String param_fileName = "fileName";
	/** 批量文件内容. */
	public static final String param_fileContent = "fileContent";
	/** 商户摘要. */
	public static final String param_merNote = "merNote";
	/** 商户自定义域. */
	// public static final String param_merReserved = "merReserved";//接口变更删除
	/** 请求方保留域. */
	public static final String param_reqReserved = "reqReserved";// 新增接口
	/** 保留�?. */
	public static final String param_reserved = "reserved";
	/** 终端�?. */
	public static final String param_termId = "termId";
	/** 终端类型. */
	public static final String param_termType = "termType";
	/** 交互模式. */
	public static final String param_interactMode = "interactMode";
	/** 发卡机构识别模式. */
	// public static final String param_recognitionMode = "recognitionMode";
	public static final String param_issuerIdentifyMode = "issuerIdentifyMode";// 接口名称变更
	/** 商户端用户号. */
	public static final String param_merUserId = "merUserId";
	/** 持卡人IP. */
	public static final String param_customerIp = "customerIp";
	/** 查询流水�?. */
	public static final String param_queryId = "queryId";
	/** 原交易查询流水号. */
	public static final String param_origQryId = "origQryId";
	/** 系统跟踪�?. */
	public static final String param_traceNo = "traceNo";
	/** 交易传输时间. */
	public static final String param_traceTime = "traceTime";
	/** 清算日期. */
	public static final String param_settleDate = "settleDate";
	/** 清算币种. */
	public static final String param_settleCurrencyCode = "settleCurrencyCode";
	/** 清算金额. */
	public static final String param_settleAmt = "settleAmt";
	/** 清算汇率. */
	public static final String param_exchangeRate = "exchangeRate";
	/** 兑换日期. */
	public static final String param_exchangeDate = "exchangeDate";
	/** 响应时间. */
	public static final String param_respTime = "respTime";
	/** 原交易应答码. */
	public static final String param_origRespCode = "origRespCode";
	/** 原交易应答信�?. */
	public static final String param_origRespMsg = "origRespMsg";
	/** 应答�?. */
	public static final String PARAM_RESPCODE = "respCode";
	/** 应答码信�?. */
	public static final String PARAM_RESPMSG = "respMsg";
	// 新增四个报文字段merUserRegDt merUserEmail checkFlag activateStatus
	/** 商户端用户注册时�?. */
	public static final String param_merUserRegDt = "merUserRegDt";
	/** 商户端用户注册邮�?. */
	public static final String param_merUserEmail = "merUserEmail";
	/** 验证标识. */
	public static final String param_checkFlag = "checkFlag";
	/** �?通状�?. */
	public static final String param_activateStatus = "activateStatus";
	/** 加密证书ID. */
	public static final String PARAM_ENCRYPTCERTID = "encryptCertId";
	/** 用户MAC、IMEI串号、SSID. */
	public static final String param_userMac = "userMac";
	/** 关联交易. */
	// public static final String param_relationTxnType = "relationTxnType";
	/** 短信类型 */
	public static final String param_smsType = "smsType";

	/** 风控信息�? */
	public static final String param_riskCtrlInfo = "riskCtrlInfo";

	/** IC卡交易信息域 */
	public static final String param_ICTransData = "ICTransData";

	/** VPC交易信息�? */
	public static final String param_VPCTransData = "VPCTransData";

	/** 安全类型 */
	public static final String param_securityType = "securityType";

	/** 银联订单�? */
	public static final String param_tn = "tn";

	/** 分期付款手续费率 */
	public static final String param_instalRate = "instalRate";

	/** 分期付款手续费率 */
	public static final String param_mchntFeeSubsidy = "mchntFeeSubsidy";

	public static final String PARAM_PIN = "pin";

	public static final String PARAM_CVN2 = "cvn2";

	public static final String PARAM_EXPIRED = "expired";

	public static final String PARAM_PHONENO = "phoneNo";

	public static final String PARAM_ENCRYPTEDINFO = "encryptedInfo";

	public static final String CERT_PKC = "PKCS12";

	public static final String PARAM_REFUND = "REFUND";

	public static final String SDK_FRONT_URL = "acpsdk.frontTransUrl";
	public static final String SDK_BACK_URL = "acpsdk.backTransUrl";
	public static final String SDK_SIGNQ_URL = "acpsdk.singleQueryUrl";
	public static final String SDK_BATQ_URL = "acpsdk.batchQueryUrl";
	public static final String SDK_BATTRANS_URL = "acpsdk.batchTransUrl";
	public static final String SDK_FILETRANS_URL = "acpsdk.fileTransUrl";
	public static final String SDK_CARD_URL = "acpsdk.cardTransUrl";
	public static final String SDK_APP_URL = "acpsdk.appTransUrl";


	public static final String JF_SDK_FRONT_TRANS_URL = "acpsdk.jfFrontTransUrl";
	public static final String JF_SDK_BACK_TRANS_URL = "acpsdk.jfBackTransUrl";
	public static final String JF_SDK_SINGLE_QUERY_URL = "acpsdk.jfSingleQueryUrl";
	public static final String JF_SDK_CARD_TRANS_URL = "acpsdk.jfCardTransUrl";
	public static final String JF_SDK_APP_TRANS_URL = "acpsdk.jfAppTransUrl";


	public static final String SDK_SIGNCERT_PWD = "acpsdk.signCert.pwd";
	public static final String SDK_SIGNCERT_TYPE = "acpsdk.signCert.type";
	public static final String SDK_ENCRYPTTRACKCERT_PATH = "acpsdk.encryptTrackCert.path";
	public static final String SDK_ENCRYPTTRACKKEY_MODULUS = "acpsdk.encryptTrackKey.modulus";
	public static final String SDK_ENCRYPTTRACKKEY_EXPONENT = "acpsdk.encryptTrackKey.exponent";

	public static final String SDK_CVN_ENC = "acpsdk.cvn2.enc";
	public static final String SDK_DATE_ENC = "acpsdk.date.enc";
	public static final String SDK_PAN_ENC = "acpsdk.pan.enc";
	public static final String SDK_SINGLEMODE = "acpsdk.singleMode";

	public static final String SDK_CALLBACK_FRONTEND = "acpsdk.callback.frontend";

	public static final String SDK_CALLBACK_BACKEND = "acpsdk.callback.backend";

	public static final String SDK_MERID = "acpsdk.merId";

	public static final String SDK_CONNECTIONTIMEOUT = "acpsdk.connectionTimeout";

	public static final String SDK_READTIMEOUT = "acpsdk.readTimeOut";

	public static final String SDK_ORDER_FILE = "acpsdk.order.file";

	public static final String SDK_CALLBACK_REFUND = "acpsdk.callback.refund";

	public static final int CURRENCY_RATE = 100;

	public static final String SIGNMETHOD = "01";

	public static final String TXNTYPE_ACCOUNT_STATUS_QUERY = "78";

	public static final String TXNTYPE_PAY = "01";

	public static final String TXNTYPE_QUERY = "00";

	public static final String TXNTYPE_REFUND = "04";

	public static final String TXNTYPE_FILE_TRANSFER = "76";

	public static final String TXNTYPE_SEND_SMS = "77";

	public static final String TXNTYPE_OPEN_CARD = "79";

	public static final String TXNSUBTYPE_BY_ACCOUNT = "00";

	public static final String TXNSUBTYPE_QUERY = "00";

	public static final String TXNSUBTYPE_REFUND = "00";

	public static final String TXNSUBTYPE_CONSUME = "01";

	public static final String TXNSUBTYPE_FILE = "01";

	public static final String TXNSUBTYPE_SEND_SMS = "02";

	public static final String BIZTYPE_CERTIFIED_PAYMENTS = "000301";

	public static final String BIZTYPE_WAP_PAYMENTS = "000201";

	public static final String BIZTYPE_FILE_TRANSFER = "000000";

	public static final String CHANNEL_TYPE_PC = "07";

	public static final String CHANNEL_TYPE_MOBILE = "08";

	public static final String ACCESSTYPE = "0";

	public static final int HTTP_RESPONSE_OK = 200;


	public static final String CURRENCYCODE = "156";

	public static final String CALLBACK_NOTIFY = "OK";
	//response

	public static final String RESP_SUCCESS = "00";

	public static final String RESP_TIMEOUT = "03";

	public static final String RESP_UNKNOW = "04";

	public static final String RESP_HANDLING = "05";

	public static final String RESP_ACCOUNT_NOT_OPEND = "77";

	//WAP payment callback url
	public static final String PAYMENT_WAP_CALLBACK_FRONT = "acpsdk.callback.success";
	public static final String PAYMENT_WAP_CALLBACK_BACKEND = "acpsdk.callback.backend";

	//2017-12-06新增
	public static final String ENCODING_UTF8 = "UTF-8";
	public static final String VERSION = "5.0.0";
	public static final String MER_ID = "105290000000037";
	public static final String REQ_URL = "https://gateway.95516.com/gateway/api/frontTransReq.do";
	public static final String FRONT_URL = "https://socialtest.icolor.com.cn/frontCallback";
	public static final String BACK_URL = "https://socialtest.icolor.com.cn/commonCallback";
	public static final String PAY_TYPE = "UNION";
	public static final String RESP_SUCCESS_MSG = "SUCCESS";


}
