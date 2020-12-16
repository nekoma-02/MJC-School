package com.epam.esm.audit.listener;

import com.epam.esm.audit.AuditAction;
import com.epam.esm.audit.entity.OrderHistory;
import com.epam.esm.audit.entity.TagHistory;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.transaction.Transactional;

public class OrderListener {
    @PersistenceContext
    private EntityManager entityManager;

    @PostPersist
    public void postPersist(Order order){
        insertIntoAuditTable(AuditAction.INSERT, order);
    }

    @PreUpdate
    public void preUpdate(Order order) {
        insertIntoAuditTable(AuditAction.UPDATE, order);
    }

    @PreRemove
    public void preRemove(Order order){
        insertIntoAuditTable(AuditAction.DELETE, order);
    }

    private void insertIntoAuditTable(AuditAction action, Order order){
        entityManager.persist(new OrderHistory(0,
                order.getId(),
                order.getOrderDate(),
                order.getOrderDateTimeZone(),
                order.getCost(),
                order.getUserId(),
                action));
    }
}
