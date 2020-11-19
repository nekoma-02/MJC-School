package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;

import java.util.List;

public interface OrderRepository {
    List<Order> findByUserId(long userId, Pagination pagination);

    Order findByUserId(long userId, long orderId);

    long createOrder(Order order);

    void addCertificateToOrder(List<GiftCertificate> certificateList, long orderId);
}
