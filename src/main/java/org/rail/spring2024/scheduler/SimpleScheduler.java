package org.rail.spring2024.scheduler;

import org.rail.spring2024.annotation.NoteTime;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.math.BigDecimal;
import java.util.List;

public class SimpleScheduler {

    @Value("${app.scheduling.priceIncreasePercentage}")
    private Double number;

    @Autowired
    private ProductRepository productRepository;

    @NoteTime
    @Scheduled(fixedDelayString = "${app.scheduling.fixedDelay}", initialDelay = 5000L)
    public void simpleSchedule() {
        System.out.println("Start simple scheduler");
        List<Product> products = productRepository.findAll();
        products.forEach(
                product -> {
                    BigDecimal price = product.getPrice();
                    double multiplier = 1 + (number / 100);
                    BigDecimal newPrice = price.multiply(new BigDecimal(multiplier));
                    product.setPrice(newPrice);
                });
        System.out.println("Stop simple scheduler");
        productRepository.saveAll(products);
    }
}
