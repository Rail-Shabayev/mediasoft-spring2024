package org.rail.spring2024.batch;

import org.rail.spring2024.dao.ProductDao;
import org.rail.spring2024.model.Product;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductWriter implements ItemWriter<Product> {

    @Autowired
    private ProductDao productRepository;

    @Override
    public void write(Chunk<? extends Product> chunk) {
        productRepository.saveAll((List<Product>) chunk.getItems());
    }
}
