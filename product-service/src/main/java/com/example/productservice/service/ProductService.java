package com.example.productservice.service;

import com.example.productservice.client.CategoryClient;
import com.example.productservice.dto.CategoryDTO;
import com.example.productservice.dto.ProductRequestDTO;
import com.example.productservice.entity.Product;
import com.example.productservice.exception.CategoryNotFoundException;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryClient categoryClient;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sản phẩm với ID " + id + " không tồn tại"));
    }

    public Product createProduct(ProductRequestDTO requestDTO) {
        try {
            CategoryDTO category = categoryClient.getCategoryById(requestDTO.getCategoryId());
            if (category == null) {
                throw new CategoryNotFoundException("Danh mục với ID " + requestDTO.getCategoryId() + " không tồn tại");
            }
        } catch (CategoryNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new CategoryNotFoundException("Danh mục với ID " + requestDTO.getCategoryId() + " không tồn tại hoặc không thể kết nối tới category-service");
        }

        Product product = Product.builder()
                .name(requestDTO.getName())
                .price(requestDTO.getPrice())
                .categoryId(requestDTO.getCategoryId())
                .build();

        return productRepository.save(product);
    }
}
