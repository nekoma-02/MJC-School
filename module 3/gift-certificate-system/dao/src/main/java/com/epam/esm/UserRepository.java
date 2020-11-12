package com.epam.esm;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(long id);

    List<User> getAll(Pagination pagination);
}
