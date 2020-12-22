package com.epam.esm;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface OrderService {
    List<Order> findByUserId(long userId, Pagination pagination);

    Order findByUserId(long userId, long orderId);

    Order createOrder(Order order, long userId);
}
