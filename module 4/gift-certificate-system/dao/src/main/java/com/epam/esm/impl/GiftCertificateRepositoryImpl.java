package com.epam.esm.impl;

import com.epam.esm.GiftCertificateRepository;
import com.epam.esm.entity.Pagination;
import com.epam.esm.util.SelectFilterCreator;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Repository
@PropertySource("classpath:sql_query_certificate.properties")
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String SELECT_CERTIFICATE = "SELECT_CERTIFICATE";
    private static final String SELECT_CERTIFICATE_BY_NAME = "SELECT_CERTIFICATE_BY_NAME";
    private static final String INSERT_TAG_TO_CERTIFICATE = "INSERT_TAG_TO_CERTIFICATE";
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private Environment environment;

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        entityManager.persist(certificate);
        return entityManager.find(GiftCertificate.class, certificate.getId());
    }

    @Override
    public void delete(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(giftCertificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return entityManager.merge(giftCertificate);
    }

    @Override
    public GiftCertificate findById(long id) {
        return entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public GiftCertificate findByName(String name) {
        return (GiftCertificate) entityManager.createNativeQuery(environment.getProperty(SELECT_CERTIFICATE_BY_NAME), GiftCertificate.class)
                .setParameter(1, name).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public void addTagToCertificate(List<Tag> tagList, long id) {
        for (Tag tag : tagList) {
            entityManager.createNativeQuery(environment.getProperty(INSERT_TAG_TO_CERTIFICATE))
                    .setParameter(1, tag.getId())
                    .setParameter(2, id)
                    .executeUpdate();
        }
    }

    @Override
    public List<GiftCertificate> getAll(Pagination pagination) {
        return entityManager.createNativeQuery(environment.getProperty(SELECT_CERTIFICATE), GiftCertificate.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

    @Override
    public List<GiftCertificate> filterCertificate(Map<String, String> filterParam, Pagination pagination) {
        SelectFilterCreator query = new SelectFilterCreator();
        return entityManager.createNativeQuery(
                query.createFilterQuery(filterParam), GiftCertificate.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }

}
