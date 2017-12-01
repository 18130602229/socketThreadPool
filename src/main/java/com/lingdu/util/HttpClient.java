package com.lingdu.util;


import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
/**
 * 线程的具体实现
 * @author Administrator
 *
 */
public class HttpClient {

	public static final Logger LOG = Logger.getLogger(HttpClient.class);

	public String request(String url, String entity, String soapAction) {
		HttpEntity re = null;
		HttpPost httppost = new HttpPost(url);
		CloseableHttpResponse response = null;
		try {
			httppost.setHeader(
					"Content-Type",
					"application/soap+xml;charset=UTF-8;action=\"http://WebXml.com.cn/getStationName");
			httppost.setHeader("SOAPAction", soapAction);
			re = new StringEntity(entity, HTTP.UTF_8);
			httppost.setEntity(re);
			response = HttpClientManagerFactory.getHttpClient().execute(httppost);
			if (response != null
					&& response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			LOG.info("call webservice error------->" + e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
