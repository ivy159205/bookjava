package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
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
        return product.map(ResponseEntity::ok) // Nếu tìm thấy, trả về 200 OK với Product
                      .orElse(ResponseEntity.notFound().build()); // Nếu không tìm thấy, trả về 404 Not Found
    }

    // Endpoint để tạo sản phẩm mới
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Trả về 201 Created khi tạo thành công
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // Endpoint để cập nhật sản phẩm hiện có
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct); // Trả về 200 OK với sản phẩm đã cập nhật
        } else {
            return ResponseEntity.notFound().build(); // Trả về 404 Not Found nếu không tìm thấy sản phẩm để cập nhật
        }
    }

    // Endpoint để xóa sản phẩm
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Trả về 204 No Content khi xóa thành công
    public void deleteProduct(@PathVariable Long id) {
        // productService.deleteProduct(id) trả về boolean, bạn có thể kiểm tra nó
        // để trả về 404 nếu ID không tồn tại, nhưng hiện tại nó chỉ xóa và trả 204
        // nếu thành công hoặc có lỗi nếu ID không tồn tại và không được kiểm tra trước đó.
        productService.deleteProduct(id);
    }
}