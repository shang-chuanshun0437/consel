
CREATE database weiyi;

CREATE TABLE device
(
    device_num int(11) NOT NULL comment "设备编号",
    device_name VARCHAR(16) comment "设备名称",

    PRIMARY KEY (device_num)
)
