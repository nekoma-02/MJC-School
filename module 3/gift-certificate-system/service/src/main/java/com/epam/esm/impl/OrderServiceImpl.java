package com.epam.esm.impl;

import com.epam.esm.GiftCertificateRepository;
import com.epam.esm.OrderRepository;
import com.epam.esm.OrderService;
import com.epam.esm.UserRepository;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String NOT_FOUND = "locale.message.OrderNotFound";
    private static final String CERTIFICATE_NOT_FOUND = "locale.message.CertificateNotFound";
    private static final String USER_NOT_FOUND = "locale.message.UserNotFound";
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GiftCertificateRepository certificateRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Order> findByUserId(long userId, Pagination pagination) {
        List<Order> orderList = orderRepository.findByUserId(userId, pagination);
        if (orderList == null || orderList.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND, userId);
        }
        return orderList;
    }

    @Override
    public Order findByUserId(long userId, long orderId) {
        Order order = orderRepository.findByUserId(userId, orderId);
        if (Objects.isNull(order)) {
            throw new  EntityNotFoundException(NOT_FOUND, userId);
        }
        return order;
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        order.getCertificateList().stream().filter(o -> isGiftCertificateExist(o.getId()));
        long userId = order.getUser().getId();
        isUserExist(userId);
        long orderId = orderRepository.createOrder(order);
        orderRepository.addCertificateToOrder(order.getCertificateList(),orderId);
        ZonedDateTime dateTime = ZonedDateTime.now();
        order.setOrderDate(dateTime.toLocalDateTime());
        order.setOrderDateTimeZone(dateTime.getZone());
        order.setCost(orderPrice(order.getCertificateList()));
        return orderRepository.findByUserId(userId, orderId);
    }

    private BigDecimal orderPrice(List<GiftCertificate> certificateList) {
        return certificateList.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private boolean isGiftCertificateExist(long id) {
        if (Objects.isNull(certificateRepository.findById(id))) {
            throw new EntityNotFoundException(CERTIFICATE_NOT_FOUND, id);
        }
        return true;
    }

    private boolean isUserExist(long id) {
        if (Objects.isNull(userRepository.findById(id))) {
            throw new EntityNotFoundException(USER_NOT_FOUND, id);
        }
        return true;
    }
}
