package com.weiyi.lock.common.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.util.ArrayList;
import java.util.List;

public class CopyProperties
{
    public static void copy(Object dest, Object orig)
    {
        try {
            PropertyUtils.copyProperties(dest,orig);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
