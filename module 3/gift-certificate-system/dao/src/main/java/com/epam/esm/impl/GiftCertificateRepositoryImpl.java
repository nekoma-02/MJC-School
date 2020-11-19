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

import java.util.List;
import java.util.Map;

@Repository
@PropertySource("classpath:sql_query_certificate.properties")
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String SELECT_CERTIFICATE = "SELECT_CERTIFICATE";
    private static final String SELECT_CERTIFICATE_BY_NAME = "SELECT_CERTIFICATE_BY_NAME";
    private static final String INSERT_TAG_TO_CERTIFICATE = "INSERT_TAG_TO_CERTIFICATE";

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Environment environment;

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        Session session = sessionFactory.getCurrentSession();
        long id = (Long) session.save(certificate);
        return session.get(GiftCertificate.class, id);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        GiftCertificate giftCertificate = session.get(GiftCertificate.class, id);
        session.delete(giftCertificate);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        return (GiftCertificate) sessionFactory.getCurrentSession().merge(giftCertificate);
    }

    @Override
    public GiftCertificate findById(long id) {
        return sessionFactory.getCurrentSession().get(GiftCertificate.class, id);
    }

    @Override
    public GiftCertificate findByName(String name) {
        return sessionFactory.getCurrentSession().createNativeQuery(environment.getProperty(SELECT_CERTIFICATE_BY_NAME), GiftCertificate.class)
                .setParameter(1, name).list().stream().findFirst().orElse(null);
    }

    @Override
    public void addTagToCertificate(List<Tag> tagList, long id) {
        Session session = sessionFactory.getCurrentSession();
        for (Tag tag : tagList) {
            session.createNativeQuery(environment.getProperty(INSERT_TAG_TO_CERTIFICATE))
                    .setParameter(1, tag.getName())
                    .setParameter(2, id)
                    .executeUpdate();
        }
    }

    @Override
    public List<GiftCertificate> getAll(Pagination pagination) {
        return sessionFactory.getCurrentSession().createNativeQuery(environment.getProperty(SELECT_CERTIFICATE), GiftCertificate.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .list();
    }

    @Override
    public List<GiftCertificate> filterCertificate(Map<String, String> filterParam) {
        SelectFilterCreator query = new SelectFilterCreator();
        return sessionFactory.getCurrentSession().createQuery(query.createFilterQuery(filterParam)).list();
    }

}
