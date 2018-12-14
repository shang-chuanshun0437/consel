package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.constant.ErrorCode;
import com.weiyi.lock.common.constant.PermissionCode;
import com.weiyi.lock.common.exception.LockException;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.RoleUser;
import com.weiyi.lock.dao.request.GetRoleUserRequest;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.AddRoleUserRequest;
import com.weiyi.lock.request.DeleteRoleUserRequest;
import com.weiyi.lock.request.QueryRoleUserRequest;
import com.weiyi.lock.response.AddRoleUserResponse;
import com.weiyi.lock.response.DeleteRoleUserResponse;
import com.weiyi.lock.response.QueryRoleUserResponse;
import com.weiyi.lock.service.api.RoleUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role/user")
public class H5RoleUserController
{
    private Logger logger = LoggerFactory.getLogger(H5RoleUserController.class);

    @Autowired
    private RoleUserService roleUserService;

    @RequestMapping(value = "/addRole",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public AddRoleUserResponse addRoleUser(@RequestBody AddRoleUserRequest request)
    {
        AddRoleUserResponse response = new AddRoleUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter addDevice() func ,the request:{}",request);
        }

        if (request.getUserRole() == null || request.getUserRole().length <= 0){
            result.setRetCode(ErrorCode.PARAMETER_ERROR);
            result.setRetMsg("user role can not be null.");
        }
        StringBuilder role = new StringBuilder();
        for (String tmp : request.getUserRole()){
            role.append(tmp).append(";");
        }
        RoleUser roleUser = new RoleUser();

        roleUser.setUserPhone(request.getAddPhone());
        roleUser.setUserName(request.getAddName());
        roleUser.setRoleName(role.toString());
        roleUser.setCreateTime(TimeUtil.getCurrentTime());

        try {
            roleUserService.addRole(roleUser);
        }catch (LockException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }
        return response;
    }

    @RequestMapping(value = "/queryRoleUser",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public QueryRoleUserResponse queryRoleUser(@RequestBody QueryRoleUserRequest request)
    {
        QueryRoleUserResponse response = new QueryRoleUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryRoleUser() func ,the request:{}",request);
        }

        GetRoleUserRequest getRoleUserRequest = new GetRoleUserRequest();

        if(request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0){
            request.setCurrentPage(1);
        }

        getRoleUserRequest.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        getRoleUserRequest.setUserPhone(request.getNeedPhone());

        try {
            int total = roleUserService.queryRoleCount(getRoleUserRequest);
            response.setCount(total);

            List<RoleUser> roleUsers = roleUserService.queryRole(getRoleUserRequest);
            if(roleUsers != null && roleUsers.size() > 0){
                response.setRoleUsers(roleUsers.toArray(new RoleUser[roleUsers.size()]));
            }
        }catch (LockException e){
            result.setRetCode(e.getCode());
            result.setRetMsg(e.getMsg());
        }
        return response;
    }

    /*
    *删除管理员
    **/
    @RequestMapping(value = "/deleteRoleUser",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public DeleteRoleUserResponse deleteRoleUser(@RequestBody DeleteRoleUserRequest request)
    {
        DeleteRoleUserResponse response = new DeleteRoleUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryRoleUser() func ,the request:{}",request);
        }

        roleUserService.deleteRoleUserByPhone(request.getDeletePhone());
        return response;
    }
}
