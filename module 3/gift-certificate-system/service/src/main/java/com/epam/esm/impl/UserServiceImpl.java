package com.epam.esm.impl;

import com.epam.esm.UserRepository;
import com.epam.esm.UserService;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final String NOT_FOUND = "locale.message.UserNotFound";

    @Autowired
    private UserRepository repo;

    @Override
    public User findById(long id) {
        Optional<User> tag = repo.findById(id);
        if (!tag.isPresent()) {
            throw new EntityNotFoundException(NOT_FOUND,id);
        }
        return tag.get();
    }

    @Override
    public List<User> getAll(Pagination pagination) {
        return repo.getAll(pagination);
    }
}
