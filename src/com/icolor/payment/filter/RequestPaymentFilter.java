package com.icolor.payment.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.icolor.payment.data.PrePaymentResult;
import com.icolor.payment.handle.PrePayHandle;
import com.icolor.payment.service.impl.UnionPayPreHandle;


public class RequestPaymentFilter implements Filter
{

	private static final Logger LOG = Logger.getLogger(RequestPaymentFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
	{
		HttpServletResponse resp = (HttpServletResponse) response;
		LOG.info("invoke unionpay...");
		PrePayHandle handle = new UnionPayPreHandle();
		PrePaymentResult result = handle.prePay("test" + System.currentTimeMillis(), "0.01");
		LOG.info("invoke unionpay result is:" + result);
		if (result.isSuccess())
		{
			String html = result.getData();
			resp.getWriter().write(html);
			resp.getWriter().flush();
			resp.getWriter().close();
		}
	}

	public void init(FilterConfig fConfig) throws ServletException
	{
	}

	public void destroy()
	{

	}

}
