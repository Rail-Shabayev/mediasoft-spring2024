package org.rail.spring2024.service;

import lombok.RequiredArgsConstructor;
import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
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
