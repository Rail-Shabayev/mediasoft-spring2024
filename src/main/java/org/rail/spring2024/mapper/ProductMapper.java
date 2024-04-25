package org.rail.spring2024.mapper;

import org.rail.spring2024.dto.ProductDto;
import org.rail.spring2024.model.Product;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


/**
 * class responsible to map productDto to product and vise versa
 */
@Configuration
public class ProductMapper {

    /** maps {@link ProductDto} to {@link Product} object
     * @param productDTO {@link ProductDto} object that needs to be mapped to {@link Product}
     * @param uuid responsible for differentiating between POST and PUT methods
     *             if uuid is null then it saves object as new object in POST request
     *             else it saves {@link ProductDto} with old uuid as in PUT request
     * @return {@link Product} object
     */
    public Product mapToEntity(ProductDto productDTO, UUID uuid) {
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

    /**
     * maps {@link Product}  to {@link ProductDto} object
     * @param product {@link Product} object that needs to be mapped to {@link ProductDto}
     * @return {@link ProductDto} object
     */
    public ProductDto mapToDto(Product product) {
        return ProductDto.builder()
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
