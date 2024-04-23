package org.rail.spring2024.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.rail.spring2024.model.ProductType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Dto object of product entity
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {
    /**
     * name of the product
     */

    @Schema(description = "name of the product",
            type = "string",
            example = "Keyboard")
    private String name;

    /**
     * description of the product
     */
    @Schema(description = "description of the product",
            type = "string",
            example = "Super keyboard")
    private String description;


    /**
     * type of the product
     */
    @Schema(description = "type of the product",
            example = "CLOTHING")
    private ProductType type;

    /**
     * price holds value of how much the product costs
     */
    @Schema(description = "price for a one product",
            example = "3")
    private BigDecimal price;

    /**
     * quantity of the product in storehouse
     */
    @Schema(description = "quantity of products in storehouse",
            example = "2")
    private int quantity;

    /**
     * last quantity updated date
     * it can't be set by the user
     */
    @Setter(AccessLevel.NONE)
    @Schema(description = "date and time when quantity was last updated")
    private LocalDateTime dateQuantityUpdated;

    /**
     * date of product creation
     * it can't be set by the user
     */
    @Setter(AccessLevel.NONE)
    @Schema(description = "date of product creation")
    private LocalDate dateCreated;
}
