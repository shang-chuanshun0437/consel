package com.weiyi.lock.dao.entity;

public class DeviceOut
{
    private Long deviceNum;

    private String deviceName;

    //设备的蓝牙mac
    private String bluetoothMac;

    //设备的管理员账号，具有为其他用户添加开锁的权限：首次绑定该设备的用户
    private Long ownerPhone;

    //设备用户量
    private Integer userCount;

    //设备版本号
    private String version;

    //设备版本号
    private Integer status;

    private String createTime;

    private String updateTime;

    public Long getDeviceNum() {
        return deviceNum;
    }

    public void setDeviceNum(Long deviceNum) {
        this.deviceNum = deviceNum;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getBluetoothMac() {
        return bluetoothMac;
    }

    public void setBluetoothMac(String bluetoothMac) {
        this.bluetoothMac = bluetoothMac;
    }

    public Long getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(Long ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
