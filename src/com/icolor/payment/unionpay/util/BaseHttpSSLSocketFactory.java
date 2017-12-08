package com.icolor.payment.unionpay.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;


public class BaseHttpSSLSocketFactory extends SSLSocketFactory
{
	private static final Logger LOG = Logger.getLogger(BaseHttpSSLSocketFactory.class);

	private SSLContext getSSLContext()
	{
		return createEasySSLContext();
	}

	@Override
	public Socket createSocket(final InetAddress arg0, final int arg1, final InetAddress arg2, final int arg3) throws IOException
	{
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1, arg2, arg3);
	}

	@Override
	public Socket createSocket(final String arg0, final int arg1, final InetAddress arg2, final int arg3)
			throws IOException, UnknownHostException
	{
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1, arg2, arg3);
	}

	@Override
	public Socket createSocket(final InetAddress arg0, final int arg1) throws IOException
	{
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
	}

	@Override
	public Socket createSocket(final String arg0, final int arg1) throws IOException, UnknownHostException
	{
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1);
	}

	@Override
	public String[] getSupportedCipherSuites()
	{
		return null;
	}

	@Override
	public String[] getDefaultCipherSuites()
	{
		return null;
	}

	@Override
	public Socket createSocket(final Socket arg0, final String arg1, final int arg2, final boolean arg3) throws IOException
	{
		return getSSLContext().getSocketFactory().createSocket(arg0, arg1, arg2, arg3);
	}

	private SSLContext createEasySSLContext()
	{
		try
		{
			final SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[]
			{ MyX509TrustManager.manger }, null);
			return context;
		}
		catch (final Exception e)
		{
			LOG.error("create SSL context error,", e);
			return null;
		}
	}

	public static class MyX509TrustManager implements X509TrustManager
	{

		static MyX509TrustManager manger = new MyX509TrustManager();

		public MyX509TrustManager()
		{
		}

		public X509Certificate[] getAcceptedIssuers()
		{
			return null;
		}

		public void checkClientTrusted(final X509Certificate[] chain, final String authType)
		{
		}

		public void checkServerTrusted(final X509Certificate[] chain, final String authType)
		{
		}
	}

	/**
	 * 解决由于服务器证书问题导致HTTPS无法访问的情况 PS:HTTPS hostname wrong: should be <localhost>
	 */
	public static class TrustAnyHostnameVerifier implements HostnameVerifier
	{
		public boolean verify(final String hostname, final SSLSession session)
		{
			// 直接返回true
			return true;
		}
	}
}
