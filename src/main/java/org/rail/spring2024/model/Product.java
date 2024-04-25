package org.rail.spring2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Product entity
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Product {
    /**
     * Unique identifier
     */

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    /**
     * name of the product
     */
    @NotBlank(message = "username is required")
    @Size(min = 2)
    private String name;

    /**
     * description of the product
     */
    @NotBlank(message = "description is required")
    @Size(min = 2)
    private String description;

    /**
     * type of the product
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private ProductType type;

    /**
     * price holds value of how much the product costs
     */
    @NotNull
    @Positive(message = "price must be positive")
    private BigDecimal price;

    /**
     * quantity of the product in storehouse
     */
    @NotNull
    @Positive(message = "quantity must be positive")
    private int quantity;

    /**
     * last quantity updated date
     */
    private LocalDateTime dateQuantityUpdated;

    /**
     * date of product creation
     */
    private LocalDate dateCreated;
}
