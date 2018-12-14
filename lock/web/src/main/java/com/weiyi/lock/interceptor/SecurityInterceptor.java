package com.weiyi.lock.interceptor;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.redis.RedisClient;
import com.weiyi.lock.common.utils.TimeUtil;
import com.weiyi.lock.dao.entity.InterfaceAccess;
import com.weiyi.lock.dao.entity.User;
import com.weiyi.lock.request.BaseRequest;
import com.weiyi.lock.response.BaseResponse;
import com.weiyi.lock.service.api.InterfaceAccessService;
import com.weiyi.lock.service.api.RoleUserService;
import com.weiyi.lock.service.api.UserService;
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
    private RoleUserService roleUserService;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private InterfaceAccessService interfaceAccessService;

    //定义切入点
    @Pointcut("@annotation(com.weiyi.lock.interceptor.SecurityAnnotation)")
    public void securityPoint()
    {

    }

    //环绕通知
    @Around("securityPoint()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        Long startTime = System.currentTimeMillis();
        // 接收到请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();

        String ip = getIpAddress(request);
        String url = request.getRequestURI();

        HttpServletResponse httpServletResponse = attributes.getResponse();
        Cookie cookie = new Cookie("abc","dd");

        httpServletResponse.addCookie(cookie);

        // 获取拦截方法上的注解及注解值
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();

        SecurityAnnotation securityAnnotation = methodSignature.getMethod().getAnnotation(SecurityAnnotation.class);

        String method = methodSignature.getMethod().toGenericString().split(" ")[2];
        String interfaceName = method.split("\\(")[0];

        List<String> annotationValues = Arrays.asList(securityAnnotation.value());

        //只有登录的用户才可以访问该资源，判断用户是否登录
        //获取前端传入的参数
        Object[] args = proceedingJoinPoint.getArgs();
        BaseRequest baseRequest = (BaseRequest)args[0];

        InterfaceAccess interfaceAccess = interfaceAccessService.queryInterfaceAccess(baseRequest.getUserPhone(),interfaceName);

        if (interfaceAccess == null)
        {
            interfaceAccess = new InterfaceAccess();
            interfaceAccess.setAverageTime(new Long(0));
            interfaceAccess.setInterfaceCount(new Long(0));
        }
        interfaceAccess.setUserPhone(baseRequest.getUserPhone());
        interfaceAccess.setInterfaceName(interfaceName);
        interfaceAccess.setStatus(Constant.SUCCESS);
        interfaceAccess.setRemark("SUCCESS");
        interfaceAccess.setCreateTime(TimeUtil.getCurrentTime());
        interfaceAccess.setIp(ip);
        interfaceAccess.setUrl(url);
        //去redis缓存中校验token
        String redisToken = (String)redisClient.hget(baseRequest.getUserPhone() + "",Constant.User.TOKEN);
        if (redisToken != null && !redisToken.equals(baseRequest.getToken()))
        {
            return buildDeniedResponse(proceedingJoinPoint,interfaceAccess,startTime,"token is error");
        }

        //如果redis中没有token，则从数据库中读取token
        if (redisToken == null)
        {
            User user = userService.queryUserByPhone(baseRequest.getUserPhone());
            if (user == null || user.getUserToken() == null || !user.getUserToken().equals(baseRequest.getToken()))
            {
                return buildDeniedResponse(proceedingJoinPoint,interfaceAccess,startTime,"token is error");
            }
            //将token存入redis
            redisClient.hset(baseRequest.getUserPhone() + "",Constant.User.TOKEN,user.getUserToken());
        }

        //注解的值为null，说明只要是登录的用户就可以访问
        if (annotationValues == null || annotationValues.size() == 0)
        {
            //调用该方法才会进入注解的方法
            Object object = proceedingJoinPoint.proceed();

            //数据库中是否存在这条记录，有 则先删除后插入
            if (interfaceAccess.getId() != null)
            {
                interfaceAccessService.deleteRecord(interfaceAccess.getId());
            }
            interfaceAccess.setConsumeTime(System.currentTimeMillis() - startTime);
            Long oldTotal = interfaceAccess.getInterfaceCount() * interfaceAccess.getAverageTime();
            interfaceAccess.setInterfaceCount(interfaceAccess.getInterfaceCount() + 1);
            interfaceAccess.setAverageTime((oldTotal + interfaceAccess.getConsumeTime()) / interfaceAccess.getInterfaceCount());
            interfaceAccessService.addRecord(interfaceAccess);
            return object;
        }
        else
        {
            //从role角色数据库中获取该用户的角色
            String userRole = roleUserService.queryUserRoleByPhone(baseRequest.getUserPhone());

            if (StringUtils.isEmpty(userRole))
            {
                return buildDeniedResponse(proceedingJoinPoint,interfaceAccess,startTime,"access denied");
            }
            String[] roles = userRole.split(";");
            //判断该用户是否具有该权限
            for (String role : roles)
            {
                if (annotationValues.contains(role))
                {
                    //调用该方法才会进入注解的方法
                    Object object = proceedingJoinPoint.proceed();

                    interfaceAccess.setConsumeTime(System.currentTimeMillis() - startTime);
                    //数据库中是否存在这条记录，有 则先删除后插入
                    if (interfaceAccess.getId() != null)
                    {
                        interfaceAccessService.deleteRecord(interfaceAccess.getId());
                    }
                    Long oldTotal = interfaceAccess.getInterfaceCount() * interfaceAccess.getAverageTime();
                    interfaceAccess.setInterfaceCount(interfaceAccess.getInterfaceCount() + 1);
                    interfaceAccess.setAverageTime((oldTotal + interfaceAccess.getConsumeTime()) / interfaceAccess.getInterfaceCount());
                    interfaceAccessService.addRecord(interfaceAccess);
                    return object;
                }
            }
        }

        return buildDeniedResponse(proceedingJoinPoint,interfaceAccess,startTime,"unknown error");
    }

    /*
    *构造请求中不满足权限的响应
    */
    private BaseResponse buildDeniedResponse(ProceedingJoinPoint proceedingJoinPoint,InterfaceAccess interfaceAccess,Long startTime,String remark) throws Throwable
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

        interfaceAccess.setStatus(Constant.FAIL);
        interfaceAccess.setConsumeTime(System.currentTimeMillis() - startTime);
        interfaceAccess.setRemark(remark);

        //数据库中是否存在这条记录，有 则先删除后插入
        if (interfaceAccess.getId() != null)
        {
            interfaceAccessService.deleteRecord(interfaceAccess.getId());
        }
        Long oldTotal = interfaceAccess.getInterfaceCount() * interfaceAccess.getAverageTime();
        interfaceAccess.setInterfaceCount(interfaceAccess.getInterfaceCount() + 1);
        interfaceAccess.setAverageTime((oldTotal + interfaceAccess.getConsumeTime()) / interfaceAccess.getInterfaceCount());
        interfaceAccessService.addRecord(interfaceAccess);

        return response;
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
