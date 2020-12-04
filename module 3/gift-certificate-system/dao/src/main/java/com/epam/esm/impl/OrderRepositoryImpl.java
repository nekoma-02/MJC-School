package com.epam.esm.impl;

import com.epam.esm.OrderRepository;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@PropertySource("classpath:sql_query_order.properties")
public class OrderRepositoryImpl implements OrderRepository {
    private static final String SELECT_ALL_ORDERS_BY_USER_ID = "SELECT_ALL_ORDERS_BY_USER_ID";
    private static final String SELECT_USER_ORDER_BY_ID = "SELECT_USER_ORDER_BY_ID";
    @Autowired
    private Environment environment;
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<Order> findByUserId(long userId, Pagination pagination) {
        return entityManager.createNativeQuery(environment.getProperty(SELECT_ALL_ORDERS_BY_USER_ID), Order.class)
                .setParameter(1, userId)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

    @Override
    public Order findByUserId(long userId, long orderId) {
        return (Order) entityManager.createNativeQuery(environment.getProperty(SELECT_USER_ORDER_BY_ID), Order.class)
                .setParameter(1, userId).setParameter(2, orderId).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public Order createOrder(Order order) {
        entityManager.persist(order);
        return entityManager.find(Order.class, order.getId());
    }
}
