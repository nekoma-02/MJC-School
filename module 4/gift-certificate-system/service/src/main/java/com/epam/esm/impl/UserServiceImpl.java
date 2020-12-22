package com.epam.esm.impl;

import com.epam.esm.UserRepository;
import com.epam.esm.UserService;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private static final String NOT_FOUND = "locale.message.UserNotFound";
    private static final String CONFLICT_EXCEPTION = "locale.message.UserConflict";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto findById(long id) {
        User user = userRepository.findById(id);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException(NOT_FOUND,id);
        }
        return UserDto.fromUser(user);
    }

    @Override
    public List<UserDto> getAll(Pagination pagination) {
        List<User> users = userRepository.getAll(pagination);
        List<UserDto>  usersDto = users.stream().map(u -> UserDto.fromUser(u)).collect(Collectors.toList());
        return usersDto;
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
    @Transactional
    public UserDto create(User user) {
        if (userRepository.findByLogin(user.getLogin()) != null) {
            throw new EntityNotFoundException(CONFLICT_EXCEPTION, 0);
        }
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserDto.fromUser(userRepository.create(user));
    }
}
