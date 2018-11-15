package com.weiyi.lock.common.constant;

public class Constant
{
    public static class User
    {
        //验证码前缀
        public static final String VERIFY_CODE = ".verify_code";

        //key : token
        public static final String TOKEN = "token";
    }

    //分页查询，每页的数量
    public static final int PAGE_SIZE = 10;

    //启用状态
    public static final int ENABLE = 0;

    //禁用状态
    public static final int DISABLE = 0;

    //成功
    public static final int SUCCESS = 1;

    //失败
    public static final int FAIL = 2;
}
