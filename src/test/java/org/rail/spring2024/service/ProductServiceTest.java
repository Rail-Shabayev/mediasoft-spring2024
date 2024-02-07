package org.rail.spring2024.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.rail.spring2024.model.ProductType.ELECTRONICS;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    @Mock
    ProductRepository productRepository;

    Product product1 = new Product
            (null, "laptop", "gaming laptop", ELECTRONICS,
                    new BigDecimal("2"), 2, null, LocalDate.now());
    ProductDTO productDto1 = new ProductDTO
            ("laptop", "gaming laptop", ELECTRONICS,
                    new BigDecimal("2"), 2, null, LocalDate.now());


    @Test
    @DisplayName("should map Product Entity to the ProductDTO object")
    void shouldMapEntityToDto() {
        ProductDTO actualProductDto = productService.mapToDto(product1);
        assertThat(actualProductDto.getName()).isEqualTo(product1.getName());
        assertThat(actualProductDto.getPrice()).isEqualTo(product1.getPrice());
    }

    @Test
    @DisplayName("should return all products")
    void shouldReturnAllProducts() {
        List<Product> productList = List.of(product1);
        List<ProductDTO> productDTOList = List.of(productDto1);
//
        when(productRepository.findAll()).thenReturn(productList);
//
        List<ProductDTO> actualProductList = productService.getAllProducts();

        assertThat(actualProductList.get(0).getName()).isEqualTo(productDTOList.get(0).getName());
    }
}