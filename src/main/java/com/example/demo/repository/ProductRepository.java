// src/main/java/com/example/demo/repository/ProductRepository.java
package com.example.demo.repository;

import com.example.demo.model.Product;
import com.example.demo.dto.ProductStatistics; // Import DTO mới
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByPrice(Float price);
    List<Product> findByNameContainingIgnoreCaseAndPrice(String name, Float price);

    // --- Các phương thức thống kê mới ---

    @Query("SELECT NEW com.example.demo.dto.ProductStatistics(" +
           "COUNT(p.id), " +       // Tổng số sản phẩm
           "AVG(p.price), " +      // Giá trung bình
           "SUM(p.quantity), " +   // Tổng số lượng
           "MAX(p.price), " +      // Giá cao nhất
           "MIN(p.price)) " +      // Giá thấp nhất
           "FROM Product p")
    ProductStatistics getProductGeneralStatistics();
}