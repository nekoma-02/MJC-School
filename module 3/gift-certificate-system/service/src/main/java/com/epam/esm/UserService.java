package com.epam.esm;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserService {
    User findById(long id);

    List<User> getAll(Pagination pagination);
}
