package com.icolor.payment.unionpay.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


public class SDKUtil
{

	private static final Logger LOG = Logger.getLogger(SDKUtil.class);

	/**
	 * 生成签名(SHA1摘要算法)
	 *
	 * @param data
	 *           待签名数据Map键对形式
	 * @param encoding
	 *           编码
	 * @return 签名是否成功
	 */
	public static boolean sign(final Map<String, String> data, String encoding)
	{
		final String orderId = data.get(SDKConstants.PARAM_ORDERID);

		if (isEmpty(encoding))
		{
			encoding = SDKConstants.ENCODING_UTF8;
		}

		data.put(SDKConstants.PARAM_CERTID, CertUtil.getSignCertId());
		final String stringData = coverMap2String(data);

		//base 64 encoding
		byte[] byteSign = null;
		String stringSign = null;
		try
		{
			final byte[] signDigest = SecureUtil.sha1X16(stringData, encoding);
			byteSign = SecureUtil.base64Encode(SecureUtil.signBySoft(CertUtil.getSignCertPrivateKey(), signDigest));
			stringSign = new String(byteSign);
			data.put(SDKConstants.PARAM_SIGNATURE, stringSign);
			return true;
		}
		catch (final Exception e)
		{
			LOG.error(String.format("order %s sign error", orderId));
			return false;
		}
	}

	/**
	 * 将Map中的数据转换成按照Key的ascii码排序后的key1=value1&key2=value2的形 不包含签名域signature
	 *
	 * @param data
	 *           待拼接的Map数据
	 * @return 拼接好后的字符串
	 */
	public static String coverMap2String(final Map<String, String> data)
	{
		final TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext())
		{
			final Entry<String, String> en = it.next();
			if (SDKConstants.PARAM_SIGNATURE.equals(en.getKey().trim()))
			{
				continue;
			}
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		final StringBuffer sf = new StringBuffer();
		while (it.hasNext())
		{
			final Entry<String, String> en = it.next();
			sf.append(en.getKey() + SDKConstants.EQUAL + en.getValue() + SDKConstants.AMPERSAND);
		}
		return sf.substring(0, sf.length() - 1);
	}

	/**
	 * 兼容老方 将形如key=value&key=value的字符串转换为相应的Map对象
	 *
	 * @param result
	 * @return
	 */
	public static Map<String, String> coverResultString2Map(final String result)
	{
		return convertResultStringToMap(result);
	}

