package com.weiyi.lock.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil
{
    //返回年月日时分秒字符串，例如20181015095546，表示2018-10-15 09:55:46
    public static String getCurrentTime()
    {
        Date now = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmmss");
        String time = f.format(now);

        return time;
    }
}
