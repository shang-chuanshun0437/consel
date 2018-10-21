package com.weiyi.lock.common.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.util.Random;

/**
 * 短信接口
 */
public class AliDayunSms
{
    //设置连接超时
    private static final String CONNECT_TIME_OUT = "sun.net.client.defaultConnectTimeout";

    //设置读取超时
    private static final String READ_TIME_OUT = "sun.net.client.defaultReadTimeout";

    private static final String PRODUCT = "Dysmsapi";
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";

    private static final String ACCESS_KEY_ID = "LTAIx7uoDy0QLp8A";
    private static  final String ACCESS_KEY_SECRET = "RRnOxIGefGOidXlJvAktIRamcHRdg3";

    /*入参:
    * @signName 短信签名  可在短信控制台中找到
    *@templateCode 短信模板-可在短信控制台中找到
    * @userPhone 手机号
    *@msgCode 短信验证码
    *
    * 出参：
    * 1、null   出现了异常
    * 2、"OK"   表示发送成功
    * 3、"NUMBER_ILLEGAL" 手机号非法
    * 4、"AMOUNT_NOT_ENOUGH" 账户余额不足
    * 5、"OTHER_ERROR" 其它错误
    */
    public static String sendMsg(String signName,String templateCode,String phoneNum,String msgCode)
    {
        System.setProperty(CONNECT_TIME_OUT, "10000");
        System.setProperty(READ_TIME_OUT, "10000");

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        try
        {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);

            IAcsClient acsClient = new DefaultAcsClient(profile);

            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);

            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码
            // 发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
            request.setPhoneNumbers(phoneNum);

            request.setSignName(signName);

            //必填:短信模板-可在短信控制台中找到，发送国际/港澳台消息时，请使用国际/港澳台短信模版
            request.setTemplateCode(templateCode);
            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n
            request.setTemplateParam("{\"code\":\"" + msgCode + "\"}");

            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            String retCode = sendSmsResponse.getCode();
            //发送成功
            if (retCode.equals("OK"))
            {
                return "OK";
            }
            else if(retCode.equals("isv.AMOUNT_NOT_ENOUGH"))
            {
                return "AMOUNT_NOT_ENOUGH";
            }
            else if(retCode.equals("isv.MOBILE_NUMBER_ILLEGAL"))
            {
                return "NUMBER_ILLEGAL";
            }
            else
            {
                return "OTHER_ERROR";
            }
        }
        catch (ClientException e)
        {
            return null;
        }
    }


}