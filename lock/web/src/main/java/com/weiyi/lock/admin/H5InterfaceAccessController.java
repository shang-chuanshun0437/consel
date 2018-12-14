package com.weiyi.lock.admin;

import com.weiyi.lock.common.Result;
import com.weiyi.lock.common.constant.Constant;
import com.weiyi.lock.common.utils.CopyProperties;
import com.weiyi.lock.dao.entity.DeviceOut;
import com.weiyi.lock.dao.entity.InterfaceAccess;
import com.weiyi.lock.dao.request.GetInterfaceAccessRequest;
import com.weiyi.lock.dao.request.QueryManageDeviceOutReq;
import com.weiyi.lock.interceptor.SecurityAnnotation;
import com.weiyi.lock.request.QueryInterfaceAccessRequest;
import com.weiyi.lock.response.QueryInterfaceAccessResponse;
import com.weiyi.lock.service.api.InterfaceAccessService;
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
@RequestMapping("/interface/access")
public class H5InterfaceAccessController
{
    private Logger logger = LoggerFactory.getLogger(H5InterfaceAccessController.class);

    @Autowired
    private InterfaceAccessService interfaceAccessService;

    /*
    *查询符合条件的设备列表,后台管理人员
    */
    @RequestMapping(value = "/queryList",method = {RequestMethod.POST})
    @ResponseBody
    @SecurityAnnotation(value = {"ADMIN"})
    public QueryInterfaceAccessResponse queryList(@RequestBody QueryInterfaceAccessRequest request)
    {
        QueryInterfaceAccessResponse response = new QueryInterfaceAccessResponse();
        Result result = new Result();
        response.setResult(result);

        if (logger.isDebugEnabled())
        {
            logger.debug("inter queryList() func ");
        }

        //填充查询数据
        if (request.getCurrentPage() == null || request.getCurrentPage().intValue() <= 0)
        {
            request.setCurrentPage(1);
        }

        GetInterfaceAccessRequest getInterfaceAccessRequest = new GetInterfaceAccessRequest();

        CopyProperties.copy(getInterfaceAccessRequest,request);
        getInterfaceAccessRequest.setCurrentPage((request.getCurrentPage() - 1) * Constant.PAGE_SIZE);
        
        //查询数量
        int total = interfaceAccessService.queryListCount(getInterfaceAccessRequest);
        response.setCount(total);

        List<InterfaceAccess> interfaceAccesses = interfaceAccessService.queryList(getInterfaceAccessRequest);

        if (interfaceAccesses != null && interfaceAccesses.size() > 0)
        {
            response.setInterfaceAccesses(interfaceAccesses.toArray(new InterfaceAccess[interfaceAccesses.size()]));
        }

        return response;
    }
}
