package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.dao.mapper.TagRowMapper;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepositoryImpl implements TagRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_TAG = "insert into Tag (name) value (?)";
    private static final String SELECT_TAG_BY_NAME = "select * from Tag where Name = ?";
    private static final String SELECT_TAG = "select * from Tag";
    private static final String SELECT_TAG_BY_ID = "select * from Tag where Id = ?";
    private static final String REMOVE_TAG_BY_NAME = "delete from Tag where Name = ?";

    @Override
    public boolean create(Tag tag) {
        return jdbcTemplate.update(INSERT_TAG, tag.getName()) == 1 ? true : false;
    }

    @Override
    public boolean delete(String name) {
        return jdbcTemplate.update(REMOVE_TAG_BY_NAME, name) == 1 ? true : false;
    }

    @Override
    public Optional<Tag> findById(long id) {
        Tag tag = DataAccessUtils.singleResult(jdbcTemplate.query(SELECT_TAG_BY_ID, new TagRowMapper(), id));
        return Optional.ofNullable(tag);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Tag tag = DataAccessUtils.singleResult(jdbcTemplate.query(SELECT_TAG_BY_NAME, new TagRowMapper(), name));
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(SELECT_TAG, new TagRowMapper());
    }



}
