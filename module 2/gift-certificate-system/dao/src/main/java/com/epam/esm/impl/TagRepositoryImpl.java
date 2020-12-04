package com.epam.esm.impl;

import com.epam.esm.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@PropertySource("classpath:sql_query_tag.properties")
public class TagRepositoryImpl implements TagRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private Environment environment;

    private static final String INSERT_TAG = "INSERT_TAG";
    private static final String SELECT_TAG_BY_NAME = "SELECT_TAG_BY_NAME";
    private static final String SELECT_TAG = "SELECT_TAG";
    private static final String SELECT_TAG_BY_ID = "SELECT_TAG_BY_ID";
    private static final String REMOVE_TAG_BY_NAME = "REMOVE_TAG_BY_NAME";

    @Override
    public Optional<Tag> create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(environment.getProperty(INSERT_TAG), new String[]{"id"});
                    ps.setString(1, tag.getName());
                    return ps;
                },
                keyHolder);
        return getTagByKeyHolder(keyHolder);
    }

    @Override
    public boolean delete(String name) {
        return jdbcTemplate.update(environment.getProperty(REMOVE_TAG_BY_NAME), name) == 1 ? true : false;
    }

    @Override
    public Optional<Tag> findById(long id) {
        Tag tag = DataAccessUtils.singleResult(jdbcTemplate.query(environment.getProperty(SELECT_TAG_BY_ID), new TagRowMapper(), id));
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Tag tag = DataAccessUtils.singleResult(jdbcTemplate.query(environment.getProperty(SELECT_TAG_BY_NAME), new TagRowMapper(), name));
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(environment.getProperty(SELECT_TAG), new TagRowMapper());
    }

    private Optional<Tag> getTagByKeyHolder(KeyHolder keyHolder) {
        Number id = keyHolder.getKey();
        return id != null ? findById(id.longValue()) : Optional.empty();
    }

}
