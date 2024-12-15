DROP DATABASE  IF EXISTS `stock_trading_app`;
CREATE DATABASE `stock_trading_app`;
USE `stock_trading_app`;

-- Create users_type table for user roles i.e. either Admin(1) or Trader(2)
CREATE TABLE `users_type` (
  `user_type_id` INT NOT NULL AUTO_INCREMENT,
  `user_type_name` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO `users_type` VALUES (1, 'Admin'), (2, 'Trader');

-- Create users table with users main information
CREATE TABLE `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) UNIQUE NOT NULL,
  `is_active` TINYINT(1) DEFAULT 1,
  `password` VARCHAR(255) NOT NULL,
  `registration_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `user_type_id` INT DEFAULT NULL,
  `first_name` VARCHAR(255) DEFAULT NULL,
  `last_name` VARCHAR(255) DEFAULT NULL,
  `profile_photo` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_user_type` (`user_type_id`),
  CONSTRAINT `FK_user_type` FOREIGN KEY (`user_type_id`) REFERENCES `users_type` (`user_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create stocks table
CREATE TABLE `stocks` (
  `stock_id` INT NOT NULL AUTO_INCREMENT,
  `stock_name` VARCHAR(255) NOT NULL,
  `stock_symbol` VARCHAR(10) NOT NULL UNIQUE,
  PRIMARY KEY (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create live_stock_price table for current stock prices
CREATE TABLE `live_stock_price` (
  `stock_id` INT PRIMARY KEY,
  `current_price` DECIMAL(10, 2) NOT NULL,
  `last_updated` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create stock_price_history table for historical prices
CREATE TABLE `stock_price_history` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `stock_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `open_price` DECIMAL(10, 2),
  `close_price` DECIMAL(10, 2),
  `high_price` DECIMAL(10, 2),
  `low_price` DECIMAL(10, 2),
  `volume` BIGINT,
  FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create transactions table for recording user transactions
-- ALTER TABLE `transactions` MODIFY COLUMN `quantity` DOUBLE NOT NULL;
CREATE TABLE `transactions` (
  `transaction_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `stock_id` INT NOT NULL,
  `transaction_type` ENUM('BUY', 'SELL') NOT NULL,
  `quantity` INT NOT NULL,
  `price` DECIMAL(10, 2) NOT NULL,
  `order_type` ENUM('MARKET', 'LIMIT', 'STOP') DEFAULT 'MARKET',
  `order_status` ENUM('PENDING', 'COMPLETED', 'CANCELLED') DEFAULT 'PENDING',
  `transaction_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`transaction_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create virtual_currency_transactions table for virtual currency management
CREATE TABLE `virtual_currency_transactions` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `transaction_type` ENUM('CREDIT', 'DEBIT') NOT NULL,
  `amount` DECIMAL(10, 2) NOT NULL,
  `transaction_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `transaction_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create virtual_currency_balance table to track user virtual currency
CREATE TABLE `virtual_currency_balance` (
  `user_id` INT PRIMARY KEY,
  `balance` DECIMAL(10, 2) DEFAULT 10000.00, -- Starting balance for virtual currency
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create posts table for user posts
CREATE TABLE `posts` (
  `post_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `content` TEXT NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`post_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create post_comments table for comments on posts
CREATE TABLE `post_comments` (
  `comment_id` INT NOT NULL AUTO_INCREMENT,
  `post_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `comment` TEXT NOT NULL,
  `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`comment_id`),
  FOREIGN KEY (`post_id`) REFERENCES `posts` (`post_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create messages table for user messaging
CREATE TABLE `messages` (
  `message_id` INT NOT NULL AUTO_INCREMENT,
  `sender_id` INT NOT NULL,
  `receiver_id` INT NOT NULL,
  `message_content` TEXT NOT NULL,
  `sent_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `is_read` TINYINT(1) DEFAULT 0,
  PRIMARY KEY (`message_id`),
  FOREIGN KEY (`sender_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`receiver_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create email_notifications table to track email notifications
CREATE TABLE `email_notifications` (
  `notification_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `subject` VARCHAR(255) NOT NULL,
  `body` TEXT NOT NULL,
  `sent_at` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `is_sent`TINYINT(1) DEFAULT 0,
  PRIMARY KEY (`notification_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Create indexes for performance
CREATE INDEX idx_user_id ON transactions(user_id);
CREATE INDEX idx_stock_symbol ON stocks(stock_symbol);
CREATE INDEX idx_user_posts ON posts(user_id);
CREATE INDEX idx_messages ON messages(receiver_id);
