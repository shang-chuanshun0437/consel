//接口访问统计表
CREATE TABLE interface_access
(
    id int(11) auto_increment  comment "自增ID",
    user_phone BIGINT NOT NULL comment "访问手机号",
    interface_name VARCHAR (256) DEFAULT NULL comment "访问的接口名称",
    url VARCHAR (256) DEFAULT NULL comment "访问的url名称",
    ip VARCHAR (48) DEFAULT NULL comment "访问的ip地址",
    status int(2) NOT NULL comment "访问状态，1 ：成功，2 失败",
    interface_count int(11) NOT NULL comment "访问此接口的次数",
    average_time int(11) NOT NULL comment "访问此接口消耗的平均时间，单位ms",
    consume_time int(11) NOT NULL comment "最近一次访问此接口所消耗的时间，单位为ms",
    remark VARCHAR(256) DEFAULT NULL comment "备注",
    create_time datetime comment "访问日期,格式为2018-10-15 09:55:46",
    PRIMARY KEY (id),
    KEY (interface_name),
    KEY (user_phone,interface_name)
);
