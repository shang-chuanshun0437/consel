
CREATE database weiyi;

//出库设备表
CREATE TABLE device_out
(
    device_num BIGINT NOT NULL comment "设备编号",
    device_name VARCHAR (16) DEFAULT NULL comment "设备名称",
    bluetooth_mac VARCHAR (24) NOT NULL comment "蓝牙mac",
    owner_phone BIGINT DEFAULT NULL comment "设备的管理员账号，具有为其他用户添加开锁的权限",
    user_count int(11)  DEFAULT 0 comment "设备用户量",
    version VARCHAR(256) DEFAULT NULL comment "设备版本号",
    status int DEFAULT 0 comment "设备状态 0 正常使用；1 禁用",
    create_time datetime comment "设备添加时间,格式为2018-10-15 09:55:46",
    update_time datetime DEFAULT CURRENT_TIMESTAMP comment "设备更新时间时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (device_num),
    KEY (owner_phone),
    KEY (version)
);
