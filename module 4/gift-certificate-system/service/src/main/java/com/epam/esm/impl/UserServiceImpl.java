package com.epam.esm.impl;

import com.epam.esm.UserRepository;
import com.epam.esm.UserService;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private static final String NOT_FOUND = "locale.message.UserNotFound";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User findById(long id) {
        User user = userRepository.findById(id);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException(NOT_FOUND,id);
        }
        return user;
    }

    @Override
    public List<User> getAll(Pagination pagination) {
        return userRepository.getAll(pagination);
    }

    @Override
    public User findByLogin(String login) {
        User user = userRepository.findByLogin(login);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException(NOT_FOUND,0);
        }
        return user;
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.create(user);
    }
}
