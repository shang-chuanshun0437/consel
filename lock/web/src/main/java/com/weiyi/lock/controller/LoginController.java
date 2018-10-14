package com.weiyi.lock.controller;

import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.LoginRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class LoginController
{
    @RequestMapping(value = "/login",method = {RequestMethod.POST})
    @ResponseBody
    public void login(@RequestBody LoginRequest request)
    {

    }
}
