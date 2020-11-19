package com.epam.esm.impl;

import com.epam.esm.TagRepository;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sql_query_tag.properties")
public class TagRepositoryImpl implements TagRepository {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Environment environment;

    private static final String SELECT_TAG = "SELECT_TAG";
    private static final String SELECT_TAG_BY_NAME = "SELECT_TAG_BY_NAME";

    @Override
    public Tag create(Tag tag) {
        Session session = sessionFactory.getCurrentSession();
        long id = (Long) session.save(tag);
        return session.get(Tag.class, id);
    }

    @Override
    public void delete(long id) {
        Session session = sessionFactory.getCurrentSession();
        Tag tag = session.get(Tag.class, id);
        session.delete(tag);
    }

    @Override
    public Tag findById(long id) {
        return sessionFactory.getCurrentSession().get(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        return sessionFactory.getCurrentSession().createNativeQuery(environment.getProperty(SELECT_TAG_BY_NAME), Tag.class)
                .setParameter(1, name).list().stream().findFirst().orElse(null);
    }

    @Override
    public List<Tag> getAll(Pagination pagination) {
        return sessionFactory.getCurrentSession().createNativeQuery(environment.getProperty(SELECT_TAG), Tag.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .list();
    }

}
