
//短信验证码表
CREATE TABLE verify_code
(
    id int(11) auto_increment  comment "自增ID",
    user_phone BIGINT NOT NULL comment "发送短信的手机号",
    content VARCHAR(256) DEFAULT NULL comment "发送短信的内容",
    create_time datetime DEFAULT NULL comment "发送短信的时间,格式为2018-10-15 09:55:46",
    PRIMARY KEY (id),
    KEY(user_phone)
);
