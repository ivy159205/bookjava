package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Tìm kiếm theo tên không phân biệt chữ hoa chữ thường
    List<Product> findByNameContainingIgnoreCase(String name);

    // Tìm kiếm theo giá chính xác
    List<Product> findByPrice(Float price);

    // Tìm kiếm theo tên không phân biệt chữ hoa chữ thường VÀ theo giá
    List<Product> findByNameContainingIgnoreCaseAndPrice(String name, Float price);
}