package com.epam.esm.impl;

import com.epam.esm.GiftCertificateRepository;
import com.epam.esm.OrderRepository;
import com.epam.esm.OrderService;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String NOT_FOUND = "locale.message.OrderNotFound";
    private static final String CERTIFICATE_NOT_FOUND = "locale.message.CertificateNotFound";
    @Autowired
    private OrderRepository repo;
    @Autowired
    private GiftCertificateRepository certificateRepo;

    @Override
    public List<Order> findByUserId(long userId, Pagination pagination) {
        List<Order> orderList = repo.findByUserId(userId, pagination);
        if (orderList == null || orderList.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND, userId);
        }
        return orderList;
    }

    @Override
    public Order findByUserId(long userId, long orderId) {
        return repo.findByUserId(userId, orderId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND, userId));
    }

    @Override
    @Transactional
    public Order createOrder(OrderDTO orderDTO, long userId) {
        ModelMapper modelMapper = new ModelMapper();
        orderDTO.setOrderDate(ZonedDateTime.now());
        Order order = modelMapper.map(orderDTO, Order.class);
        List<GiftCertificate> certificateList = new ArrayList<>();
        orderDTO.getCertificateList().forEach(e -> certificateList.add(
                certificateRepo.findById(e.getId()).orElseThrow(() -> new EntityNotFoundException(CERTIFICATE_NOT_FOUND,e.getId()))));
        order.setCost(orderPrice(certificateList));
        long orderId = repo.createOrder(order, userId);
        repo.addCertificateToOrder(certificateList,orderId);
        return repo.findByUserId(userId,orderId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND, userId));
    }

    private BigDecimal orderPrice(List<GiftCertificate> certificateList) {
        return certificateList.stream().map(GiftCertificate::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
