package com.wxxiaomi.myschool.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import android.util.Log;

import com.wxxiaomi.myschool.bean.webpage.request.NetReceiverData;
import com.wxxiaomi.myschool.bean.webpage.request.NetSendData;

public class MyHttpWebUtil {

	public static NetReceiverData sendGet(NetSendData sendData) {
//		Log.i("wang", "进入sendget方法中");
		NetReceiverData returnData = new NetReceiverData();
		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			url = new URL(sendData.getUrl());
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(20000);
			conn.setConnectTimeout(20000);
			conn.setRequestMethod("GET");
			if (sendData.getHeaders() != null
					&& !sendData.getHeaders().isEmpty()) {
				for (Map.Entry<String, String> entry : sendData.getHeaders()
						.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
//					Log.i("wang", "sendGet中header--" + entry.getKey() + ":"
//							+ entry.getValue());
				}
			}
			if (conn.getResponseCode() == 200) {
				Log.i("wang", "sendGet中conn.getResponseCode() == 200");
				processResponseHeader(conn,returnData.getHeaders(),sendData.getHeaders());
				
				is = conn.getInputStream();
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] bs = new byte[1024];
				int len = -1;
				while ((len = is.read(bs)) != -1) {
					bos.write(bs, 0, len);

				}
				byte b[] = bos.toByteArray();
				bos.close();
				returnData.setFromUrl(sendData.getUrl());
				returnData.setContent(b);
//				returnData.setFromUrl(sendData.getUrl());
				returnData.setStateCode(200);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
			}
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}

		return returnData;
	}

	public static NetReceiverData sendPost(NetSendData sendData) {
		Log.i("wang", "post的url="+sendData.getUrl());
		NetReceiverData returnData = new NetReceiverData();
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		HttpURLConnection conn = null;
		try {
			URL realUrl = new URL(sendData.getUrl());
			// 打开和URL之间的连接
			conn = (HttpURLConnection) realUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(8000);
			conn.setConnectTimeout(8000);
			conn.setInstanceFollowRedirects(false);
			if (sendData.getHeaders() != null
					&& !sendData.getHeaders().isEmpty()) {
				for (Map.Entry<String, String> entry : sendData.getHeaders()
						.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
					 Log.i("wang", "post数据的头部信息->" + entry.getKey() + ":"
					 + entry.getValue());
				}
			}
			if (sendData.getParmars() != null
					&& !sendData.getParmars().isEmpty()) {
				String params = "";
				int i = 0;
				for (Map.Entry<String, String> entry : sendData.getParmars()
						.entrySet()) {
					 Log.i("wang",
					 "post中的参数->"+entry.getKey()+":"+entry.getValue());
					if (i == 0) {
						params = entry.getKey() + "=" + entry.getValue();
					} else {
						params += "&" + entry.getKey() + "=" + entry.getValue();
					}
					i++;
				}
				// Log.i("wang", "post数据的参数信息->"+params);
				// 获取URLConnection对象对应的输出流
				out = new PrintWriter(conn.getOutputStream());
				// 发送请求参数
				out.print(params);
				// flush输出流的缓冲
				out.flush();
			}
			// }

			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "gb2312"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			 Log.i("wang", "sendPost中的result=" + result);
			if (conn.getResponseCode() == 200) {

				Log.i("wang", "conn.getResponseCode() == 200");
				processResponseHeader(conn, returnData.getHeaders(),
						sendData.getHeaders());
				returnData.setFromUrl(sendData.getUrl());
				returnData.setContent(result.getBytes("gb2312"));
				returnData.setStateCode(200);
			} else if (conn.getResponseCode() == 302) {
				Log.i("wang", "conn.getResponseCode() == 302");
				NetSendData Data302 = new NetSendData();
				processResponseHeader(conn, Data302.getHeaders(),
						sendData.getHeaders());
				Data302.getHeaders().put("Referer", sendData.getUrl());
				returnData.setFromUrl(sendData.getUrl());
				String temp = conn.getHeaderField("Location");
				Log.i("wang", "sendPost中302的目标是:" + sendData.getHost() + temp);
				Data302.setUrl(sendData.getHost() + temp);
				return sendGet(Data302);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			if (conn != null) {
				conn.disconnect();
			}

		}

		return returnData;
	}

	/**
	 * 处理服务器返回的conn中的头部包含Set-Cookie的情况
	 * 要取出并把它封装到returnData的header中，即inMap
	 * @param http
	 * @param returnDataHeader
	 * @param sendDataHeader
	 * @throws UnsupportedEncodingException
	 */
	private static void processResponseHeader(HttpURLConnection http,
			Map<String, String> returnDataHeader, Map<String, String> sendDataHeader)
			throws UnsupportedEncodingException {
		if(sendDataHeader.containsKey("Cookie")){
			returnDataHeader.put("Cookie", sendDataHeader.get("Cookie"));
		}
		Map<String, String> header = getHttpResponseHeader(http);
		for (Map.Entry<String, String> entry : header.entrySet()) {
			String key = entry.getKey() != null ? entry.getKey() : "";
			if (key.equals("Set-Cookie")) {
				returnDataHeader.put("Cookie", returnDataHeader.get("Cookie") == null ? entry.getValue()
						: returnDataHeader.get("Cookie") + ";" + entry.getValue() + ";");
			}
		}
	}

	private static Map<String, String> getHttpResponseHeader(
			HttpURLConnection http) throws UnsupportedEncodingException {
		Map<String, String> header = new LinkedHashMap<String, String>();
		for (int i = 0;; i++) {
			String mine = http.getHeaderField(i);
			if (mine == null)
				break;
			header.put(http.getHeaderFieldKey(i), mine);
			Log.i("wang", "返回的头部"+i+":"+http.getHeaderFieldKey(i)+":"+ mine);
		}
		return header;
	}
}
