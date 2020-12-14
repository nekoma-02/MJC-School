package com.epam.esm.audit.listener;

import com.epam.esm.audit.AuditAction;
import com.epam.esm.audit.entity.GiftCertificateHistory;
import com.epam.esm.entity.GiftCertificate;

import javax.persistence.*;

public class GiftCertificateListener {
    @PersistenceContext
    private EntityManager entityManager;

    @PostPersist
    public void postPersist(GiftCertificate certificate){
        insertIntoAuditTable(AuditAction.INSERT, certificate);
    }

    @PreUpdate
    public void preUpdate(GiftCertificate certificate) {
        insertIntoAuditTable(AuditAction.UPDATE, certificate);
    }

    @PreRemove
    public void preRemove(GiftCertificate certificate){
        insertIntoAuditTable(AuditAction.DELETE, certificate);
    }

    private void insertIntoAuditTable(AuditAction action, GiftCertificate certificate){
        entityManager.persist(new GiftCertificateHistory(0,
                certificate.getId(),
                certificate.getName(),
                certificate.getDescription(),
                certificate.getPrice(),
                certificate.getCreateDate(),
                certificate.getCreateDateTimeZone(),
                certificate.getLastUpdateDate(),
                certificate.getLastUpdateDateTimeZone(),
                certificate.getDuration(),
                action));
    }

}
