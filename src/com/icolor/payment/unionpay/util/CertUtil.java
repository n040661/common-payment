package com.icolor.payment.unionpay.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;


public class CertUtil
{

	private static final Logger LOG = Logger.getLogger(CertUtil.class);

	private final static String CERTS_CER = "/unionpay/certs/acp_prod_enc.cer";

	private final static String CERTS_PFX = "/unionpay/certs/privateKey.pfx";

	private final static String CERTS_VALIDATECERT = "/unionpay/certs/acp_prod_verify_sign.cer";

	private static final String SIGN_CERT_PWD = "111111";

	private static final String SIGN_CERT_TYPE = "PKCS12";

	/** 证书容器. */
	private static KeyStore keyStore = null;
	/** 密码加密证书 */
	private static X509Certificate encryptCert = null;

	/** 验证签名证书. */
	private static X509Certificate validateCert = null;
	/** 验签证书存储Map. */
	private static Map<String, X509Certificate> certMap = new HashMap<String, X509Certificate>();

	private final static Map<String, KeyStore> certKeyStoreMap = new ConcurrentHashMap<String, KeyStore>();

	static
	{
		try
		{
			init();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 添加签名，验签，加密算法提供者
	 */
	private static void addProvider()
	{
		if (Security.getProvider("BC") == null)
		{
			LOG.info("add BC provider");
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		}
		else
		{
			Security.removeProvider("BC"); // 解决eclipse调试时tomcat自动重新加载时，BC存在不明原因异常的问题。
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			LOG.info("re-add BC provider");
		}
		// printSysInfo();
	}

	/**
	 * 初始化所有证书.
	 *
	 * @throws IOException
	 */
	public static void init() throws IOException
	{
		addProvider();
		// 单证书模式,初始化配置文件中的签名证书
		initSignCert();
		initEncryptCert();// 初始化加密公钥证书
		initValidateCertFromDir();// 初始化所有的验签证书
	}

	/**
	 * 加载签名证书
	 */
	public static void initSignCert()
	{
		if (null != keyStore)
		{
			keyStore = null;
		}
		try
		{
			keyStore = getKeyInfo(CERTS_PFX, SIGN_CERT_PWD, SIGN_CERT_TYPE);
			LOG.info(String.format("InitSignCert Successful. CertId=[%s]", getSignCertId()));
		}
		catch (final IOException e)
		{
			LOG.error("InitSignCert Error", e);
		}
	}

	/**
	 * 加载RSA签名证书
	 *
	 * @param certFilePath
	 * @param certPwd
	 */
	public static void loadRsaCert(final String certFilePath, final String certPwd)
	{
		KeyStore keyStore = null;
		try
		{
			keyStore = getKeyInfo(CERTS_CER, certPwd, SDKConstants.CERT_PKC);// certFilePath
			certKeyStoreMap.put(CERTS_CER, keyStore);// certFilePath
			LOG.info("LoadRsaCert Successful");
		}
		catch (final IOException e)
		{
			LOG.error("LoadRsaCert Error", e);
		}
	}

	/**
	 * 加载密码加密证书 目前支持有两种加密
	 */
	private static void initEncryptCert()
	{
		LOG.info(String.format("load sensitive info cert", CERTS_CER));
		if (!SDKUtil.isEmpty(CERTS_CER))
		{
			encryptCert = initCert(CERTS_CER);
			LOG.info("LoadEncryptCert Successful");
		}
		else
		{
			LOG.info("WARN: acpsdk.encryptCert.path is empty");
		}
	}

	/**
	 *
	 * @param path
	 * @return
	 */
	private static X509Certificate initCert(final String path)
	{
		X509Certificate encryptCertTemp = null;
		CertificateFactory cf = null;
		InputStream in = null;
		try
		{
			cf = CertificateFactory.getInstance("X.509", "BC");
			in = CertUtil.class.getResourceAsStream(path);
			encryptCertTemp = (X509Certificate) cf.generateCertificate(in);
			// 打印证书加载信息,供测试阶段调试
			LOG.info(String.format("[%s] [CertId=%s]", path, encryptCertTemp.getSerialNumber().toString()));
		}
		catch (final CertificateException e)
		{
			LOG.error("InitCert Error", e);

		}
		catch (final NoSuchProviderException e)
		{
			LOG.error("LoadVerifyCert Error No BC Provider", e);
		}
		finally
		{
			if (null != in)
			{
				try
				{
					in.close();
				}
				catch (final IOException e)
				{
					LOG.error(e.toString());
				}
			}
		}
		return encryptCertTemp;
	}

	/**
	 * 从指定目录下加载验证签名证书
	 *
	 */
	public static void initValidateCertFromDir() throws IOException
	{
		certMap.clear();
		CertificateFactory cf = null;
		final List<InputStream> inputList = new ArrayList<InputStream>();
		inputList.add(CertUtil.class.getResourceAsStream(CERTS_VALIDATECERT));
		inputList.add(CertUtil.class.getResourceAsStream(CERTS_CER));
		try
		{
			cf = CertificateFactory.getInstance("X.509");
			for (final InputStream in : inputList)
			{
				validateCert = (X509Certificate) cf.generateCertificate(in);
				certMap.put(validateCert.getSerialNumber().toString(), validateCert);
			}
		}
		catch (final CertificateException e)
		{
			LOG.error("LoadVerifyCert Error", e);
		}
	}

	/**
	 * 获取签名证书私钥（单证书模式）
	 *
	 * @return
	 */
	public static PrivateKey getSignCertPrivateKey()
	{
		try
		{
			final Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements())
			{
				keyAlias = aliasenum.nextElement();
			}
			final PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, SIGN_CERT_PWD.toCharArray());
			return privateKey;
		}
		catch (final KeyStoreException e)
		{
			LOG.error("getSignCertPrivateKey Error", e);
			return null;
		}
		catch (final UnrecoverableKeyException e)
		{
			LOG.error("getSignCertPrivateKey Error", e);
			return null;
		}
		catch (final NoSuchAlgorithmException e)
		{
			LOG.error("getSignCertPrivateKey Error", e);
			return null;
		}
	}

