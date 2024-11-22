use stock_trading_app;
CREATE TABLE `portfolio` (
  `user_id` INT NOT NULL,
  `stock_id` INT NOT NULL,
  `total_quantity` Decimal(10,2) DEFAULT 0.0,
  `average_price` DECIMAL(10, 2) DEFAULT 0.00,
  `profit_loss` DECIMAL(10,2) DEFAULT 0.00,
  PRIMARY KEY (`user_id`, `stock_id`),
  FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  FOREIGN KEY (`stock_id`) REFERENCES `stocks` (`stock_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;