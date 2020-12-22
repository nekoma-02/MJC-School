package com.epam.esm;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;

import java.util.List;

public interface UserService {
    UserDto findById(long id);

    List<UserDto> getAll(Pagination pagination);

    User findByLogin(String login);

    UserDto create(User user);
}
