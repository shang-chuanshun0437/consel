package com.weiyi.lock.common.constant;

public class PermissionCode
{
    //游客，不登录也可以访问的资源
    public static final String VISITOR = "ROLE_VISITOR";

    //普通用户，登录的用户才能访问的资源
    public static final String USER = "ROLE_USER";

    //管理员，具有管理员权限的用户才能访问的资源
    public static final String ADMIN = "ROLE_ADMIN";

}
