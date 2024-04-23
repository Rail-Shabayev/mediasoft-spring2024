package org.rail.spring2024.dto.filterDto;

import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.Setter;
import org.rail.spring2024.model.Product;

import java.math.BigDecimal;

@Getter
@Setter
public class PriceCriteria extends ProductFilterDto {
    private BigDecimal value;

    @Override
    public Predicate getPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        BigDecimal fieldValue = this.getValue();
        final Expression<BigDecimal> expression = root.get(this.getField());
        System.out.println(getField() + " in product " + getValue());
        return switch (this.getOperation()) {
            default -> throw new RuntimeException("some error in operation");
            case "<=", "LESS_THAN_OR_EQ" -> criteriaBuilder.lessThanOrEqualTo(expression,
                    fieldValue);
            case ">=", "GRATER_THAN_OR_EQ" -> criteriaBuilder.greaterThanOrEqualTo(expression,
                    fieldValue);
            case "~", "LIKE" -> criteriaBuilder.like(expression.as(String.class), "%" + fieldValue + "%");
            case "=", "EQUALS" -> criteriaBuilder.equal(expression, fieldValue);
        };
    }
}
