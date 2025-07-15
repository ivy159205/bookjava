// src/main/java/com/example/demo/controller/ProductController.java
package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import com.example.demo.dto.ProductStatistics; // Import DTO thống kê
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://127.0.0.1:5500") // Đảm bảo đúng cổng của Live Server
public class ProductController {

    @Autowired
    private ProductService productService;

    // Endpoint GET chính để lấy tất cả sản phẩm và hỗ trợ tìm kiếm linh hoạt
    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Float price) {

        if (name != null && !name.isEmpty() && price != null) {
            // Tìm kiếm theo cả tên và giá
            return productService.searchProductsByNameAndPrice(name, price);
        } else if (name != null && !name.isEmpty()) {
            // Tìm kiếm chỉ theo tên
            return productService.searchProductsByName(name);
        } else if (price != null) {
            // Tìm kiếm chỉ theo giá
            return productService.searchProductsByPrice(price);
        } else {
            // Lấy tất cả sản phẩm nếu không có tham số tìm kiếm nào
            return productService.getAllProducts();
        }
    }

    // Endpoint để tìm sản phẩm theo ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint để tạo sản phẩm mới
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Endpoint để cập nhật sản phẩm hiện có
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint để xóa sản phẩm
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    // --- Endpoint thống kê mới ---
    @GetMapping("/statistics")
    public ResponseEntity<ProductStatistics> getProductStatistics() {
        ProductStatistics stats = productService.getProductStatistics();
        if (stats != null) {
            return ResponseEntity.ok(stats);
        } else {
            // Trường hợp không có sản phẩm nào, hoặc lỗi trong service/repo
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}