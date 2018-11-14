package com.weiyi.lock.dao.entity;

//售出设备订单表
public class OrderSell
{
    private String orderId;

    private Long deviceNum;

    private Long buyerPhone;

    private String buyerName;

    private String buyerAddress;

    private String expressName;

    private String expressId;

    private Integer status;

    private String createTime;

    private String updateTime;

    private String remark;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public Long getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(Long buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getExpressName() {
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
