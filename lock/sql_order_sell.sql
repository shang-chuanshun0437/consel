//订单编号
CREATE TABLE order_sell
(
    order_id VARCHAR (125) NOT NULL comment "订单编号",
    device_num BIGINT NOT NULL comment "设备编号",
    buyer_phone BIGINT NOT NULL comment "买家手机号",
    buyer_name VARCHAR (24) NOT NULL comment "买家名称",
    buyer_address VARCHAR (256) NOT NULL comment "收货地址",
    express_name VARCHAR (32) NOT NULL comment "快递公司名称",
    express_id VARCHAR (32) NOT NULL comment "快递单号",
    status int  DEFAULT 1 comment "订单状态 1 未发货； 2 已发货 ；3 已收货;4 退换货；5 报修订单",
    create_time datetime comment "订单创建日期,格式为2018-10-15 09:55:46",
    update_time datetime DEFAULT CURRENT_TIMESTAMP comment "订单更新日期,格式为20181015095546，表示2018-10-15 09:55:46",
    remark VARCHAR (128) NOT NULL comment "备注",
    PRIMARY KEY (express_id),
    KEY(status) ,
    KEY (buyer_phone)
);
