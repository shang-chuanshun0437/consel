//开门历史记录表
CREATE TABLE open_history
(
    id int(11) auto_increment  comment "自增ID",
    user_phone BIGINT NOT NULL comment "用户手机号",
    user_name VARCHAR (24) comment "用户名称",
    device_num BIGINT NOT NULL comment "设备编号",
    device_name VARCHAR (36) comment "设备名称",
    open_time CHAR (14) comment "开门时间,格式为20181015095546，表示2018-10-15 09:55:46",
    PRIMARY KEY (id),
);
