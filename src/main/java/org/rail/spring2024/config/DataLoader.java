package org.rail.spring2024.config;

import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;
import static org.rail.spring2024.model.ProductType.*;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner runner(ProductRepository productRepository) {
        return args -> {
            Product product1 = new Product
                    (randomUUID(), "laptop", "gaming laptop", ELECTRONICS,
                            new BigDecimal("2"), 2, LocalDateTime.now(), LocalDate.now());
            Product product2 = new Product
                    (randomUUID(), "snikers", "best snikers", FOOTWEAR,
                            new BigDecimal("1"), 5, LocalDateTime.now(), LocalDate.now());
            Product product3 = new Product
                    (randomUUID(), "purse", "gucci purse", ACCESSORIES,
                            new BigDecimal("3"), 10, LocalDateTime.now(), LocalDate.now());
            productRepository.save(product1);
            productRepository.save(product2);
            productRepository.save(product3);
        };
    }
}