	/**
	 * 将形如key=value&key=value的字符串转换为相应的Map对象
	 *
	 * @param result
	 * @return
	 */
	public static Map<String, String> convertResultStringToMap(String result)
	{
		Map<String, String> map = null;
		try
		{

			if (StringUtils.isNotBlank(result))
			{
				if (result.startsWith("{") && result.endsWith("}"))
				{
					System.out.println(result.length());
					result = result.substring(1, result.length() - 1);
				}
				map = parseQString(result);
			}

		}
		catch (final UnsupportedEncodingException e)
		{
			LOG.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 解析应答字符串，生成应答要素
	 *
	 * @param str
	 *           要解析的字符
	 * @return 解析的结果map
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseQString(final String str) throws UnsupportedEncodingException
	{

		final Map<String, String> map = new HashMap<String, String>();
		final int len = str.length();
		final StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;
		boolean isOpen = false;// 值里有嵌
		char openName = 0;
		if (len > 0)
		{
			for (int i = 0; i < len; i++)
			{// 遍历整个带解析的字符
				curChar = str.charAt(i);// 取当前字
				if (isKey)
				{// 如果当前生成的是key

					if (curChar == '=')
					{// 如果读取=分隔
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					}
					else
					{
						temp.append(curChar);
					}
				}
				else
				{// 如果当前生成的是value
					if (isOpen)
					{
						if (curChar == openName)
						{
							isOpen = false;
						}

					}
					else
					{// 如果没开启嵌
						if (curChar == '{')
						{// 如果碰到，就启嵌
							isOpen = true;
							openName = '}';
						}
						if (curChar == '[')
						{
							isOpen = true;
							openName = ']';
						}
					}
					if (curChar == '&' && !isOpen)
					{// 如果读取&分割,同时这个分割符不是域，这时将map里添
						putKeyValueToMap(temp, isKey, key, map);
						temp.setLength(0);
						isKey = true;
					}
					else
					{
						temp.append(curChar);
					}
				}

			}
			putKeyValueToMap(temp, isKey, key, map);
		}
		return map;
	}

	private static void putKeyValueToMap(final StringBuilder temp, final boolean isKey, String key, final Map<String, String> map)
			throws UnsupportedEncodingException
	{
		if (isKey)
		{
			key = temp.toString();
			if (key.length() == 0)
			{
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		}
		else
		{
			if (key.length() == 0)
			{
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, temp.toString());
		}
	}

	/**
	 * 判断字符串是否为NULL或空
	 *
	 * @param s
	 *           待判断的字符串数
	 * @return 判断结果 true- false-
	 */
	public static boolean isEmpty(final String s)
	{
		return null == s || "".equals(s.trim());
	}

	/**
	 * 过滤请求报文中的空字符串或空字符
	 *
	 * @param contentData
	 * @return
	 */
	public static Map<String, String> filterBlank(final Map<String, String> contentData)
	{
		LOG.info("打印请求报文 :");
		final Map<String, String> submitFromData = new HashMap<String, String>();
		final Set<String> keyset = contentData.keySet();

		for (final String key : keyset)
		{
			final String value = contentData.get(key);
			if (StringUtils.isNotBlank(value))
			{
				// 对value值进行去除前后空处理
				submitFromData.put(key, value.trim());
				LOG.info(key + "-->" + String.valueOf(value));
			}
		}
		return submitFromData;
	}

	/**
	 * 组装请求，返回报文字符串用于显示
	 *
	 * @param data
	 * @return
	 */
	public static String getHtmlResult(final Map<String, String> data)
	{

		final TreeMap<String, String> tree = new TreeMap<String, String>();
		Iterator<Entry<String, String>> it = data.entrySet().iterator();
		while (it.hasNext())
		{
			final Entry<String, String> en = it.next();
			tree.put(en.getKey(), en.getValue());
		}
		it = tree.entrySet().iterator();
		final StringBuffer sf = new StringBuffer();
		while (it.hasNext())
		{
			final Entry<String, String> en = it.next();
			final String key = en.getKey();
			final String value = en.getValue();
			if ("respCode".equals(key))
			{
				sf.append("<b>" + key + SDKConstants.EQUAL + value + "</br></b>");
			}
			else
			{
				sf.append(key + SDKConstants.EQUAL + value + "</br>");
			}
		}
		return sf.toString();
	}

	/**
	 * 1 yuan convert 100 switch money,from yuan to feng
	 *
	 * @param yuan
	 * @return
	 */
	public static String yuanSwitchfen(final String yuan)
	{
		if (StringUtils.isEmpty(yuan))
		{
			return null;
		}

		BigDecimal yuanDecimal = new BigDecimal(yuan);
		yuanDecimal = yuanDecimal.multiply(new BigDecimal(SDKConstants.CURRENCY_RATE));
		String fen = yuanDecimal.toString();
		int count = fen.indexOf(SDKConstants.POINT);
		if (count == -1)
		{
			count = fen.length();
		}
		return fen = fen.substring(0, count);
	}

	/**
	 * 100 fen convert 1 yuan
	 *
	 * @param fen
	 * @return
	 */
	public static String fenSwitchyuan(final String fen)
	{
		if (StringUtils.isEmpty(fen))
		{
			return null;
		}
		BigDecimal fenDecimal = new BigDecimal(fen);
		fenDecimal = fenDecimal.divide(new BigDecimal(SDKConstants.CURRENCY_RATE));
		return fenDecimal.toString();
	}

	/**
	 * 获取请求参数中所有的信息
	 *
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request)
	{
		final Map<String, String> res = new HashMap<String, String>();
		final Enumeration<?> temp = request.getParameterNames();
		if (null == temp)
		{
			return null;
		}
		while (temp.hasMoreElements())
		{
			final String en = (String) temp.nextElement();
			final String value = request.getParameter(en);

			// 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段
			if (null == value || "".equals(value))
			{
				continue;
			}
			res.put(en, value);
		}
		return res;
	}

	public static Map<String, String> encodingUnionpayParams(final Map<String, String> reqParam, final String encoding)
	{
		if (null == reqParam || reqParam.isEmpty())
		{
			return null;
		}
		final Map<String, String> valideData = new HashMap<String, String>(reqParam.size());
		for (final Map.Entry<String, String> entry : reqParam.entrySet())
		{
			final String key = entry.getKey();
			String value = entry.getValue();
			try
			{
				value = new String(value.getBytes(encoding), encoding);
			}
			catch (final UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			valideData.put(key, value);
		}
		return valideData;
	}

	/**
	 * 将Map存储的对象，转换为key=value&key=value的字符
	 *
	 * @param requestParam
	 * @param coder
	 * @return
	 */
	public static String getRequestParamString(final Map<String, String> requestParam, String coder)
	{
		if (MapUtils.isEmpty(requestParam))
		{
			LOG.error("request data is empty ,return null");
			return null;
		}

		if (null == coder || "".equals(coder))
		{
			coder = SDKConstants.ENCODING_UTF8;
		}
		final StringBuffer sf = new StringBuffer();

		for (final Entry<String, String> en : requestParam.entrySet())
		{
			try
			{
				if (sf.length() > 0)
				{
					sf.append(SDKConstants.AMPERSAND);
				}
				final String value = en.getValue();

				sf.append(en.getKey());

				sf.append(SDKConstants.EQUAL);

				if (StringUtils.isEmpty(value))
				{
					sf.append("");
				}
				else
				{
					sf.append(URLEncoder.encode(value, coder));
				}
			}
			catch (final UnsupportedEncodingException e)
			{
				LOG.error("encoding request data error,", e);
				return null;
			}
		}
		LOG.info(String.format("request data[%s] has encoding by URLEncoding", sf.toString()));
		return sf.toString();
	}

	/**
	 * create random number ,
	 * 
	 * @param index
	 *           num length,last 6
	 * @return
	 */
	public static String random(final int index)
	{
		final Random random = new Random();
		final int x = random.nextInt(999999);
		String str = Integer.toString(x);
		while (str.length() < index)
		{
			str += SDKConstants.ZERO;
		}
		return str;
	}

}
