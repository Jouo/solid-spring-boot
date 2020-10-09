DROP DATABASE IF EXISTS `solid-spring-boot`;
CREATE DATABASE `solid-spring-boot`;
USE `solid-spring-boot`;

SET FOREIGN_KEY_CHECKS = 0;


-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||
--                  User, Account, Role
-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    
    `user_id`       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(50) NOT NULL,
    `last_name`     VARCHAR(50) NOT NULL DEFAULT "",
    `account_id`    INT DEFAULT NULL,
    
    FOREIGN KEY (`account_id`) REFERENCES `account`(`account_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
    
    `account_id`    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `username`      VARCHAR(50) UNIQUE NOT NULL,
    `password`      VARCHAR(60) NOT NULL,
    `ban_time`      BIGINT NOT NULL DEFAULT FALSE,
    `locked`        TINYINT NOT NULL DEFAULT FALSE
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    
    `role_id`       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `role`          VARCHAR(20) NOT NULL,
    `description`   VARCHAR(50) NOT NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||
--      Configuration, Provider, cash_register, Printer
-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||


DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
    
    `configuration_id`  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `company`           VARCHAR(100) NOT NULL,
    `address`           VARCHAR(100) NOT NULL,
    `phone`             VARCHAR(100) NOT NULL,
    `message`           VARCHAR(100) NOT NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `provider`;
CREATE TABLE `provider` (
    
    `provider_id`   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(50) NOT NULL,
    `company`       VARCHAR(50) NOT NULL,
    `phone`         VARCHAR(50) NOT NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `cash_register`;
CREATE TABLE `cash_register` (
    
    `cash_register_id`  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(50) NOT NULL,
    `printer_id`        INT DEFAULT NULL,
    
    FOREIGN KEY (`printer_id`) REFERENCES `printer`(`printer_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `printer`;
CREATE TABLE `printer` (
    
    `printer_id`    INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(50) NOT NULL,
    `ip`            VARCHAR(50) NOT NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
--               Category, Product, Stock, Group
-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
    
    `category_id`   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`          VARCHAR(50) NOT NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    
    `product_id`        INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `name`              VARCHAR(50) NOT NULL,
    `cost`              FLOAT(10,2) NOT NULL,
    `price`             FLOAT(10,2) NOT NULL,
    `image`             VARCHAR(100) NOT NULL,
    `category_id`       INT DEFAULT NULL,
    `stock_id`          INT DEFAULT NULL,
    
    FOREIGN KEY (`category_id`) REFERENCES `category`(`category_id`)
        ON UPDATE CASCADE ON DELETE SET NULL,
        
    FOREIGN KEY (`stock_id`) REFERENCES `stock`(`stock_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock` (

    `stock_id`  INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `quantity`  INT NOT NULL DEFAULT 0
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
    
    `group_id`      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `quantity`      INT NOT NULL DEFAULT 0,
    `product_id`    INT DEFAULT NULL,
    
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||
--            Checkout, Purchase, Sale, Order
-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||


DROP TABLE IF EXISTS `checkout`;
CREATE TABLE `checkout` (
    
    `checkout_id`   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `date`          BIGINT NOT NULL,
    `user_id`       INT DEFAULT NULL,
    `sales`         FLOAT(10,2) NOT NULL,
    `purchases`     FLOAT(10,2) NOT NULL,
    
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
        
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `purchase`;
CREATE TABLE `purchase` (
    
    `purchase_id`   INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `date`          BIGINT NOT NULL,
    `total`         FLOAT(10,2) NOT NULL,
    `user_id`       INT DEFAULT NULL,
    `provider_id`   INT DEFAULT NULL,
    `checkout_id`   INT DEFAULT NULL,
    
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
        ON UPDATE CASCADE ON DELETE SET NULL,
        
    FOREIGN KEY (`provider_id`) REFERENCES `provider`(`provider_id`)
        ON UPDATE CASCADE ON DELETE SET NULL,
    
    FOREIGN KEY (`checkout_id`) REFERENCES `checkout`(`checkout_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `sale`;
CREATE TABLE `sale` (
    
    `sale_id`           INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `date`              BIGINT NOT NULL,
    `total`             FLOAT(10,2) NOT NULL,
    `user_id`           INT DEFAULT NULL,
    `cash_register_id`  INT DEFAULT NULL,  
    `checkout_id`       INT DEFAULT NULL,
    
    FOREIGN KEY (`user_id`) REFERENCES `user`(`user_id`)
        ON UPDATE CASCADE ON DELETE SET NULL,
        
    FOREIGN KEY (`cash_register_id`) REFERENCES `cash_register`(`cash_register_id`)
        ON UPDATE CASCADE ON DELETE SET NULL,
        
    FOREIGN KEY (`checkout_id`) REFERENCES `checkout`(`checkout_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
        
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    
    `order_id`      INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `quantity`      INT NOT NULL,
    `total`         FLOAT(10,2) NOT NULL,
    `product_id`    INT DEFAULT NULL,
    `sale_id`       INT DEFAULT NULL,
    
    FOREIGN KEY (`sale_id`) REFERENCES `sale`(`sale_id`)
        ON UPDATE CASCADE ON DELETE SET NULL,
    
    FOREIGN KEY (`product_id`) REFERENCES `product`(`product_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
--                        Relationships
-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
    
    `id`            INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `account_id`    INT DEFAULT NULL,
    `role_id`       INT DEFAULT NULL,
    
    FOREIGN KEY (`account_id`) REFERENCES `account`(`account_id`)
        ON UPDATE CASCADE ON DELETE SET NULL,
    
    FOREIGN KEY (`role_id`) REFERENCES `role`(`role_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


DROP TABLE IF EXISTS `purchase_group`;
CREATE TABLE `purchase_group` (
    
    `id`            INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `purchase_id`   INT DEFAULT NULL,
    `group_id`      INT DEFAULT NULL,
    
    FOREIGN KEY (`purchase_id`) REFERENCES `purchase`(`purchase_id`)
        ON UPDATE CASCADE ON DELETE SET NULL,
        
    FOREIGN KEY (`group_id`) REFERENCES `group`(`group_id`)
        ON UPDATE CASCADE ON DELETE SET NULL
    
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;


-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
--      Default Data, values are used during unit tests
-- ||||||||||||||||||||||||||||||||||||||||||||||||||||||||||


INSERT INTO `user`(`name`, `last_name`, `account_id`) VALUES
('Jashua','Developer', 1),
('JUnit','Mockito', 2);


-- Password is: 123
INSERT INTO `account`(`username`, `password`) VALUES
('developer','$2y$12$ytZChBP15Dh8HO1vKJOlHOsO1WEi1etsIYWQhECkOM6KlDLiZlO7i'),
('TDD','$2y$12$ytZChBP15Dh8HO1vKJOlHOsO1WEi1etsIYWQhECkOM6KlDLiZlO7i');


 INSERT INTO `role`(`role`, `description`) VALUES
('ROLE_admin','Administrator'),
('ROLE_employee','Employee');


 INSERT INTO `account_role`(`account_id`, `role_id`) VALUES
(1, 1), (2, 2);


 INSERT INTO `configuration`(`company`, `address`, `phone`, `message`) VALUES
('Little Shop','Vancouver','778-882-8291','¡Thanks for shopping!');


INSERT INTO `provider`(`name`, `company`, `phone`) VALUES
('George Williams','Beefway Meats ltd', '778-572-7853'),
('Joseph Smith','Van-Whole Produce ltd', '778-608-6724'),
('Aubrey Brown','Donia Farms ltd', '778-184-1968');


 INSERT INTO `cash_register`(`name`, `printer_id`) VALUES
('Main Cash Register', 1),
('Secondary Cash Register', 2);


 INSERT INTO `printer`(`name`, `ip`) VALUES
('Main Printer', '10.0.0.5'),
('Secondary Printer', '10.0.0.6');


 INSERT INTO `category`(`name`) VALUES
('Beverage'),
('Dairy'),
('Meat'),
('Produce'),
('Other');


INSERT INTO `product`(`name`, `cost`, `price`, `image`, `category_id`, `stock_id`) VALUES
('Soda', 1, 2, '/beverages/soda.jpeg', 1, 1),
('Tea', 1, 2, '/beverages/tea.jpeg', 1, 2),
('Coffee', 1, 2, '/beverages/coffee.jpeg', 1, 3),
('Eggs', 3, 5, '/dairy/eggs.jpeg', 2, 4),
('Milk', 1.5, 3, '/dairy/milk.jpeg', 2, 5),
('Cheese', 0.5, 1, '/dairy/cheese.jpeg', 2, 6),
('Beef', 2, 4, '/meat/beef.jpeg', 3, 7),
('Chicken', 2, 3, '/meat/chicken.jpeg', 3, 8), 
('Pork', 2, 4, '/meat/pork.jpeg', 3, 9),
('Apples', 2, 4, '/produce/apples.jpeg', 4, 10),
('Bannanas', 1, 3, '/produce/bannanas.jpeg', 4, 11),
('Grapes', 3, 5, '/produce/grapes.jpeg', 4, 12),
('Cigarettes', 2, 3, '/other/cigarettes.jpeg', 5, 13),
('Lighter', 1, 2, '/other/lighter.jpeg', 5, 14),
('Pizza', 4, 10, '/other/pizza.jpeg', 5, 15);


INSERT INTO `stock`(`quantity`) VALUES
(50), (30), (25), (150), (35), (20), (20), (30), (25), (75), (100), (25), (35), (20), (50);


SET FOREIGN_KEY_CHECKS = 1;