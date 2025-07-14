package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    // Phương thức tìm kiếm tùy chỉnh
    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String titleKeyword, String authorKeyword);
}