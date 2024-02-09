package org.rail.spring2024.repository;

import org.rail.spring2024.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByName(String name);

    void   deleteByName(String name);
}
