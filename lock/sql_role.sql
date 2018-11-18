//角色表
CREATE TABLE role
(
    id int(11) auto_increment  comment "自增ID",
    role_name VARCHAR (16) NOT NULL comment "角色名称",
    role_desc VARCHAR (16) DEFAULT NULL comment "角色描述",
    status int(2) NOT NULL comment "角色状态 1：正常 ； 2：禁用",
    create_time datetime comment "角色创建日期,格式为2018-10-15 09:55:46",
    PRIMARY KEY (id),
    UNIQUE KEY (role_name)
);
