CREATE DATABASE BookManagement;
GO

USE BookManagement;
GO

CREATE TABLE Book (
    id INT PRIMARY KEY IDENTITY(1,1),
    title NVARCHAR(255) NOT NULL,
    author NVARCHAR(255) NOT NULL,
    isbn NVARCHAR(20) UNIQUE NOT NULL,
    publicationYear INT
);