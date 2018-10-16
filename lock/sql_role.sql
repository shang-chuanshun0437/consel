
CREATE TABLE role
(
    user_phone BIGINT NOT NULL comment "用户手机号",
    user_name VARCHAR (16) DEFAULT NULL comment "用户名称",
    user_role VARCHAR(256) NOT NULL comment "用户角色",
    create_time VARCHAR(24) comment "用户角色创建日期,格式为20181015095546，表示2018-10-15 09:55:46",
    update_time VARCHAR(24) comment "用户角色创建日期,格式为20181015095546，表示2018-10-15 09:55:46",
    PRIMARY KEY (user_phone)
);
