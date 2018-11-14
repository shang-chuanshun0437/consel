
CREATE database weiyi;

//入库设备表
CREATE TABLE device_in
(
    device_num BIGINT NOT NULL comment "设备编号",
    bluetooth_mac VARCHAR (24) NOT NULL comment "蓝牙mac",
    version VARCHAR(256) DEFAULT NULL comment "设备版本号",
    user_phone BIGINT NOT NULL comment "入库操作者手机号",
    user_name VARCHAR (16) DEFAULT NULL comment "入库操作者名称",
    flag int NOT NULL comment "0 未出库；1 已出库",
    create_time CHAR (14) DEFAULT NULL comment "设备入库时间,格式为20181015095546，表示2018-10-15 09:55:46",
    out_time CHAR (14) DEFAULT NULL comment "设备出库时间,格式为20181015095546，表示2018-10-15 09:55:46",
    PRIMARY KEY (device_num),
    KEY (out_time)
);
