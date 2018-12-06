package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.response.CheckVersionResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
* 版本升级
*/
@Controller
@RequestMapping("/system")
public class CheckVersionController
{
    @RequestMapping(value = "/checkVersion",method = {RequestMethod.POST})
    @ResponseBody
    public CheckVersionResponse checkVersion()
    {
        CheckVersionResponse checkVersionResponse = new CheckVersionResponse();

        Result result = new Result();

        checkVersionResponse.setResult(result);
        checkVersionResponse.setVersion("2019.01.03");

        return checkVersionResponse;
    }

}
