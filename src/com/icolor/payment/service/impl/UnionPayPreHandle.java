package com.icolor.payment.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icolor.payment.common.util.IcolorDateFromatUtils;
import com.icolor.payment.data.PrePaymentResult;
import com.icolor.payment.handle.PrePayHandle;
import com.icolor.payment.unionpay.util.SDKConstants;
import com.icolor.payment.unionpay.util.SDKUtil;


public class UnionPayPreHandle implements PrePayHandle
{

	private static final Logger LOG = Logger.getLogger(CommonPaymentServiceImpl.class);

	public PrePaymentResult prePay(String orderCode, String fee)
	{
		PrePaymentResult result = new PrePaymentResult();
		if (StringUtils.isBlank(orderCode))
		{
			result.setErrorMsg("unionpay orderId can't be empty");
			return result;
		}

		if (StringUtils.isBlank(fee))
		{
			result.setErrorMsg("unionpay order fee can't be empty");
			return result;
		}

		final Map<String, String> requestData = new HashMap<String, String>();

		requestData.put("version", SDKConstants.VERSION);
		requestData.put("encoding", SDKConstants.ENCODING_UTF8);

		requestData.put("signMethod", SDKConstants.SIGNMETHOD);
		requestData.put("txnType", SDKConstants.TXNSUBTYPE_CONSUME);
		requestData.put("txnSubType", SDKConstants.TXNSUBTYPE_CONSUME);
		requestData.put("bizType", SDKConstants.BIZTYPE_WAP_PAYMENTS);
		requestData.put("channelType", SDKConstants.CHANNEL_TYPE_MOBILE);

		requestData.put("merId", SDKConstants.MER_ID);
		requestData.put("accessType", SDKConstants.ACCESSTYPE);
		requestData.put("orderId", orderCode);

		requestData.put("txnTime", IcolorDateFromatUtils.getCurrentTime());
		requestData.put("currencyCode", SDKConstants.CURRENCYCODE);

		String amount = SDKUtil.yuanSwitchfen(fee);
		requestData.put("txnAmt", amount);

		String successUrl = SDKConstants.FRONT_URL;
		String backurl = SDKConstants.BACK_URL;

		requestData.put("frontUrl", successUrl);
		requestData.put("backUrl", backurl);

		requestData.put("reqReserved", SDKConstants.PAY_TYPE);

		final Map<String, String> submitFromData = sign(requestData, SDKConstants.ENCODING_UTF8);
		String html = createAutoFormHtml(submitFromData, SDKConstants.ENCODING_UTF8);
		result.setSuccess(true);
		result.setData(html);
		return result;
	}

	/**
	 * 功能：前台交易构造HTTP POST自动提交表单<br>
	 *
	 * 表单提交地址<br>
	 *
	 * @param hiddens
	 *           以MAP形式存储的表单键值<br>
	 * @param encoding
	 *           上送请求报文域encoding字段的值<br>
	 * @return 构造好的HTTP POST交易表单<br>
	 */
	private String createAutoFormHtml(final Map<String, String> hiddens, final String encoding)
	{
		String reqUrl = SDKConstants.REQ_URL;
		final StringBuilder sf = new StringBuilder();
		sf.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=" + encoding + "\"/></head><body>");
		sf.append("<form id = \"pay_form\" action=\"" + reqUrl + "\" method=\"post\">");
		if (MapUtils.isEmpty(hiddens))
		{
			LOG.error("order construct form error,request map is empty");
			return null;
		}
		for (final Map.Entry<String, String> entry : hiddens.entrySet())
		{
			final String key = entry.getKey();
			final String value = entry.getValue();
			sf.append("<input type=\"hidden\" name=\"" + key + "\" id=\"" + key + "\" value=\"" + value + "\"/>");
		}
		sf.append("</form>");
		sf.append("</body>");
		sf.append("<script type=\"text/javascript\">");
		sf.append("document.all.pay_form.submit();");
		sf.append("</script>");
		sf.append("</html>");
		if (LOG.isDebugEnabled())
		{
			LOG.debug(String.format("order create payment html is : %s", sf.toString()));
		}
		return sf.toString();
	}

	/**
	 * 请求报文签名(使用配置文件中配置的私钥证书加密)<br>
	 * 功能：对请求报文进行签名,并计算赋值certid,signature字段并返回<br>
	 *
	 * @param reqData
	 *           请求报文map<br>
	 * @param encoding
	 *           上送请求报文域encoding字段的值<br>
	 * @return 签名后的map对象<br>
	 */
	public Map<String, String> sign(final Map<String, String> reqData, final String encoding)
	{
		final String orderId = reqData.get(SDKConstants.PARAM_ORDERID);
		final Map<String, String> submitData = SDKUtil.filterBlank(reqData);
		final boolean signResult = SDKUtil.sign(submitData, encoding);
		if (!signResult)
		{
			LOG.error(String.format("order %s payment sign error", orderId));
			return null;
		}
		return submitData;
	}
}
