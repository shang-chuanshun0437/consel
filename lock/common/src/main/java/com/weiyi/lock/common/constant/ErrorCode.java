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

    //设备不存在或设备已存在管理员，则不允许绑定
    public static final int BIND_DEVICE_ERROR = 1007;

    //解绑设备失败
    public static final int UNBIND_DEVICE_ERROR = 1008;

    //管理员解绑解绑设备，设备下还存在其他用户，解绑失败
    public static final int OTHER_USERS_EXIST = 1009;

    //设备不存在
    public static final int DEVICE_NOT_EXIST = 1010;

}
