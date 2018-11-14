package com.weiyi.lock.request;

public class ModifyOrderSellRequest extends BaseRequest
{
    private Long deviceNum;

    private String orderId;

    private Long buyerPhone;

    private String expressName;

    private String expressId;

    private Integer status;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(Long buyerPhone) {
        this.buyerPhone = buyerPhone;
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
}
