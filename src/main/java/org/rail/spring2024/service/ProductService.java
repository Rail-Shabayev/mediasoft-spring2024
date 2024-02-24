package org.rail.spring2024.service;

import lombok.RequiredArgsConstructor;
import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.exception.ProductNotFoundException;
import org.rail.spring2024.mapper.ProductMapper;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    public String saveProduct(ProductDTO productDTO) {
        productRepository.findByName(productDTO.getName()).ifPresent(product -> {
            throw new RuntimeException("Product with that name already exists");
        });

        Product product = mapper.mapToEntity(productDTO, null);
        product.setDateCreated(LocalDate.now());
        product.setDateQuantityUpdated(LocalDateTime.now());

        productRepository.save(product);
        return "product saved";
    }

    public String putProduct(String productName, ProductDTO productDTO) throws ProductNotFoundException {
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with name: " + productName));
        if (!productName.equals(productDTO.getName())) {
            productRepository.findByName(productDTO.getName()).ifPresent(p -> {
                throw new RuntimeException("Product with that name already exists");
            });
        }
        Product mappedToEntity = mapper.mapToEntity(productDTO, product.getUuid());
        mappedToEntity.setDateQuantityUpdated(LocalDateTime.now());
        mappedToEntity.setDateCreated(product.getDateCreated());
        productRepository.save(mappedToEntity);
        return "product updated";
    }

    public String deleteProduct(String productName) throws ProductNotFoundException {
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with name: " + productName));
        productRepository.delete(product);
        return "product deleted";
    }
}
