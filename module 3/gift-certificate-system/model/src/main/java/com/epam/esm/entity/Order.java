package com.epam.esm.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Data
@JsonDeserialize(builder = Order.OrderBuilder.class)
@Builder(builderClassName = "OrderBuilder")
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private long id;
    private ZonedDateTime orderDate;
    private BigDecimal cost;

}
