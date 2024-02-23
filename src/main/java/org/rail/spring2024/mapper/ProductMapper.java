package org.rail.spring2024.mapper;

import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.model.Product;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class ProductMapper {

    public Product mapToEntity(ProductDTO productDTO, UUID uuid) {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        return Product.builder()
                .uuid(uuid)
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .type(productDTO.getType())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .dateQuantityUpdated(productDTO.getDateQuantityUpdated())
                .dateCreated(productDTO.getDateCreated())
                .build();
    }

    public ProductDTO mapToDto(Product product) {
        return ProductDTO.builder()
                .name(product.getName())
                .description(product.getDescription())
                .type(product.getType())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .dateCreated(product.getDateCreated())
                .dateQuantityUpdated(product.getDateQuantityUpdated())
                .build();
    }

}
