package com.weiyi.lock.dao.entity;

import com.weiyi.lock.common.utils.TimeUtil;

public class InterfaceAccess
{
    private Integer id;

    //访问账号
    private Long userPhone;

    //访问的接口名称
    private String interfaceName;

    //访问的URL
    private String url;

    //用户IP
    private String ip;

    //访问状态，1 成功  2  失败
    private Integer status;

    //访问此接口的次数
    private Long interfaceCount;

    //访问此接口的平均时长
    private Long averageTime;

    //最近一次访问此接口消耗的时间，单位ms
    private Long consumeTime;

    //访问时间
    private String createTime;

    //备注
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Long userPhone) {
        this.userPhone = userPhone;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getConsumeTime() {
        return consumeTime;
    }

    public void setConsumeTime(Long consumeTime) {
        this.consumeTime = consumeTime;
    }

    public String getCreateTime() {
        return TimeUtil.getDateTime(createTime);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getInterfaceCount() {
        return interfaceCount;
    }

    public void setInterfaceCount(Long interfaceCount) {
        this.interfaceCount = interfaceCount;
    }

    public Long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Long averageTime) {
        this.averageTime = averageTime;
    }
}
