package com.epam.esm.impl;

import com.epam.esm.UserRepository;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sql_query_user.properties")
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment environment;

    private static final String SELECT_USER = "SELECT_USER";
    private static final String SELECT_USER_BY_ID = "SELECT_USER_BY_ID";

    @Override
    public Optional<User> findById(long id) {
        User user = DataAccessUtils.singleResult(jdbcTemplate.query(environment.getProperty(SELECT_USER_BY_ID), new UserRowMapper(), id));
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll(Pagination pagination) {
        return jdbcTemplate.query(environment.getProperty(SELECT_USER), new UserRowMapper(), pagination.getLimit(), pagination.getOffset());
    }
}