	public static PrivateKey getSignCertPrivateKeyByStoreMap(final String certPath, final String certPwd)
	{
		if (!certKeyStoreMap.containsKey(certPath))
		{
			loadRsaCert(certPath, certPwd);
		}
		try
		{
			final Enumeration<String> aliasenum = certKeyStoreMap.get(certPath).aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements())
			{
				keyAlias = aliasenum.nextElement();
			}
			final PrivateKey privateKey = (PrivateKey) certKeyStoreMap.get(certPath).getKey(keyAlias, certPwd.toCharArray());
			return privateKey;
		}
		catch (final KeyStoreException e)
		{
			LOG.error("getSignCertPrivateKeyByStoreMap Error", e);
			return null;
		}
		catch (final UnrecoverableKeyException e)
		{
			LOG.error("getSignCertPrivateKeyByStoreMap Error", e);
			return null;
		}
		catch (final NoSuchAlgorithmException e)
		{
			LOG.error("getSignCertPrivateKeyByStoreMap Error", e);
			return null;
		}
	}

	/**
	 * 获取加密证书公钥.密码加密时需要
	 *
	 * @return
	 */
	public static PublicKey getEncryptCertPublicKey()
	{
		if (null == encryptCert)
		{
			if (!SDKUtil.isEmpty(CERTS_CER))
			{
				encryptCert = initCert(CERTS_CER);
				return encryptCert.getPublicKey();
			}
			else
			{
				LOG.info("ERROR: acpsdk.encryptCert.path is empty");
				return null;
			}
		}
		else
		{
			return encryptCert.getPublicKey();
		}
	}

	/**
	 * 验证签名证书
	 *
	 * @return 验证签名证书的公钥
	 */
	public static PublicKey getValidateKey()
	{
		if (null == validateCert)
		{
			return null;
		}
		return validateCert.getPublicKey();
	}

	/**
	 * 通过certId获取证书Map中对应证书的公钥
	 *
	 * @param certId
	 *           证书物理序号
	 * @return 通过证书编号获取到的公钥
	 * @throws IOException
	 */
	public static PublicKey getValidateKey(final String certId) throws IOException
	{
		X509Certificate cf = null;
		if (certMap.containsKey(certId))
		{
			// 存在certId对应的证书对象
			cf = certMap.get(certId);
			return cf.getPublicKey();
		}
		else
		{
			// 不存在则重新Load证书文件目录
			initValidateCertFromDir();
			if (certMap.containsKey(certId))
			{
				// 存在certId对应的证书对象
				cf = certMap.get(certId);
				return cf.getPublicKey();
			}
			else
			{
				LOG.error("lost certId=[" + certId + "] validate signature cert.");
				return null;
			}
		}
	}

	/**
	 * 获取签名证书中的证书序列号（单证书）
	 *
	 * @return 证书的物理编号
	 */
	public static String getSignCertId()
	{
		try
		{
			final Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements())
			{
				keyAlias = aliasenum.nextElement();
			}
			final X509Certificate cert = (X509Certificate) keyStore.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		}
		catch (final Exception e)
		{
			LOG.error("getSignCertId Error", e);
			return null;
		}
	}

	/**
	 * 获取加密证书的证书序列号
	 *
	 * @return
	 */
	public static String getEncryptCertId()
	{
		if (null == encryptCert)
		{
			if (!SDKUtil.isEmpty(CERTS_CER))
			{
				encryptCert = initCert(CERTS_CER);
				return encryptCert.getSerialNumber().toString();
			}
			else
			{
				LOG.info("ERROR: acpsdk.encryptCert.path is empty");
				return null;
			}
		}
		else
		{
			return encryptCert.getSerialNumber().toString();
		}
	}

	/**
	 * 获取签名证书公钥对象
	 *
	 * @return
	 */
	public static PublicKey getSignPublicKey()
	{
		try
		{
			final Enumeration<String> aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements())
			{
				keyAlias = aliasenum.nextElement();
			}
			final Certificate cert = keyStore.getCertificate(keyAlias);
			final PublicKey pubkey = cert.getPublicKey();
			return pubkey;
		}
		catch (final Exception e)
		{
			LOG.error(e.toString());
			return null;
		}
	}

	/**
	 * 将证书文件读取为证书存储对象
	 *
	 * @param pfxkeyfile
	 *           证书文件名
	 * @param keypwd
	 *           证书密码
	 * @param type
	 *           证书类型
	 * @return 证书对象
	 * @throws IOException
	 */
	public static KeyStore getKeyInfo(final String pfxkeyfile, final String keypwd, final String type) throws IOException
	{

		LOG.info("加载签名证书==>" + pfxkeyfile);

		InputStream fis = null;
		try
		{
			final KeyStore ks = KeyStore.getInstance(type, "BC");
			LOG.info(String.format("Load RSA CertPath=[%s],Pwd=[],type=[]", pfxkeyfile, keypwd, type));
			fis = CertUtil.class.getResourceAsStream(pfxkeyfile);
			char[] nPassword = null;
			nPassword = null == keypwd || "".equals(keypwd.trim()) ? null : keypwd.toCharArray();
			if (null != ks)
			{
				ks.load(fis, nPassword);
			}
			return ks;
		}
		catch (final Exception e)
		{
			if (Security.getProvider("BC") == null)
			{
				LOG.info("BC Provider not installed.");
			}
			LOG.error("getKeyInfo Error", e);
			if ((e instanceof KeyStoreException) && "PKCS12".equals(type))
			{
				Security.removeProvider("BC");
			}
			return null;
		}
		finally
		{
			if (null != fis)
			{
				fis.close();
			}
		}
	}

	// 打印系统环境信息
	public static void printSysInfo()
	{
		LOG.info("================= SYS INFO begin====================");
		LOG.info("os_name:" + System.getProperty("os.name"));
		LOG.info("os_arch:" + System.getProperty("os.arch"));
		LOG.info("os_version:" + System.getProperty("os.version"));
		LOG.info("java_vm_specification_version:" + System.getProperty("java.vm.specification.version"));
		LOG.info("java_vm_specification_vendor:" + System.getProperty("java.vm.specification.vendor"));
		LOG.info("java_vm_specification_name:" + System.getProperty("java.vm.specification.name"));
		LOG.info("java_vm_version:" + System.getProperty("java.vm.version"));
		LOG.info("java_vm_name:" + System.getProperty("java.vm.name"));
		LOG.info("java.version:" + System.getProperty("java.version"));
		LOG.info("java.vm.vendor=[" + System.getProperty("java.vm.vendor") + "]");
		LOG.info("java.version=[" + System.getProperty("java.version") + "]");
		printProviders();
		LOG.info("================= SYS INFO end=====================");
	}

	public static void printProviders()
	{
		LOG.info("Providers List:");
		final Provider[] providers = Security.getProviders();
		for (int i = 0; i < providers.length; i++)
		{
			LOG.info(i + 1 + "." + providers[i].getName());
		}
	}

	/**
	 * 证书文件过滤器
	 *
	 */
	static class CerFilter implements FilenameFilter
	{
		public boolean isCer(final String name)
		{
			if (name.toLowerCase().endsWith(".cer"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}

		public boolean accept(final File dir, final String name)
		{
			return isCer(name);
		}
	}

	public static String getCertIdByKeyStoreMap(final String certPath, final String certPwd)
	{
		if (!certKeyStoreMap.containsKey(certPath))
		{
			// 缓存中未查询到,则加载RSA证书
			loadRsaCert(certPath, certPwd);
		}
		return getCertIdIdByStore(certKeyStoreMap.get(certPath));
	}

	private static String getCertIdIdByStore(final KeyStore keyStore)
	{
		Enumeration<String> aliasenum = null;
		try
		{
			aliasenum = keyStore.aliases();
			String keyAlias = null;
			if (aliasenum.hasMoreElements())
			{
				keyAlias = aliasenum.nextElement();
			}
			final X509Certificate cert = (X509Certificate) keyStore.getCertificate(keyAlias);
			return cert.getSerialNumber().toString();
		}
		catch (final KeyStoreException e)
		{
			LOG.error("getCertIdIdByStore Error", e);
			return null;
		}
	}

	/**
	 * 获取证书容器
	 *
	 * @return
	 */
	public static Map<String, X509Certificate> getCertMap()
	{
		return certMap;
	}

	/**
	 * 设置证书容器
	 *
	 * @param certMap
	 */
	public static void setCertMap(final Map<String, X509Certificate> certMap)
	{
		CertUtil.certMap = certMap;
	}

	/**
	 * 使用模和指数生成RSA公钥 注意：此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同
	 *
	 * @param modulus
	 *           模
	 * @param exponent
	 *           指数
	 * @return
	 */
	public static PublicKey getPublicKey(final String modulus, final String exponent)
	{
		try
		{
			final BigInteger b1 = new BigInteger(modulus);
			final BigInteger b2 = new BigInteger(exponent);
			final KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			final RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return keyFactory.generatePublic(keySpec);
		}
		catch (final Exception e)
		{
			LOG.error(String.format("construct RSA public key failure"), e);
			return null;
		}
	}

	/**
	 * 使用模和指数的方式获取公钥对象
	 *
	 * @return
	 */
	public static PublicKey getEncryptTrackCertPublicKey(final String modulus, final String exponent)
	{
		if (SDKUtil.isEmpty(modulus) || SDKUtil.isEmpty(exponent))
		{
			LOG.error("[modulus] OR [exponent] invalid");
			return null;
		}
		return getPublicKey(modulus, exponent);
	}
}
