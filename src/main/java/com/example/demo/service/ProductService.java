// src/main/java/com/example/demo/service/ProductService.java
package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.dto.ProductStatistics; // Import DTO

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(productDetails.getName());
                    product.setPrice(productDetails.getPrice());
                    product.setQuantity(productDetails.getQuantity());
                    product.setDescription(productDetails.getDescription());
                    return productRepository.save(product);
                }).orElse(null);
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Product> searchProductsByName(String nameKeyword) {
        return productRepository.findByNameContainingIgnoreCase(nameKeyword);
    }

    public List<Product> searchProductsByPrice(Float priceKeyword) {
        return productRepository.findByPrice(priceKeyword);
    }

    public List<Product> searchProductsByNameAndPrice(String nameKeyword, Float priceKeyword) {
        return productRepository.findByNameContainingIgnoreCaseAndPrice(nameKeyword, priceKeyword);
    }

    // --- Phương thức thống kê mới ---
    public ProductStatistics getProductStatistics() {
        return productRepository.getProductGeneralStatistics();
    }
}