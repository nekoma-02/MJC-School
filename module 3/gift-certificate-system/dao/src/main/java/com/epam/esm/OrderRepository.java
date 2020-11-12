package com.epam.esm;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<Order> findByUserId(long userId, Pagination pagination);

    Optional<Order> findByUserId(long userId, long orderId);

    long createOrder(Order order, long userId);

    void addCertificateToOrder(List<GiftCertificate> certificateList, long orderId);
}
