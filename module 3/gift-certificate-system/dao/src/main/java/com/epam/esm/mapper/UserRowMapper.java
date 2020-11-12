package com.epam.esm.mapper;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    private static final String ID = "id";
    private static final String NAME = "name";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong(ID);
        String name = rs.getString(NAME);
        return new User(id, name);
    }
}
