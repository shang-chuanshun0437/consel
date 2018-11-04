package com.weiyi.lock.interceptor;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.request.BaseRequest;
import com.weiyi.lock.response.BaseResponse;
import com.weiyi.lock.service.api.RoleService;
import com.weiyi.lock.service.api.UserService;
import com.weiyi.lock.service.response.GetUserInfoResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

/*
*权限拦截器
 */
@Component
@Aspect
public class SecurityInterceptor
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RedisClient redisClient;

    //定义切入点
    @Pointcut("@annotation(com.weiyi.lock.interceptor.SecurityAnnotation)")
    public void securityPoint()
    {

    }

    //环绕通知
    @Around("securityPoint()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        // 接收到请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse httpServletResponse = attributes.getResponse();
        Cookie cookie = new Cookie("abc","dd");

        httpServletResponse.addCookie(cookie);

        // 获取拦截方法上的注解及注解值
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        SecurityAnnotation securityAnnotation = methodSignature.getMethod().getAnnotation(SecurityAnnotation.class);

        List<String> annotationValues = Arrays.asList(securityAnnotation.value());

        //只有登录的用户才可以访问该资源，判断用户是否登录
        //获取前端传入的参数
        Object[] args = proceedingJoinPoint.getArgs();
        BaseRequest baseRequest = (BaseRequest)args[0];

        //去redis缓存中校验token
        String redisToken = (String)redisClient.hget(baseRequest.getUserPhone() + "",Constant.User.TOKEN);
        if (redisToken != null && !redisToken.equals(baseRequest.getToken()))
        {
            return buildDeniedResponse(proceedingJoinPoint);
        }

        //如果redis中没有token，则从数据库中读取token
        if (redisToken == null)
        {
            GetUserInfoResponse user = userService.queryUserByPhone(baseRequest.getUserPhone());
            if (user == null || user.getUserToken() == null || !user.getUserToken().equals(baseRequest.getToken()))
            {
                return buildDeniedResponse(proceedingJoinPoint);
            }
            //将token存入redis
            redisClient.hset(baseRequest.getUserPhone() + "",Constant.User.TOKEN,user.getUserToken());
        }

        //注解的值为null，说明只要是登录的用户就可以访问
        if (annotationValues == null || annotationValues.size() == 0)
        {
            //调用该方法才会进入注解的方法
            return proceedingJoinPoint.proceed();
        }
        else
        {
            //从role角色数据库中获取该用户的角色
            String userRole = roleService.queryUserRoleByPhone(baseRequest.getUserPhone());

            if (StringUtils.isEmpty(userRole))
            {
                return buildDeniedResponse(proceedingJoinPoint);
            }
            String[] roles = userRole.split(";");
            //判断该用户是否具有该权限
            for (String role : roles)
            {
                if (annotationValues.contains(role))
                {
                    return proceedingJoinPoint.proceed();
                }
            }
        }

        return buildDeniedResponse(proceedingJoinPoint);
    }

    /*
    *构造请求中不满足权限的响应
    */
    private BaseResponse buildDeniedResponse(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        //获取返回值类型
        Signature signature = proceedingJoinPoint.getSignature();
        String className = signature.toLongString().split(" ")[1];
        Constructor constructor = Class.forName(className).getConstructor();

        BaseResponse response = (BaseResponse)constructor.newInstance();
        Result result = new Result();
        result.setRetCode(400);
        result.setRetMsg("access denied");
        response.setResult(result);

        return response;
    }
}
