
/* Creating a user with name stock_trading_app */
DROP USER IF EXISTS 'stock_trading_app'@'localhost';
/* Identified by ..... to set password */
CREATE USER 'stock_trading_app'@'localhost' IDENTIFIED BY 'stock_trading_app';
GRANT ALL PRIVILEGES ON * . * TO 'stock_trading_app';