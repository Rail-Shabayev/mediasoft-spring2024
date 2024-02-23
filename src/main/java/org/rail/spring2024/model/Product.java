package org.rail.spring2024.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @NotBlank(message = "username is required")
    @Size(min = 2)
    private String name;

    @NotBlank(message = "description is required")
    @Size(min = 2)
    private String description;

    @NotNull
    private ProductType type;

    @NotNull
    @Positive(message = "price must be positive")
    private BigDecimal price;

    @NotNull
    @Positive(message = "quantity must be positive")
    private int quantity;
    private LocalDateTime dateQuantityUpdated;
    private LocalDate dateCreated;
}
