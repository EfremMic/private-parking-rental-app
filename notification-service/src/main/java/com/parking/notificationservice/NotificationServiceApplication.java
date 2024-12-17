package com.parking.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.net.ssl.*;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		disableSslVerification();
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	private static void disableSslVerification() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, new TrustManager[]{new X509TrustManager() {
				public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {}
				public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {}
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return new java.security.cert.X509Certificate[0];
				}
			}}, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
