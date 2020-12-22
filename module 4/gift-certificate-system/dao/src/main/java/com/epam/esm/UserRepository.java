package com.epam.esm;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserRepository {

    User findById(long id);

    List<User> getAll(Pagination pagination);

    User findByLogin(String login);

    User create(User user);
}
