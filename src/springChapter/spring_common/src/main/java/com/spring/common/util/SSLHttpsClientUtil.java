package com.spring.common.util;

import java.security.Principal;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SSLHttpsClientUtil {

	private String protocol = "TLSv1";
	private SSLConnectionSocketFactory sslsf;
	// private CloseableHttpClient client;

	private static Logger logger = LoggerFactory
			.getLogger(SSLHttpsClientUtil.class);

	private static class Holder {
		protected static SSLHttpsClientUtil instance = new SSLHttpsClientUtil();
	}

	private SSLHttpsClientUtil() {
		try {
			initSSLConnectionSocketFactory();
		} catch (Exception ex) {
			logger.error("Exception", ex);
		}
	}

	public static SSLHttpsClientUtil getInstance() {
		return Holder.instance;
	}

	@SuppressWarnings("deprecation")
	private void initSSLConnectionSocketFactory() throws Exception {

		SSLContext ctx = SSLContext.getInstance(protocol);

		X509TrustManager tm = new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain,
					String authType)
					throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType)
					throws CertificateException {
				if ((chain == null) || (chain.length == 0))
					throw new IllegalArgumentException(
							"null or zero-length certificate chain");
				if ((authType == null) || (authType.length() == 0))
					throw new IllegalArgumentException(
							"null or zero-length authentication type");

				boolean br = false;
				Principal principal = null;
				for (X509Certificate x509Certificate : chain) {
					principal = x509Certificate.getSubjectX500Principal();
					if (principal != null) {
						br = true;
						return;
					}
				}
				if (!(br))
					throw new CertificateException("服务端证书验证失败！");
			}

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

		};
		ctx.init(null, new TrustManager[] { tm }, new SecureRandom());

		// Allow TLSv1 protocol only
		sslsf = new SSLConnectionSocketFactory(ctx, new String[] { protocol },
				null, SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
		// client =
	}

	/**
	 * 
	 * @author chenhaiyan
	 * @return
	 */
	public CloseableHttpClient getSSLHttpClient() {
		return HttpClientBuilder.create().setSSLSocketFactory(sslsf).build();
	}

	/**
	 * 
	 * @author chenhaiyan
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public String getContent(String url) throws Exception {
		CloseableHttpClient httpclient = this.getSSLHttpClient();
		return HttpUtil.getContentWithCokkie(url, null, httpclient);
	}
}