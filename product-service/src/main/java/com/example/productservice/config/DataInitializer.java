package com.example.productservice.config;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (productRepository.count() == 0) {
            log.info("Products table is empty. Initializing sample product data...");

            List<Product> initialProducts = List.of(
                    Product.builder()
                            .name("Laptop Dell XPS 15")
                            .price(1850.0)
                            .categoryId(1L)
                            .build(),
                    Product.builder()
                            .name("iPhone 16 Pro Max 256GB")
                            .price(1299.99)
                            .categoryId(2L)
                            .build(),
                    Product.builder()
                            .name("Sony WH-1000XM5 Wireless Headphones")
                            .price(399.0)
                            .categoryId(3L)
                            .build(),
                    Product.builder()
                            .name("Keychron K2 Mechanical Keyboard")
                            .price(89.9)
                            .categoryId(4L)
                            .build()
            );

            productRepository.saveAll(initialProducts);
            log.info("Successfully seeded {} products.", initialProducts.size());
        }
    }
}
