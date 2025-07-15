CREATE DATABASE ProductManagement
GO
USE ProductManagement
GO
CREATE TABLE tbl_Product(
	id INT PRIMARY KEY IDENTITY(1, 1),
	p_name NVARCHAR(255),
	price FLOAT,
	quantity INT,
	p_description NVARCHAR(255)
);