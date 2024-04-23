package org.rail.spring2024.service;

import org.instancio.Instancio;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.rail.spring2024.dto.ProductDto;
import org.rail.spring2024.exception.ProductNotFoundException;
import org.rail.spring2024.mapper.ProductMapper;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {


    @Mock
    ProductMapper mapper;

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;



    Product product = Instancio.of(Product.class)
            .set(field(Product::getName), "laptop")
            .set(field(Product::getDescription), "gaming laptop")
            .create();
    ProductDto productDto = Instancio.create(ProductDto.class);

    @Test
    @DisplayName("should return all products")
    void shouldReturnAllProducts() {
        List<Product> productList = List.of(product);
        List<ProductDto> productDtoList = List.of(productDto);

        when(productRepository.findAll(any(Pageable.class))).thenReturn((Page<Product>) productList);
        when(mapper.mapToDto(product)).thenReturn(productDto);
//        List<ProductDto> actualProductList = productService.getAllProducts(any());

//        assertThat(actualProductList.get(0).getName()).isEqualTo(productDtoList.get(0).getName());
    }


    @Test
    @DisplayName("should save ProductDto")
    void shouldSaveProductDto() {
        productRepository.save(product);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());

        assertThat(productArgumentCaptor.getValue().getName()).isEqualTo("laptop");
        assertThat(productArgumentCaptor.getValue().getDescription()).isEqualTo("gaming laptop");
    }

    @Test
    @DisplayName("should put ProductDto")
    void shouldPutProductDto() {
        product.setName("purse");
        productRepository.save(product);
        verify(productRepository, times(1)).save(productArgumentCaptor.capture());

        assertThat(productArgumentCaptor.getValue().getName()).isEqualTo("purse");
        assertThat(productArgumentCaptor.getValue().getDescription()).isEqualTo("gaming laptop");
    }

    @Test
    @DisplayName("should throw a ProductNotFound exception")
    void shouldThrowException() {
        product.setName("purse");
        productRepository.save(product);

        assertThatExceptionOfType(ProductNotFoundException.class).isThrownBy(() -> productService.putProduct("purse", productDto));

    }

    @Test
    @DisplayName("should delete ProductDto")
    void shouldDeleteProductDto() {
        productRepository.delete(product);
        verify(productRepository, times(1)).delete(productArgumentCaptor.capture());

        assertThat(productArgumentCaptor.getValue().getDescription()).isEqualTo("gaming laptop");
    }
}