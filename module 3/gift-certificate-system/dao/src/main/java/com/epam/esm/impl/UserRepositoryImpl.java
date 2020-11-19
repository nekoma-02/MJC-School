package com.epam.esm.impl;

import com.epam.esm.UserRepository;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySource("classpath:sql_query_user.properties")
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private Environment environment;

    private static final String SELECT_USER = "SELECT_USER";

    @Override
    public User findById(long id) {
        return sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public List<User> getAll(Pagination pagination) {
        return sessionFactory.getCurrentSession().createNativeQuery(environment.getProperty(SELECT_USER),User.class)
                .setFirstResult(pagination.getOffset())
                .setMaxResults(pagination.getLimit())
                .list();
    }
}
