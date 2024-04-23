package org.rail.spring2024.dto.filterDto;

import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.Setter;
import org.rail.spring2024.model.Product;

@Getter
@Setter
public class StringCriteria extends ProductFilterDto {
    private String value;

    @Override
    public Predicate getPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Object fieldValue = this.getValue();
        final Expression<String> expression = root.get(this.getField());
        return switch (this.getOperation()) {
            default -> throw new RuntimeException("some error in operation");
            case "<=", "LESS_THAN_OR_EQ" -> criteriaBuilder.like(expression,
                    "%" + fieldValue);
            case ">=", "GRATER_THAN_OR_EQ" -> criteriaBuilder.like(expression,
                     fieldValue + "%");
            case "~", "LIKE" -> criteriaBuilder.like(expression.as(String.class), "%" + fieldValue + "%");
            case "=", "EQUALS" -> criteriaBuilder.equal(expression, fieldValue);
        };
    }

}
