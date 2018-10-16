package com.weiyi.lock.common.utils;

public class LockAssert
{
    public static void isTrue(boolean expression, int code,String msg) {
        if (!expression) {
            throw new LockException(code,msg);
        }
    }

    public static void isTrue(boolean expression, int code) {
        if (!expression) {
            throw new LockException(code);
        }
    }
}
