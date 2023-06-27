# 数据库

## 准备数据库

创建用户：
```mysql
# create user ‘用户名’@‘IP地址’ identified by '密码'。
create user 'test'@'%' identified by '123456';
```
创建测试数据库：
```mysql
create database test_db;
```
将测试数据库的权限授予刚创建的用户：
```mysql
# 选择数据库
use test_db;
# 授权
grant all privileges on test_db to 'test'@'%';
# 刷新权限
flush privileges;
```

## 创建表
```mysql
use test_db;

CREATE TABLE `tb_product_info`
(
    `id`           int(11) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
    `company_id`   varchar(30)    DEFAULT NULL COMMENT '公司ID',
    `code`         varchar(11)    DEFAULT NULL COMMENT '商品编号',
    `product_name` varchar(200)   DEFAULT NULL COMMENT '商品名称',
    `price`        decimal(15, 2) DEFAULT NULL COMMENT '价格',
    `sku_type`     tinyint(4)     DEFAULT NULL COMMENT 'sku类型',
    `color_type`   tinyint(4)     DEFAULT NULL COMMENT '颜色类型',
    `create_time`  datetime       DEFAULT NULL COMMENT '创建时间',
    `create_date`  date           DEFAULT NULL COMMENT '创建日期',
    `stock`        bigint(20)     DEFAULT NULL COMMENT '库存',
    `status`       tinyint(4)     DEFAULT NULL COMMENT '状态',
    PRIMARY KEY (`id`),
    UNIQUE KEY `idx_code` (`code`) USING BTREE,
    UNIQUE KEY idx_sku_color (sku_type, color_type)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10
  CHARSET = utf8
  COMMENT ='商品信息';

show tables;
```

查看创建后的表信息：
```mysql
# 展示表状态
show table status;
# 显示表字段的详细信息
show FULL FIELDS from tb_product_info;
# 显示表的索引
show INDEX from tb_product_info;
```