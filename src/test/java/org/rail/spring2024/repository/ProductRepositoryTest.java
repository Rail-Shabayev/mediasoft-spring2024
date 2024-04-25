package org.rail.spring2024.repository;

import org.instancio.Instancio;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.rail.spring2024.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductRepositoryTest extends ContainerStart{
    @Autowired
    private ProductRepository productRepository;

    Product product = Instancio.create(Product.class);

    @Test
    @DisplayName("should find a product by it's UUID")
    public void shouldFindById() {
        //Arrange
        productRepository.save(product);
        //Act
        Optional<Product> actualProduct = productRepository.findById(product.getUuid());
        //Assert
        assertThat(actualProduct.get()
                .getName()).isEqualTo(product.getName());
    }
    @Test
    @DisplayName("should find a product using criteriaApi")
    public void shouldFindByCriteria() {
        //Arrange
        productRepository.save(product);
        Specification<Product> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("name"), product.getName());
        //Act
        String  fetchedProductName = productRepository.findAll(specification)
                .get(0)
                .getName();
        //Assert
        assertThat(fetchedProductName).isEqualTo(product.getName());
        assertThat(fetchedProductName).isNotEqualTo("some name");
    }

}
