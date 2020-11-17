package com.epam.esm.impl;

import com.epam.esm.OrderRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.mapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sql_query_order.properties")
public class OrderRepositoryImpl implements OrderRepository {

    private static final String ID = "id";
    private static final String INSERT_ORDER_TO_CERTIFICATE = "INSERT_ORDER_TO_CERTIFICATE";
    private static final String INSERT_ORDER = "INSERT_ORDER";
    private static final String SELECT_ALL_ORDERS_BY_USER_ID = "SELECT_ALL_ORDERS_BY_USER_ID";
    private static final String SELECT_USER_ORDER_BY_ID = "SELECT_USER_ORDER_BY_ID";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment environment;

    @Override
    public List<Order> findByUserId(long userId, Pagination pagination) {
        return jdbcTemplate.query(environment.getProperty(SELECT_ALL_ORDERS_BY_USER_ID), new OrderRowMapper(), userId, pagination.getLimit(), pagination.getOffset());
    }

    @Override
    public Optional<Order> findByUserId(long userId, long orderId) {
        Order order = DataAccessUtils.singleResult(jdbcTemplate.query(environment.getProperty(SELECT_USER_ORDER_BY_ID), new OrderRowMapper(), userId, orderId));
        return Optional.ofNullable(order);
    }

    @Override
    public long createOrder(Order order, long userId) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(environment.getProperty(INSERT_ORDER), new String[]{ID});
                    ps.setLong(1, userId);
                    ps.setTimestamp(2, Timestamp.valueOf(order.getOrderDate().toLocalDateTime()));
                    ps.setString(3, order.getOrderDate().getZone().toString());
                    ps.setBigDecimal(4, order.getCost());
                    return ps;
                },
                keyHolder);
        Number numberKey = keyHolder.getKey();
        return numberKey == null ? 0 : numberKey.longValue();
    }

    @Override
    public void addCertificateToOrder(List<GiftCertificate> certificateList, long orderId) {
        jdbcTemplate.batchUpdate(environment.getProperty(INSERT_ORDER_TO_CERTIFICATE), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, orderId);
                ps.setLong(2, certificateList.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return certificateList.size();
            }
        });
    }

}
