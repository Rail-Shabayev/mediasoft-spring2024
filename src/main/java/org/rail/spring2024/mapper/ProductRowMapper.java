package org.rail.spring2024.mapper;

import org.rail.spring2024.model.Product;
import org.rail.spring2024.model.ProductType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;


public class ProductRowMapper implements RowMapper<Product> {
    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getObject("uuid", UUID.class),
                rs.getString("name"),
                rs.getString("description"),
                ProductType.valueOf(rs.getString("type")),
                rs.getBigDecimal("price"),
                rs.getInt("quantity"),
                rs.getObject("date_quantity_updated", LocalDateTime.class),
                rs.getDate("date_created").toLocalDate());
    }
}

