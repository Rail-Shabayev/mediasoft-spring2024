package org.rail.spring2024.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.mapper.ProductMapper;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.rail.spring2024.model.ProductType.ELECTRONICS;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository, mapper);
    }

    @Mock
    ProductMapper mapper;
    @Mock
    ProductRepository productRepository;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    Product product1 = new Product
            (null, "laptop", "gaming laptop", ELECTRONICS,
                    new BigDecimal("2"), 2, null, LocalDate.now());
    ProductDTO productDto1 = new ProductDTO
            ("laptop", "gaming laptop", ELECTRONICS,
                    new BigDecimal("2"), 2, null, LocalDate.now());



    @Test
    @DisplayName("should return all products")
    void shouldReturnAllProducts() {
        List<Product> productList = List.of(product1);
        List<ProductDTO> productDTOList = List.of(productDto1);
        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDTO> actualProductList = productService.getAllProducts();

        assertThat(actualProductList.get(0).getName()).isEqualTo(productDTOList.get(0).getName());
    }


    @Test
    @DisplayName("should save ProductDto")
    void shouldSaveProductDto() {
        productRepository.save(product1);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());

        assertThat(productArgumentCaptor.getValue().getName()).isEqualTo("laptop");
        assertThat(productArgumentCaptor.getValue().getDescription()).isEqualTo("gaming laptop");
    }

    @Test
    @DisplayName("should put ProductDto")
    void shouldPutProductDto() {
        product1.setName("purse");
        productRepository.save(product1);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());

        assertThat(productArgumentCaptor.getValue().getName()).isEqualTo("purse");
        assertThat(productArgumentCaptor.getValue().getDescription()).isEqualTo("gaming laptop");
    }

    @Test
    @DisplayName("should delete ProductDto")
    void shouldDeleteProductDto() {
        when(productRepository.findByName("laptop")).thenReturn(Optional.ofNullable(product1));
        productRepository.delete(product1);
        verify(productRepository,times(1)).delete(product1);
    }
}