
CREATE TABLE users
(
    user_phone BIGINT NOT NULL comment "用户手机号",
    user_name VARCHAR (16) DEFAULT NULL comment "用户名称",
    user_password VARCHAR(24) NOT NULL comment "用户密码",
    user_token VARCHAR(256) DEFAULT NULL comment "用户token",
    user_email VARCHAR(256) DEFAULT NULL comment "用户邮箱",
    user_address VARCHAR(256) DEFAULT NULL comment "常用地址",
    create_time VARCHAR(24) comment "用户创 建日期,格式为20181015095546，表示2018-10-15 09:55:46",

    PRIMARY KEY (user_phone)
);
