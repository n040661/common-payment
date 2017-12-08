package com.icolor.payment.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icolor.payment.unionpay.util.SDKConstants;
import com.icolor.payment.unionpay.util.SDKUtil;


@WebServlet("/commonCallbackTest")
public class PaymentCallbackController extends HttpServlet
{
	private static final Logger LOG = Logger.getLogger(PaymentCallbackController.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException
	{
		LOG.info("third party back callback...");

		final Map<String, String> requestParam = SDKUtil.getAllRequestParam(request);

		for (Map.Entry<String, String> entry : requestParam.entrySet())
		{
			LOG.info("callback result:[" + entry.getKey() + "," + entry.getValue() + "]");
		}

		Map<String, String> valideData = null;

		if (MapUtils.isEmpty(requestParam))
		{
			LOG.error("get unionpay payment callback data is empty from frontend,can't handle order status");
		}

		final String orderId = request.getParameter(SDKConstants.PARAM_ORDERID);
		final String encoding = request.getParameter(SDKConstants.PARAM_ENCODING);

		if (StringUtils.isEmpty(orderId))
		{
			LOG.error("order get unionpay callback from frontend , orderId is empty.");
			return;
		}

		valideData = SDKUtil.encodingUnionpayParams(requestParam, encoding);

		if (MapUtils.isEmpty(valideData))
		{
			LOG.error(String.format(
					"order [%s] get unionpay callback data from frontend ,encoding result is empty,can't handle order status",
					orderId));
			return;
		}

		//do not change the response data before validate data.
		/*
		 * if (!icolorPaymentFacade.validate(valideData, encoding)) { LOG.error(String.format(
		 * "order [%s] get unionpay callback from frontend, validate failure,can't handle order stauts", orderId)); }
		 */

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		LOG.info("back doPost...");
		this.doGet(req, resp);
	}
}
