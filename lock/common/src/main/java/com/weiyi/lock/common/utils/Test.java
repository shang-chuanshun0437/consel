package com.weiyi.lock.common.utils;

import com.weiyi.lock.common.utils.AliDayunSms;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

public class Test
{
    public static void main(String[] args)
    {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String time = f.format("2009-01-03 00:00:00");

        String phone = "+8618753137390";
        String msgCode = "1234";

        String result = SendMsg.send(phone,msgCode,0);
        System.out.println(result);
        if (result != null && result.equals("0"))
        {
            //短信发送成功
            //TODO 做贵公司自己的业务逻辑处理
            System.out.println(result);
        }
        else
        {
            //短信发送失败
            //TODO 做贵公司自己的业务逻辑处理
            System.out.println(result);
        }

    }
}
