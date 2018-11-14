package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.dao.request.QueryAllUserListReq;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.QueryAllUserRequest;
import com.weiyi.lock.response.QueryAllUserResponse;
import com.weiyi.lock.service.api.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

/*
* 用户修改操作控制器
*/
@Controller
@RequestMapping("/user")
public class H5UserController
{
    private Logger logger = LoggerFactory.getLogger(H5UserController.class);

    @Autowired
    private UserService userService;

    /*
     * 后台管理人员获取注册用户
     */
    @RequestMapping(value = "/query/allUser",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation()
    public QueryAllUserResponse queryAllUser(@RequestBody @Valid QueryAllUserRequest request)
    {
        QueryAllUserResponse response = new QueryAllUserResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryAllUser() func ,the user is:{}", request.getUserPhone());
        }
        QueryAllUserListReq queryAllUserListReq = new QueryAllUserListReq();

        CopyProperties.copy(queryAllUserListReq,request);
        queryAllUserListReq.setUserPhone(request.getNeedPhone());

        int total = userService.queryAllUserCount(queryAllUserListReq);
        response.setCount(total);

        if(total <= 0)
        {
            return response;
        }

        List<User> users = userService.queryAllUser(queryAllUserListReq);
        response.setUsers(users.toArray(new User[users.size()]));

        return response;
    }
}
