package org.rail.spring2024.batch;

import org.rail.spring2024.model.Product;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ProductPriceProcessor implements ItemProcessor<Product, Product> {

    private final BigDecimal number;

    public ProductPriceProcessor(BigDecimal number) {
        this.number = number;
    }

    @Override
    public Product process(Product product) {
        BigDecimal price = product.getPrice();
        BigDecimal multiplier = BigDecimal.ONE.add(number.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
        product.setPrice(price.multiply(multiplier));
        return product;
    }
}
