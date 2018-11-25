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

	//请求的URL  禁止修改（国内）
	private static final String DOMESTIC_URL = "http://api.smsbao.com/sms";

	//请求的URL  禁止修改（国际）
	private static final String INTERNATIONAL_URL = " http://api.smsbao.com/wsms";

	//短信验证码的前缀，（国内）
	private static final String DOMESTIC_PREFIX = "【LF LTD】您的验证码为 ";

	//短信验证码的后缀，（国内）
	private static final String DOMESTIC_SUFFIX = "。此验证码15分钟内有效。提醒您：请勿将此验证码提供给其他人，以保障您的使用安全。";

	//临时密码（国内）
	private static final String DOMESTIC_TEMP_PASSWORD = "【LF.LTD】您的临时密码为9999，请用您的身份证号及临时密码登入会员中心,登入后请立即更改您的密码。温馨提示:请勿将此临时密码提供给其他人以保障您的使用安全。";

	//短信验证码的前缀，（港澳台）
	private static final String GAT_PREFIX = "您的驗證碼為 ";

	//短信验证码的后缀，（港澳台）
	private static final String GAT_SUFFIX = "。此驗證碼15分鐘內有效。提醒您：請勿將此驗證碼提供給其他人，以保障您的使用安全。";

	//临时密码（港澳台）
	private static final String GAT_TEMP_PASSWORD = "您的臨時密碼為9999，請用您的身份證號及臨時密碼登入會員中心,登入後請立即更改您的密碼。溫馨提示:請勿將此臨時密碼提供給其他人以保障您的使用安全。";

	//短信验证码的前缀，（国外）
	private static final String ABROAD_PREFIX = "【LF.LTD】Your verification code is ";

	//短信验证码的后缀，（国外）
	private static final String ABROAD_SUFFIX = ",This verification code is valid for 15 minutes. Remind you: Do not provide this verification code to others to keep your use safe.";

	//临时密码（国外）
	private static final String ABROAD_TEMP_PASSWORD = "【LF.LTD】Your temporary password is 9999. Please use your ID number and temporary password to log in to the member center. Please change your password immediately after login. Tips: Do not provide this temporary password to others to keep your use safe.";

	/*入参:
	 * @phone 手机号
	 *@msgCode 短信验证码 (当传入的消息类型为 1 时，此参数可传null)
	 *@msgType 消息类型： 0 短信验证码 ； 1 临时密码
	 * 出参：
	 * "0" 成功；否则失败
	 */
	public static String send(String phone,String msgCode,int msgType)
	{
		//请求的URL
		String URL = "";

		//手机号类型：0 大陆；1 港澳台；2 国外
		int phoneType = 0;
		if (phone.startsWith("+86")){
			phone = phone.substring(3,phone.length());
			phoneType = 0;
			URL = DOMESTIC_URL;
		}else if(phone.startsWith("+852") || phone.startsWith("+853") || phone.startsWith("+886")){
			phone=encodeUrlString(phone,"UTF-8");
			phoneType = 1;
			URL = INTERNATIONAL_URL;
		}else {
			phone=encodeUrlString(phone,"UTF-8");
			phoneType = 2;
			URL = INTERNATIONAL_URL;
		}

		//组装短信内容
		String content = "";

		if (msgType == 0)
		{
			if (phoneType == 0){
				content = DOMESTIC_PREFIX + msgCode + DOMESTIC_SUFFIX;
			}else if (phoneType == 1){
				content = GAT_PREFIX + msgCode + GAT_SUFFIX;
			}else if(phoneType == 2){
				content = ABROAD_PREFIX + msgCode + ABROAD_SUFFIX;
			}
		}else if (msgType == 1){
			if (phoneType == 0){
				content = DOMESTIC_TEMP_PASSWORD;
			}else if (phoneType == 1){
				content = GAT_TEMP_PASSWORD;
			}else if (phoneType == 2){
				content = ABROAD_TEMP_PASSWORD;
			}
		}
		//String content = TEMP_PASSWORD;
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
