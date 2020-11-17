package com.epam.esm.mapper;

import com.epam.esm.entity.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class OrderRowMapper implements RowMapper<Order> {

    private static final String ORDER_DATE = "OrderDate";
    private static final String TIME_ZONE_ORDER_DATE = "TimeZone_OrderDate";
    private static final String ID = "id";
    private static final String COST = "Cost";

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDateTime orderDate = rs.getTimestamp(ORDER_DATE).toLocalDateTime();
        ZoneId timeZoneOrderDate = ZoneId.of(rs.getString(TIME_ZONE_ORDER_DATE));
        return Order.builder()
                .id(rs.getLong(ID))
                .cost(rs.getBigDecimal(COST))
                .orderDate(ZonedDateTime.of(orderDate,timeZoneOrderDate))
                .build();
    }
}
