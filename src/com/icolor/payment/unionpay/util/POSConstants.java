/**
 *
 */
package com.icolor.payment.unionpay.util;

/**
 * @author tianwei
 *
 */
public class POSConstants
{
	public static final String TRADE_TYPE = "POS";

	public static final String FINANCE_PAYMENTTYPE = "收入";

	//POS callback param name
	public static final String CONTEXT = "context";

	public static final String MAC = "mac";

	public static final String ERPBIZ_TRANSTYPE_WAYBILLSIGN = "P003";

	public static final String RESP_SIGNATURE_ERROR_CODE = "A0";

	public static final String RESP_SIGNATURE_ERROR_MSG = "signature error";

	public static final String RESP_PARAM_EMPTY_ERROR_CODE = "H1";

	public static final String RESP_PARAM_EMPTY_ERROR_MSG = "order no can't be empty";

	public static final String RESP_CAN_NOT_FIND_ORDER_ERROR_CODE = "H3";

	public static final String RESP_CAN_NOT_FIND_ORDER_ERROR_MSG = "can't find order";

	public static final String RESP_UPDATE_ORDER_STATUS_ERROR_CODE = "H4";

	public static final String RESP_UPDATE_ORDER_STATUS_ERROR_MSG = "update order status failure";

	public static final String RESP_SUCCESS_CODE = "00";

	public static final String RESP_SUCCESS_MSG = "SUCCESS";

	public static final String UPDATE_ORDER_STATUS_SUCCESS = "0";

	public static final String REQ_PARAM_MAC = "mac";

	public static final String PARAM_POS_MD5KEY = "payment.pos.md5key";

	//callback request data
	public static final String REQ_PARAM_ORDER_NO = "orderno";
	public static final String REQ_PARAM_COD = "cod";
	public static final String REQ_PARAM_TRACETIME = "tracetime";
	public static final String REQ_PARAM_POSTRACE = "postrace";
	public static final String REQ_PARAM_BANKTRACE = "banktrace";

	//call back response params
	public static final String CALLBACK_ROOT_ELEMENT = "transaction";

	public static final String CALLBACK_HEADER = "transaction_header";

	public static final String CALLBACK_BODY = "transaction_body";

	public static final String CALLBACK_HEADER_VERSION = "version";

	public static final String CALLBACK_HEADER_TRANSTYPE = "transtype";

	public static final String CALLBACK_HEADER_EMPLOYNO = "employno";

	public static final String CALLBACK_HEADER_TERMID = "termid";

	public static final String CALLBACK_HEADER_RESPONSE_TIME = "response_time";

	public static final String CALLBACK_HEADER_RESPONSE_CODE = "response_code";

	public static final String CALLBACK_HEADER_RESPONSE_MSG = "response_msg";

	public static final String CALLBACK_HEADER_MAC = "mac";

	//query order params

	public static final String QUERY_PARAM_ORDER_NO = "order_no";

	public static final String QUERY_PARAM_DSORDER_NO = "dsorder_no";

	public static final String QUERY_PARAM_MER_ID = "mer_id";

	public static final String QUERY_PARAM_MER_NAME = "mer_name";

	public static final String QUERY_PARAM_START_DATE = "start_date";

	public static final String QUERY_PARAM_END_DATE = "end_date";

	public static final String QUERY_PARAM_SETTLE_FLAG = "settle_flag";

	public static final String QUERY_PARAM_SETTLE_DATE = "settle_date";

	public static final String QUERY_PARAM_MAC = "mac";

	public static final String POS_MER_ID = "payment.pos.merid";

	public static final String POS_QUERY_MD5KEY = "payment.pos.query.md5key";

	public static final String POS_RECONCILIATION_MD5KEY = "payment.pos.reconciliationMD5key";

	public static final String POS_QUERY_URL = "payment.pos.query.url";


	// query order response param
	public static final String QUERY_RESULT_STATUS = "0";

	public static final String QUERY_RESULT_CODE = "code";

	public static final String QUERY_RESULT_MSG = "msg";

	public static final String QUERY_RESULT_ORDER_NO = "order_no";

	public static final String QUERY_RESULT_DSORDER_NO = "dsorder_no";

	public static final String QUERY_RESULT_COD = "cod";

	public static final String QUERY_RESULT_PAYWAY = "pay_way";

	public static final String QUERY_RESULT_POS_TRACE = "pos_trace";

	public static final String QUERY_RESULT_TRACE_TIME = "trace_time";

	public static final String QUERY_RESULT_CARD_ID = "card_id";

	public static final String QUERY_RESULT_SIGNFLAG = "signflag";

	public static final String QUERY_RESULT_SIGNER = "signer";

	public static final String QUERY_RESULT_DSSN = "dssn";

	public static final String QUERY_RESULT_DSNAME = "dsname";

	public static final String QUERY_RESULT_ERP_STATUS = "erp_status";

	public static final String QUERY_RESULT_ERP_DATE = "erp_date";

	public static final String QUERY_RESULT_DS_STATUS = "ds_status";

	public static final String QUERY_RESULT_DS_DATE = "ds_date";

	public static final String QUERY_RESULT_TT_STATUS = "tt_status";

	public static final String QUERY_RESULT_TT_DATE = "tt_date";

	public static final String QUERY_RESULT_SETTLE_AMT = "settle_amt";

	public static final String QUERY_RESULT_FEE = "fee";

	public static final String QUERY_RESULT_OTHER_FEE = "other_fee";

	public static final String QUERY_RESULT_CARD_TYPE = "card_type";

	public static final String QUERY_RESULT_SETTLE_MERID = "settle_merid";

	public static final String QUERY_RESULT_SETTLE_TERMID = "settle_termid";

	public static final String QUERY_RESULT_BANK_TRACE = "bank_trace";

	public static final String QUERY_RESULT_BANK_SBTRACE = "sbanktrace";

	public static final String QUERY_RESULT_TXN_TYPE = "txn_type";

	public static final String QUERY_RESULT_LIST = "list";

	// reconciliation param
	public static final String RECONCI_RESPONSE_CODE = "response_code";

	public static final String RECONCI_RESPONSE_MSG = "response_msg";

	public static final String RECONCI_RESPONSE_HEAD = "acctResponse";

	public static final String RECONCI_RESPONSE_MSG_SUCCESS_CONTENT = "SUCCESS";

	public static final String RECONCI_RESPONSE_MSG_FAILURE_CONTEXT_EMPTY = "context is empty ,can't reconciliation";

	public static final String RECONCI_RESPONSE_MSG_FAILURE_SIGNATURE = "check signature error";

	public static final String RECONCI_RESPONSE_MSG_FAILURE_CONVERT = "data from stringXML convert to DOM object error";

	public static final String RECONCI_RESPONSE_SUCCESS_CODE = "00";

	public static final String RECONCI_RESPONSE_FAILURE_CODE = "99";

	public static final String REQUEST_PARAMS_TXNAMT = "txnamt";

	public static final String REQUEST_PARAMS_ORDERNO = "orderno";

	public static final String REQUEST_PARAMS_TRACETIME = "tracetime";

	public static final String REQUEST_PARAMS_POSTRACE = "postrace";

	public static final String REQUEST_PARAMS_BANKTRACE = "banktrace";
}
