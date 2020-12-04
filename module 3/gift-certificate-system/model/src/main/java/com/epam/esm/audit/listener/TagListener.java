package com.epam.esm.audit.listener;

import com.epam.esm.audit.AuditAction;
import com.epam.esm.audit.entity.TagHistory;
import com.epam.esm.audit.entity.UserHistory;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

public class TagListener {
    @PersistenceContext
    private EntityManager entityManager;

    @PostPersist
    public void postPersist(Tag tag){
        insertIntoAuditTable(AuditAction.INSERT, tag);
    }

    @PreUpdate
    public void preUpdate(Tag tag) {
        insertIntoAuditTable(AuditAction.UPDATE, tag);
    }

    @PreRemove
    public void preRemove(Tag tag){
        insertIntoAuditTable(AuditAction.DELETE, tag);
    }

    private void insertIntoAuditTable(AuditAction action, Tag tag){
        entityManager.persist(new TagHistory(0, tag.getId(), tag.getName(), action));
    }
}
