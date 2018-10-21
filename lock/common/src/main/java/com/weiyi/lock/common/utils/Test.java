package com.weiyi.lock.common.utils;

import com.weiyi.lock.common.utils.AliDayunSms;
import org.springframework.util.StringUtils;

public class Test
{
    public static void main(String[] args)
    {
        String signName = "欧亚总代理";
        String templateCode = "SMS_148591459";
        String phoneNum = "18753137390";
        String msgCode = "789456";

        String ret = AliDayunSms.sendMsg(signName,templateCode,phoneNum,msgCode);
        if (ret != null && ret.equals("OK"))
        {
            //短信发送成功
            //TODO 做你们自己的业务逻辑处理
        }
        else
        {
            //短信发送失败
            //TODO 做你们自己的业务逻辑处理
            System.out.println(ret);
        }

    }
}
