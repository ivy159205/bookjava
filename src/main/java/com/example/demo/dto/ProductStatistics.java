// src/main/java/com/example/demo/dto/ProductStatistics.java
package com.example.demo.dto;

public class ProductStatistics {
    private Long totalProducts;
    private Double averagePrice;
    private Long totalQuantity;
    private Float maxPrice;    // <-- Đổi thành Float
    private Float minPrice;    // <-- Đổi thành Float

    // Constructor phải khớp chính xác kiểu dữ liệu và thứ tự với truy vấn JPQL
    public ProductStatistics(Long totalProducts, Double averagePrice, Long totalQuantity, Float maxPrice, Float minPrice) {
        this.totalProducts = totalProducts;
        this.averagePrice = averagePrice;
        this.totalQuantity = totalQuantity;
        this.maxPrice = maxPrice;
        this.minPrice = minPrice;
    }

    // Constructor mặc định (vẫn cần)
    public ProductStatistics() {
    }

    // Getters and Setters
    public Long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public Long getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Long totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Float getMaxPrice() { // <-- Đổi thành Float
        return maxPrice;
    }

    public void setMaxPrice(Float maxPrice) { // <-- Đổi thành Float
        this.maxPrice = maxPrice;
    }

    public Float getMinPrice() { // <-- Đổi thành Float
        return minPrice;
    }

    public void setMinPrice(Float minPrice) { // <-- Đổi thành Float
        this.minPrice = minPrice;
    }
}