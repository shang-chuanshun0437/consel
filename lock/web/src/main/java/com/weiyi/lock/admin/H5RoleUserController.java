package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.RoleUser;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.AddRoleUserRequest;
import com.weiyi.lock.response.AddRoleUserResponse;
import com.weiyi.lock.service.api.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/role/user")
public class H5RoleUserController
{
    private Logger logger = LoggerFactory.getLogger(H5RoleUserController.class);

    @Autowired
    private RoleUserService roleUserService;

    @RequestMapping(value = "/addRole",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public AddRoleUserResponse addRoleUser(@RequestBody AddRoleUserRequest request)
    {
        AddRoleUserResponse response = new AddRoleUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addDevice() func ,the request:{}",request);
        }

        //首先判断添加的用户是已经存在,存在则报错
        RoleUser dbRole = roleUserService.queryRoleByPhone(request.getAddPhone());
        if(dbRole == null)
        {
            RoleUser roleUser = new RoleUser();

            roleUser.setUserPhone(request.getAddPhone());
            roleUser.setUserName(request.getAddName());
            roleUser.setUserRole(request.getUserRole());
            roleUser.setCreateTime(TimeUtil.getCurrentTime());
            roleUser.setUpdateTime(roleUser.getCreateTime());

            roleUserService.addRole(roleUser);
        }
        else
        {
            result.setRetCode(ErrorCode.ROLE_USER_EXIST);
            result.setRetMsg("role user exist.");
        }

        return response;
    }
}
