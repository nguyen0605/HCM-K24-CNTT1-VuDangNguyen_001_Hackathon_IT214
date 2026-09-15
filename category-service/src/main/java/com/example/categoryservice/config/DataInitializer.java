package com.example.categoryservice.config;

import com.example.categoryservice.entity.Category;
import com.example.categoryservice.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            log.info("Categories table is empty. Initializing sample category data...");

            List<Category> initialCategories = List.of(
                    Category.builder()
                            .name("Electronics")
                            .description("Laptops, Desktops, and electronic appliances")
                            .build(),
                    Category.builder()
                            .name("Smartphones")
                            .description("Mobile phones, tablets, and smart devices")
                            .build(),
                    Category.builder()
                            .name("Audio")
                            .description("Headphones, earphones, and speakers")
                            .build(),
                    Category.builder()
                            .name("Accessories")
                            .description("Keyboards, mice, chargers, and cables")
                            .build()
            );

            categoryRepository.saveAll(initialCategories);
            log.info("Successfully seeded {} categories.", initialCategories.size());
        }
    }
}
