package org.rail.spring2024.dto;

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
    private String name;
    private String description;
    private ProductType type;
    private BigDecimal price;
    private int quantity;
    @Setter(AccessLevel.NONE)
    private LocalDateTime dateQuantityUpdated;
    @Setter(AccessLevel.NONE)
    private LocalDate dateCreated;
}
