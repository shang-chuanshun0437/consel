
//用户绑定的设备表
CREATE TABLE user_device
(
    id int(11) auto_increment  comment "自增ID",
    user_phone BIGINT NOT NULL comment "设备用户",
    device_num BIGINT NOT NULL comment "用户绑定的设备编号",
    expiry_date datetime comment "设备设备有效期,格式为2018-10-15 09:55:46",
    create_time datetime comment "设备添加时间,格式为2018-10-15 09:55:46",
    update_time datetime DEFAULT CURRENT_TIMESTAMP  comment "设备更新时间时间,格式2018-10-15 09:55:46",
    PRIMARY KEY (id),
    unique index(device_num,user_phone)
);
