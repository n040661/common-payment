package com.icolor.payment.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.icolor.payment.enums.PaymentType;
import com.icolor.payment.service.CommonPaymentService;
import com.icolor.payment.service.impl.CommonPaymentServiceImpl;
import com.icolor.payment.unionpay.util.SDKConstants;


public class PaymentFilter implements Filter
{
	private static final Logger LOG = Logger.getLogger(PaymentFilter.class);

	public void destroy()
	{

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		LOG.info("third party back callback...");

		PaymentType payType = null;

		//1. check pay type
		String reqReserved = request.getParameter(SDKConstants.param_reqReserved);
		if (StringUtils.equals(SDKConstants.PAY_TYPE, reqReserved))
		{
			payType = PaymentType.Union;
		}

		if (payType == null)
		{
			LOG.error(String.format("callback get pay type erorr,pay type is empty"));
			return;
		}

		//回调处理
		CommonPaymentService sercice = CommonPaymentServiceImpl.getInstance();
		sercice.callback(request, payType);

		//响应第三方支付结果
		PrintWriter pw = response.getWriter();
		if (PaymentType.Union.equals(payType))
		{
			pw.write(SDKConstants.RESP_SUCCESS_MSG);
		}
		pw.flush();
		pw.close();

	}

	public void init(FilterConfig arg0) throws ServletException
	{

	}

}
