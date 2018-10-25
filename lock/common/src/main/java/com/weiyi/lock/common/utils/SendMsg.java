package com.weiyi.lock.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SendMsg
{
	//账号  禁止修改
	private static final String USER_NAME = "mlboydotcom";

	//密码  禁止修改
	private static final String PASSWORD = "20000522";

	//请求的URL  禁止修改
	private static final String URL = "http://api.smsbao.com/sms";

	//短信验证码的前缀，可修改
	private static final String PREFIX = "【LF LTD】您的验证码为 ";

	//短信验证码的后缀，可修改
	private static final String SUFFIX = "。此验证码15分钟内有效。提醒您：请勿将此验证码提供给其他人，以保障您的使用安全。";

	/*入参:
	 * @phone 手机号
	 *@msgCode 短信验证码
	 *
	 * 出参：
	 * "0" 成功；否则失败
	 */
	public static String send(String phone,String msgCode)
	{
		//组装短信验证码
		String content = PREFIX + msgCode + SUFFIX;

		//组装请求参数
		StringBuffer httpArg = new StringBuffer();
		httpArg.append("u=").append(USER_NAME).append("&");
		httpArg.append("p=").append(md5(PASSWORD)).append("&");
		httpArg.append("m=").append(phone).append("&");
		httpArg.append("c=").append(encodeUrlString(content, "UTF-8"));

		String result = request(URL, httpArg.toString());

		return result;
	}

	//发送http请求
	private static String request(String httpUrl, String httpArg) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		httpUrl = httpUrl + "?" + httpArg;

		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = reader.readLine();
			if (strRead != null) {
				sbf.append(strRead);
				while ((strRead = reader.readLine()) != null) {
					sbf.append("\n");
					sbf.append(strRead);
				}
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	//对传输密码，进行md5加密
	private static String md5(String plainText) {
		StringBuffer buf = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return buf.toString();
	}

	private static String encodeUrlString(String str, String charset) {
		String strRet = null;
		if (str == null)
			return str;
		try {
			strRet = java.net.URLEncoder.encode(str, charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return strRet;
	}
}
