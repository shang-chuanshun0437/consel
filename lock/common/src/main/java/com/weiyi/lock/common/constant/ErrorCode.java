package com.weiyi.lock.common.constant;

public class ErrorCode
{
    //验证码不正确
    public static final int VERIFY_ERROR = 1000;

    //用户已存在
    public static final int USER_EXIST = 1001;

    //登录时，用户不存在
    public static final int USER_NOT_EXIST = 1002;

    //登录时，密码错误
    public static final int PASSWORD_ERROR = 1003;

    //用户角色已存在
    public static final int ROLE_USER_EXIST = 1004;

    //设备已存在
    public static final int DEVICE_EXIST = 1005;

    //设备已存在管理员，不允许后台维护人员修改
    public static final int OWNER_USER_EXIST = 1006;

}
