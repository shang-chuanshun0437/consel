package com.weiyi.lock.common.constant;

public class PermissionCode
{
    //普通用户，登录的用户才能访问的资源
    public static final String USER = "ROLE_USER";

    //管理员，具有管理员权限的用户才能访问的资源
    public static final String ADMIN = "ROLE_ADMIN";

    //操作设备的权限
    public static final String DEVICE = "ROLE_DEVICE";

    //添加用户角色的权限
    public static final String ADD_ROLE = "ROLE_ADD_ROLE";
}
