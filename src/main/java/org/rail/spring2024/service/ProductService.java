package org.rail.spring2024.service;

import lombok.RequiredArgsConstructor;
import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
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

    public String saveProduct(ProductDTO productDTO) {
        List<String> names = getAllProducts().stream().map(ProductDTO::getName).toList();
        if (names.contains(productDTO.getName())) {
            return "this product already exists";
        }
        Product product = mapToEntity(productDTO, null);
        productRepository.save(product);
        return "product saved";
    }

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


    public String putProduct(String name, ProductDTO productDTO) {
        List<String> names = getAllProducts().stream().map(ProductDTO::getName).toList();
        if (!names.contains(name)) {
            return "this product dosen't exists";
        }
        Product product = productRepository.findByName(name);
        productRepository.save(mapToEntity(productDTO, product.getUuid()));
        return "product updated";
    }

    public String deleteProduct(String productName) {
        List<String> names = getAllProducts().stream().map(ProductDTO::getName).toList();
        if (!names.contains(productName)) {
            return "this product dosen't exists";
        }
        productRepository.deleteByName(productName);
        return "product deleted";
    }
}
