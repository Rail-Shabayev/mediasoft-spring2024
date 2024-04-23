package org.rail.spring2024.dto.filterDto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import lombok.Setter;
import org.rail.spring2024.model.Product;

@Getter
@Setter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        property = "field",
        visible = true,
        include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StringCriteria.class, names = {"name", "description"}),
        @JsonSubTypes.Type(value = DateCriteria.class, names = {"dateQuantityUpdated", "dateCreated"}),
        @JsonSubTypes.Type(value = ProductFilterDto.class, names = {"quantity"}),
        @JsonSubTypes.Type(value = PriceCriteria.class, names = {"price"})
})
public class ProductFilterDto {
    private String field;
    private String operation;
    private Object value;

    public Predicate getPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Object fieldValue = this.getValue();
        final Expression<String> expression = root.get(this.getField()).as(String.class);
        return switch (this.getOperation()) {
            default -> throw new RuntimeException("some error in operation");
            case "<=", "LESS_THAN_OR_EQ" -> criteriaBuilder.lessThanOrEqualTo(expression,
                    fieldValue.toString());
            case ">=", "GRATER_THAN_OR_EQ" -> criteriaBuilder.greaterThanOrEqualTo(expression,
                    fieldValue.toString());
            case "~", "LIKE" -> criteriaBuilder.like(expression.as(String.class), "%" + fieldValue + "%");
            case "=", "EQUALS" -> criteriaBuilder.equal(expression, fieldValue);
        };
    }
}
