package org.rail.spring2024.dao;

import jakarta.transaction.Transactional;
import org.rail.spring2024.mapper.ProductRowMapper;
import org.rail.spring2024.model.Product;
import org.rail.spring2024.model.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Repository
public class ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> findAll() {
        String query = "SELECT * FROM product";
        return jdbcTemplate.query(query, new ProductRowMapper());
    }


    @Transactional
    public void saveAll(List<Product> products) {
        jdbcTemplate.batchUpdate("update product set name = ?, description = ?, type = ?, price = ?, quantity = ?, date_quantity_updated = ?, date_created = ? " +
                " where uuid = ?", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Product product = products.get(i);
                ps.setString(1, product.getName());
                ps.setString(2, product.getDescription());
                ps.setString(3, String.valueOf(product.getType()));
                ps.setBigDecimal(4, product.getPrice());
                ps.setInt(5, product.getQuantity());
                ps.setObject(6, (LocalDateTime) product.getDateQuantityUpdated());
                ps.setObject(7, (LocalDate) product.getDateCreated());
                ps.setObject(8, (UUID) product.getUuid());
            }

            @Override
            public int getBatchSize() {
                return products.size();
            }
        });
    }
}
