package com.icolor.payment.unionpay.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import com.icolor.payment.unionpay.util.BaseHttpSSLSocketFactory.TrustAnyHostnameVerifier;


public class HttpClient
{

	private static final Logger LOG = Logger.getLogger(HttpClient.class);

	/**
	 * 目标地址
	 */
	private URL url;

	/**
	 * 通信连接超时时间
	 */
	private int connectionTimeout = 3000;

	/**
	 * 通信读超时时间
	 */
	private int readTimeOut = 3000;

	/**
	 * 通信结果
	 */
	private String result;

	/**
	 * 获取通信结果
	 *
	 * @return
	 */
	public String getResult()
	{
		return result;
	}

	/**
	 * 设置通信结果
	 *
	 * @param result
	 */
	public void setResult(final String result)
	{
		this.result = result;
	}

	/**
	 * 构造函数
	 *
	 * @param url
	 *           目标地址
	 * @param connectionTimeout
	 *           HTTP连接超时时间
	 * @param readTimeOut
	 *           HTTP读写超时时间
	 */
	public HttpClient(final String url)
	{
		try
		{
			this.url = new URL(url);
		}
		catch (final MalformedURLException e)
		{
			LOG.error("construct Httpclient error,", e);
		}
	}

	/**
	 * 发送信息到服务端
	 *
	 * @param data
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public int send(final Map<String, String> data, final String encoding) throws Exception
	{
		try
		{
			final HttpURLConnection httpURLConnection = createConnection(encoding);
			if (null == httpURLConnection)
			{
				throw new Exception("创建联接失败");
			}
			final String sendData = SDKUtil.getRequestParamString(data, encoding);
			LOG.info(String.format("send to unionpay request data:", sendData));
			this.requestServer(httpURLConnection, sendData, encoding);
			this.result = this.response(httpURLConnection, encoding);
			LOG.info(String.format("synchronize return result:", result));
			return httpURLConnection.getResponseCode();
		}
		catch (final Exception e)
		{
			LOG.error("request unionpay error,", e);
			throw e;
		}
	}

	public int sendForPOS(final String sendData, final String encoding) throws Exception
	{
		try
		{
			final HttpURLConnection httpURLConnection = createConnection(encoding);
			if (null == httpURLConnection)
			{
				throw new Exception("创建联接失败");
			}
			this.requestServer(httpURLConnection, sendData, encoding);
			this.result = this.response(httpURLConnection, encoding);
			return httpURLConnection.getResponseCode();
		}
		catch (final Exception e)
		{
			throw e;
		}
	}

	/**
	 * 发送信息到服务端 GET方式
	 *
	 * @param data
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public int sendGet(final String encoding) throws Exception
	{
		try
		{
			final HttpURLConnection httpURLConnection = createConnectionGet(encoding);
			if (null == httpURLConnection)
			{
				LOG.error("create connection failure");
				throw new Exception("create connection failure");
			}
			this.result = this.response(httpURLConnection, encoding);
			LOG.info(String.format("type GET to get unionpay synchronized response is:", result));
			return httpURLConnection.getResponseCode();
		}
		catch (final Exception e)
		{
			throw e;
		}
	}


	/**
	 * HTTP Post发送消息
	 *
	 * @param connection
	 * @param message
	 * @throws IOException
	 */
	private void requestServer(final URLConnection connection, final String message, final String encoder) throws Exception
	{
		PrintStream out = null;
		try
		{
			connection.connect();
			out = new PrintStream(connection.getOutputStream(), false, encoder);
			out.print(message);
			out.flush();
		}
		catch (final Exception e)
		{
			throw e;
		}
		finally
		{
			if (null != out)
			{
				out.close();
			}
		}
	}

	/**
	 * 显示Response消息
	 *
	 * @param connection
	 * @param CharsetName
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	private String response(final HttpURLConnection connection, final String encoding)
			throws URISyntaxException, IOException, Exception
	{
		InputStream in = null;
		final StringBuilder sb = new StringBuilder();
		try
		{
			if (200 == connection.getResponseCode())
			{
				in = connection.getInputStream();
				sb.append(new String(read(in), encoding));
			}
			else
			{
				in = connection.getErrorStream();
				sb.append(new String(read(in), encoding));
			}
			LOG.info(String.format("HTTP Return Status-Code:[%s]", connection.getResponseCode()));
			return sb.toString();
		}
		catch (final Exception e)
		{
			throw e;
		}
		finally
		{
			if (null != in)
			{
				in.close();
			}
			if (null != connection)
			{
				connection.disconnect();
			}
		}
	}

	public static byte[] read(final InputStream in) throws IOException
	{
		final byte[] buf = new byte[1024];
		int length = 0;
		final ByteArrayOutputStream bout = new ByteArrayOutputStream();
		while ((length = in.read(buf, 0, buf.length)) > 0)
		{
			bout.write(buf, 0, length);
		}
		bout.flush();
		return bout.toByteArray();
	}

	/**
	 * 创建连接
	 *
	 * @return
	 * @throws ProtocolException
	 */
	private HttpURLConnection createConnection(final String encoding) throws ProtocolException
	{
		HttpURLConnection httpURLConnection = null;
		try
		{
			httpURLConnection = (HttpURLConnection) url.openConnection();
		}
		catch (final IOException e)
		{
			LOG.error("create http url connection error,", e);
			return null;
		}
		httpURLConnection.setConnectTimeout(this.connectionTimeout);// 连接超时时间
		httpURLConnection.setReadTimeout(this.readTimeOut);// 读取结果超时时间
		httpURLConnection.setDoInput(true); // 可读
		httpURLConnection.setDoOutput(true); // 可写
		httpURLConnection.setUseCaches(false);// 取消缓存
		httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + encoding);
		httpURLConnection.setRequestMethod("POST");
		if ("https".equalsIgnoreCase(url.getProtocol()))
		{
			final HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
			husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
			husn.setHostnameVerifier(new TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
			return husn;
		}
		return httpURLConnection;
	}

	/**
	 * 创建连接
	 *
	 * @return
	 * @throws ProtocolException
	 */
	private HttpURLConnection createConnectionGet(final String encoding) throws ProtocolException
	{
		HttpURLConnection httpURLConnection = null;
		try
		{
			httpURLConnection = (HttpURLConnection) url.openConnection();
		}
		catch (final IOException e)
		{
			LOG.error("create http connection error by GET type", e);
			return null;
		}
		httpURLConnection.setConnectTimeout(this.connectionTimeout);// 连接超时时间
		httpURLConnection.setReadTimeout(this.readTimeOut);// 读取结果超时时间
		httpURLConnection.setUseCaches(false);// 取消缓存
		httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + encoding);
		httpURLConnection.setRequestMethod("GET");
		if ("https".equalsIgnoreCase(url.getProtocol()))
		{
			final HttpsURLConnection husn = (HttpsURLConnection) httpURLConnection;
			husn.setSSLSocketFactory(new BaseHttpSSLSocketFactory());
			husn.setHostnameVerifier(new TrustAnyHostnameVerifier());//解决由于服务器证书问题导致HTTPS无法访问的情况
			return husn;
		}
		return httpURLConnection;
	}

}
