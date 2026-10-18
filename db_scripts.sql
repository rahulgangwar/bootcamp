show databases;
CREATE DATABASE IF NOT EXISTS main_db;
USE main_db;


-- Check bin logs
SHOW VARIABLES LIKE 'log_bin';
SHOW VARIABLES LIKE 'binlog_format';
SHOW BINARY LOGS;