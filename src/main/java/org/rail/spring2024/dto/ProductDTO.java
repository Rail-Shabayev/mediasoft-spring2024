package org.rail.spring2024.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.rail.spring2024.model.ProductType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    @Schema(description = "name of the product",
            type = "string",
            example = "Keyboard")
    private String name;

    @Schema(description = "description of the product",
            type = "string",
            example = "Super keyboard")
    private String description;

    @Schema(description = "type of the product",
            example = "CLOTHING")
    private ProductType type;

    @Schema(description = "price for a one product",
            example = "3")
    private BigDecimal price;

    @Schema(description = "quantity of products in storehouse",
            example = "2")
    private int quantity;

    @Setter(AccessLevel.NONE)
    @Schema(description = "date and time when quantity was last updated")
    private LocalDateTime dateQuantityUpdated;

    @Setter(AccessLevel.NONE)
    @Schema(description = "date of product creation")
    private LocalDate dateCreated;
}
