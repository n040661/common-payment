package com.icolor.payment.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.icolor.payment.data.PaymentResult;
import com.icolor.payment.data.PrePaymentResult;
import com.icolor.payment.enums.PaymentType;
import com.icolor.payment.handle.PaymentCheckSignatureException;
import com.icolor.payment.handle.PaymentResultHandle;
import com.icolor.payment.handle.PrePayHandle;
import com.icolor.payment.handle.UnionPaymentResultHandle;
import com.icolor.payment.service.CommonPaymentService;
import com.icolor.payment.unionpay.util.CertUtil;
import com.icolor.payment.unionpay.util.SDKConstants;
import com.icolor.payment.unionpay.util.SDKUtil;
import com.icolor.payment.unionpay.util.SecureUtil;


public class CommonPaymentServiceImpl implements CommonPaymentService
{
	private static final Logger LOG = Logger.getLogger(CommonPaymentServiceImpl.class);

	private static final Map<PaymentType, PrePayHandle> PAYHANDLEMAP = new HashMap<PaymentType, PrePayHandle>();

	private static final Map<PaymentType, Class<? extends PaymentResultHandle>> CALLBACKHANDLEMAP = new HashMap<PaymentType, Class<? extends PaymentResultHandle>>();

	private static final CommonPaymentService service = new CommonPaymentServiceImpl();

	public static CommonPaymentService getInstance()
	{
		return service;
	}

	private CommonPaymentServiceImpl()
	{
		// add pre handle 
		PAYHANDLEMAP.put(PaymentType.Union, new UnionPayPreHandle());

		//add callback handle
		CALLBACKHANDLEMAP.put(PaymentType.Union, UnionPaymentResultHandle.class);
	}

	public PrePaymentResult pay(PaymentType payType, String orderCode, String fee)
	{
		LOG.info(String.format("current pay type is %s", payType));
		PrePayHandle handle = PAYHANDLEMAP.get(payType);
		if (handle == null)
		{
			LOG.error("current payment did't has paymentHandle");
			return null;
		}
		return handle.prePay(orderCode, fee);
	}

	public void callback(HttpServletRequest request, PaymentType payType)
	{

		PaymentResult result = new PaymentResult();

		//银联签名，封装数据
		if (PaymentType.Union.equals(payType))
		{
			final Map<String, String> requestParam = SDKUtil.getAllRequestParam(request);
			// 验证签名
			if (!validateUnionPay(requestParam))
			{
				LOG.error("callback data check signature error,can't handle order");
				throw new PaymentCheckSignatureException("check unionpay callback data failure");
			}

			convertUnionData2StandradData(result, requestParam);
		}

		//4. 处理返回结果
		Class<? extends PaymentResultHandle> handleClass = CALLBACKHANDLEMAP.get(payType);
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		PaymentResultHandle handler = ctx.getBean(handleClass);

		if (handler == null)
		{
			LOG.error("current callback payment did't has paymentHandle");
			return;
		}
		handler.handle(result);

		LOG.info("callback handle end...");
	}

	private void convertUnionData2StandradData(PaymentResult result, Map<String, String> requestParam)
	{
		result.setType(PaymentType.Union);
		StringBuilder originalData = new StringBuilder();
		for (Map.Entry<String, String> entry : requestParam.entrySet())
		{
			originalData.append("[");
			originalData.append(entry.getKey());
			originalData.append("=");
			originalData.append(entry.getValue());
			originalData.append("] ");
		}
		result.setOriginalData(originalData.toString());
		String respCode = requestParam.get(SDKConstants.PARAM_RESPCODE);
		if (StringUtils.equals(respCode, SDKConstants.RESP_SUCCESS))
		{
			result.setSuccess(true);
		}
		else
		{
			String errorMsg = requestParam.get(SDKConstants.PARAM_RESPMSG);
			result.setErrorMsg(errorMsg);
			return;
		}

		String orderCode = requestParam.get(SDKConstants.PARAM_ORDERID);
		result.setOrderCode(orderCode);

		String fee = requestParam.get(SDKConstants.param_txnAmt);
		result.setFee(fee);
	}

	private boolean validateUnionPay(Map<String, String> requestParam)
	{

		Map<String, String> valideData = null;

		if (MapUtils.isEmpty(requestParam))
		{
			LOG.error("get unionpay payment callback data is empty from frontend,can't handle order status");
			return false;
		}
		final String orderId = requestParam.get(SDKConstants.PARAM_ORDERID);
		final String encoding = requestParam.get(SDKConstants.PARAM_ENCODING);

		if (StringUtils.isBlank(orderId))
		{
			LOG.error("order get unionpay callback from frontend , orderId is empty.");
			return false;
		}

		valideData = SDKUtil.encodingUnionpayParams(requestParam, encoding);

		if (MapUtils.isEmpty(valideData))
		{
			LOG.error(String.format(
					"order [%s] get unionpay callback data from frontend ,encoding result is empty,can't handle order status",
					orderId));
			return false;
		}

		final String stringSign = valideData.get(SDKConstants.PARAM_SIGNATURE);

		final String certId = valideData.get(SDKConstants.PARAM_CERTID);

		// 将Map信息转换成key1=value1&key2=value2的形式
		final String stringData = SDKUtil.coverMap2String(valideData);

		try
		{
			return SecureUtil.validateSignBySoft(CertUtil.getValidateKey(certId),
					SecureUtil.base64Decode(stringSign.getBytes(encoding)), SecureUtil.sha1X16(stringData, encoding));
		}
		catch (final UnsupportedEncodingException e)
		{
			LOG.error(String.format("order %s validate response  data ,unsupprot encoding error", orderId), e);
			return false;
		}
		catch (final Exception e)
		{
			LOG.error(String.format("order %s validate response data error", orderId), e);
			return false;
		}
	}

}
