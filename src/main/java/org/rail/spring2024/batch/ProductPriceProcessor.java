package org.rail.spring2024.batch;

import org.rail.spring2024.model.Product;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

public class ProductPriceProcessor implements ItemProcessor<Product, Product> {

    private final Double number;

    public ProductPriceProcessor(Double number) {
        this.number = number;
    }

    @Override
    public Product process(Product product) {
        BigDecimal price = product.getPrice();
        double multiplier = 1 + (number / 100);
        BigDecimal newPrice = price.multiply(new BigDecimal(multiplier));
        product.setPrice(newPrice);
        return product;
    }
}
