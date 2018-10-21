
CREATE database weiyi;

//设备表
CREATE TABLE device
(
    device_num BIGINT NOT NULL comment "设备编号",
    device_name VARCHAR (16) DEFAULT NULL comment "设备名称",
    bluetooth_mac VARCHAR (24) NOT NULL comment "蓝牙mac",
    owner_phone BIGINT DEFAULT NULL comment "设备的管理员账号，具有为其他用户添加开锁的权限",
    version VARCHAR(256) DEFAULT NULL comment "设备版本号",
    create_time CHAR (14) comment "设备添加时间,格式为20181015095546，表示2018-10-15 09:55:46",
    update_time CHAR (14) comment "设备更新时间时间,格式为20181015095546，表示2018-10-15 09:55:46",
    PRIMARY KEY (device_num),
    KEY (owner_phone),
    KEY (version)
);
