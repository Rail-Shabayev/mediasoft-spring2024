package org.rail.spring2024.scheduler;

import org.rail.spring2024.annotation.NoteTime;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class SimpleScheduler {

    @Value("#{new java.math.BigDecimal(\"${app.scheduling.priceIncreasePercentage}\")}")
    private BigDecimal number;

    @Autowired
    private ProductRepository productRepository;

    @NoteTime
    @Scheduled(fixedDelayString = "${app.scheduling.fixedDelay}", initialDelay = 5000L)
    @Transactional
    public void simpleSchedule() {
        System.out.println("Start simple scheduler");
        List<Product> products = productRepository.findAll();
        products.forEach(
                product -> {
                    BigDecimal price = product.getPrice();
                    BigDecimal multiplier = BigDecimal.ONE.add(number.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
                    product.setPrice(price.multiply(multiplier));
                });
        System.out.println("Stop simple scheduler");
        productRepository.saveAll(products);
    }
}
