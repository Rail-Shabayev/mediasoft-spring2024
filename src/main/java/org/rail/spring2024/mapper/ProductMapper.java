package org.rail.spring2024.mapper;

import org.rail.spring2024.dto.ProductDTO;
import org.rail.spring2024.model.Product;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;


/**
 * class responsible to map productDto to product and vise versa
 */
@Configuration
public class ProductMapper {

    /** maps {@link ProductDTO} to {@link Product} object
     * @param productDTO {@link ProductDTO} object that needs to be mapped to {@link Product}
     * @param uuid responsible for differentiating between POST and PUT methods
     *             if uuid is null then it saves object as new object in POST request
     *             else it saves {@link ProductDTO} with old uuid as in PUT request
     * @return {@link Product} object
     */
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

    /**
     * maps {@link Product}  to {@link ProductDTO} object
     * @param product {@link Product} object that needs to be mapped to {@link ProductDTO}
     * @return {@link ProductDTO} object
     */
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
