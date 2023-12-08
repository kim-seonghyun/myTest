use nhn_academy_6;


CREATE TABLE `users`
(
    `user_id`         varchar(50)  NOT NULL COMMENT '아이디',
    `user_name`       varchar(50)  NOT NULL COMMENT '이름',
    `user_password`   varchar(200) NOT NULL COMMENT 'mysql password 사용',
    `user_birth`      varchar(8)   NOT NULL COMMENT '생년월일 : 19840503',
    `user_auth`       varchar(10)  NOT NULL COMMENT '권한: ROLE_ADMIN,ROLE_USER',
    `user_point`      int          NOT NULL COMMENT 'default : 1000000',
    `created_at`      datetime     NOT NULL COMMENT '가입일자',
    `latest_login_at` datetime DEFAULT NULL COMMENT '마지막 로그인 일자',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='회원';

create table `address`
(
    `address_id`    varchar(50)  NOT NULL,
    `address_line1` varchar(60)  NOT NULL,
    `address_line2` varchar(60)  NOT NULL,
    `city`          varchar(30)  NOT NULL,
    `sido`          varchar(100) NOT NULL,
    `postal_code`   varchar(15)  NOT NULL,
    `modified_date` timestamp default NULL,
    PRIMARY KEY (`address_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT '주소';


CREATE TABLE `users_address`
(
    `user_id`    varchar(50) default null comment '유저_아이디',
    `address_id` varchar(50) default null comment '주소_아이디',
    KEY `users_address_pk` (`user_id`, `address_id`),
    CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT `fk_address_id` FOREIGN KEY (`address_id`) REFERENCES `address` (address_id) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT '유저_주소';


/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2023. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

CREATE TABLE Categories
(
    CategoryID   INT auto_increment,
    CategoryName varchar(50),

    CONSTRAINT pk_Categories PRIMARY KEY (CategoryID)
);

CREATE TABLE Products
(
    ProductID    INT auto_increment,
    CategoryID   INT,
    ModelNumber  nvarchar(10),
    ModelName    nvarchar(120),
    ProductImage nvarchar(30),
    UnitCost     decimal(15),
    Description  text,

    CONSTRAINT pk_Products PRIMARY KEY (ProductID),
    CONSTRAINT fk_Products_Categories FOREIGN KEY (CategoryID) REFERENCES Categories (CategoryID)
);


CREATE TABLE Reviews
(
    ReviewID  int auto_increment,
    ProductID int,
    user_id   varchar(50),
    Rating    int,
    Comments  text,

    CONSTRAINT pk_ReviewID PRIMARY KEY (ReviewID),
    CONSTRAINT fk_Review_Products FOREIGN KEY (ProductID) REFERENCES Products (ProductID),
    CONSTRAINT fk_Review_users FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE Orders
(
    OrderID   int auto_increment,
    user_id   varchar(50),
    OrderDate Datetime,
    ShipDate  Datetime,

    CONSTRAINT pk_Orders PRIMARY KEY (OrderID),
    CONSTRAINT fk_Orders_users FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE OrderDetails
(
    OrderID   int,
    ProductID int,
    Quantity  int,
    UnitCost  decimal(15),

    CONSTRAINT pk_OrderDetails PRIMARY KEY (OrderID, ProductID),
    CONSTRAINT fk_OrderDetails_Orders FOREIGN KEY (OrderID) REFERENCES Orders (OrderID),
    CONSTRAINT fk_OrderDetails_Products FOREIGN KEY (ProductID) REFERENCES Products (ProductID)
);


CREATE TABLE ShoppingCart
(
    RecordID     int auto_increment,
    CartID       nvarchar(150),
    Quantity     int,
    ProductID    int,
    DateCreateed Datetime DEFAULT NOW(),

    CONSTRAINT pk_RecordID PRIMARY KEY (RecordID),
    CONSTRAINT fk_cart_ProductID FOREIGN KEY (ProductID) REFERENCES Products (ProductID)
);

