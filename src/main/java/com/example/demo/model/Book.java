package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data; // Nếu dùng Lombok

@Entity
@Table(name = "Book") // Đảm bảo tên bảng khớp với tên trong DB
@Data // Tự động tạo getters, setters, toString, equals, hashCode nếu dùng Lombok
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Integer publicationYear;

    public Book() {
    }

    public Book(String title, String author, String isbn, Integer publicationYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    // Nếu không dùng Lombok, bạn cần tự tạo các getters và setters thủ công:
    // public Long getId() { return id; }
    // public void setId(Long id) { this.id = id; }
    // ... và tương tự cho các thuộc tính khác.
}