package com.weiyi.lock.controller;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.AddRoleRequest;
import com.weiyi.lock.response.AddRoleResponse;
import com.weiyi.lock.service.api.RoleService;
import com.weiyi.lock.service.response.GetRoleInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role")
public class RoleController
{
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/addRole",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {PermissionCode.ADD_ROLE})
    public AddRoleResponse addRole(@RequestBody AddRoleRequest request)
    {
        AddRoleResponse response = new AddRoleResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addDevice() func ,the request:{}",request);
        }

        //首先判断添加的用户是已经存在,存在则报错
        GetRoleInfoResponse dbRole = roleService.queryRoleByPhone(request.getAddPhone());
        if(dbRole == null)
        {
            GetRoleInfoResponse getRoleInfoResponse = new GetRoleInfoResponse();

            getRoleInfoResponse.setUserPhone(request.getAddPhone());
            getRoleInfoResponse.setUserName(request.getAddName());
            getRoleInfoResponse.setUserRole(request.getUserRole());
            getRoleInfoResponse.setCreateTime(TimeUtil.getCurrentTime());
            getRoleInfoResponse.setUpdateTime(getRoleInfoResponse.getCreateTime());

            roleService.addRole(getRoleInfoResponse);
        }
        else
        {
            result.setRetCode(ErrorCode.ROLE_USER_EXIST);
            result.setRetMsg("role user exist.");
        }

        return response;
    }
}
