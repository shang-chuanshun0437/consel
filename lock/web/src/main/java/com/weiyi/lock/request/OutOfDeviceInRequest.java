package com.weiyi.lock.request;

public class OutOfDeviceInRequest extends BaseRequest
{
    private String orderId;

    private Long deviceNum;

    private Long buyerPhone;

    private String buyerName;

    private String buyerAddress;

    private String expressName;

    private String expressId;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
