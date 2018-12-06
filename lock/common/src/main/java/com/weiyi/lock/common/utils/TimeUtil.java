package com.weiyi.lock.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil
{
    //返回年月日时分秒字符串
    public static String getCurrentTime()
    {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String time = f.format(now);

        return time;
    }

    //去掉后面的.0
    public static String getDateTime(String timestamp)
    {
        if(timestamp == null)
        {
            return null;
        }
        return timestamp.substring(0,timestamp.length() - 2);
    }

    //格式化输入日期
    public static String formatTime(String date)
    {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        String time = f.format(date);

        return time;
    }
}
