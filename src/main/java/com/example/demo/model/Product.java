package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data; // Nếu dùng Lombok
import jakarta.persistence.Column; 

@Entity
@Table(name = "tbl_Product") // Đảm bảo tên bảng khớp với tên trong DB
@Data // Tự động tạo getters, setters, toString, equals, hashCode nếu dùng Lombok
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    
    @Column(name = "p_name")
    private String name;
    private Float price;
    private Integer quantity;
    @Column(name = "p_description")
    private String description;

    public Product() {
    }

    public Product(String name, Float price, Integer quantity, String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    // Nếu không dùng Lombok, bạn cần tự tạo các getters và setters thủ công:
    // public Long getId() { return id; }
    // public void setId(Long id) { this.id = id; }
    // ... và tương tự cho các thuộc tính khác.
}