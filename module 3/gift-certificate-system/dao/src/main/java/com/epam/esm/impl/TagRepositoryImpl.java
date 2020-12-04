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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sql_query_tag.properties")
public class TagRepositoryImpl implements TagRepository {
    private static final String SELECT_TAG = "SELECT_TAG";
    private static final String SELECT_TAG_BY_NAME = "SELECT_TAG_BY_NAME";
    @Autowired
    private Environment environment;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag create(Tag tag) {
        entityManager.persist(tag);
        return entityManager.find(Tag.class, tag.getId());
    }

    @Override
    public void delete(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }

    @Override
    public Tag findById(long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public Tag findByName(String name) {
        return (Tag) entityManager.createNativeQuery(environment.getProperty(SELECT_TAG_BY_NAME), Tag.class)
                .setParameter(1, name).getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<Tag> getAll(Pagination pagination) {
        return entityManager.createNativeQuery(environment.getProperty(SELECT_TAG), Tag.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .getResultList();
    }
}
