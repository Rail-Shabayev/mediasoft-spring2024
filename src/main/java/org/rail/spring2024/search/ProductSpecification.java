package org.rail.spring2024.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.rail.spring2024.dto.filterDto.ProductFilterDto;
import org.rail.spring2024.model.Product;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification implements Specification<Product> {

    private final ProductFilterDto productFilterDto;

    public ProductSpecification(ProductFilterDto productFilterDto) {
        this.productFilterDto = productFilterDto;
    }

    @Override
    public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return productFilterDto.getPredicate(root, query, criteriaBuilder);
    }
}
