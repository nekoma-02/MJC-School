package com.epam.esm.controller;

import com.epam.esm.UserService;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<User> getAllUsers(Pagination pagination) {
        return service.getAll(pagination);
    }

}
