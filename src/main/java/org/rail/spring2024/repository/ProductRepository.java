package org.rail.spring2024.repository;

import org.rail.spring2024.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * ProductRepository interface for persisting data into db.
 */
public interface ProductRepository extends JpaRepository<Product, UUID> {
    /**
     * Finds product object by specified name
     * @param name product name to find
     * @return Optional<Product> object
     */
    Optional<Product> findByName(String name);

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Product> findAll();
}
