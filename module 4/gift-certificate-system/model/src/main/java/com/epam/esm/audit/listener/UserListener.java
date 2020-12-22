package com.epam.esm.audit.listener;

import com.epam.esm.audit.AuditAction;
import com.epam.esm.audit.entity.UserHistory;
import com.epam.esm.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

public class UserListener {
    @PersistenceContext
    private EntityManager entityManager;

    @PostPersist
    public void postPersist(User user){
        insertIntoAuditTable(AuditAction.INSERT, user);
    }

    @PreUpdate
    public void preUpdate(User user) {
        insertIntoAuditTable(AuditAction.UPDATE, user);
    }

    @PreRemove
    public void preRemove(User user){
        insertIntoAuditTable(AuditAction.DELETE, user);
    }

    private void insertIntoAuditTable(AuditAction action, User user){
        entityManager.persist(new UserHistory(0,
                user.getId(),
                user.getName(),
                action,
                user.getLogin(),
                user.getPassword(),
                user.getRole()));
    }
}
