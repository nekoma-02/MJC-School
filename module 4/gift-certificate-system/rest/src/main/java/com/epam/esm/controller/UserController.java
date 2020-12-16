package com.epam.esm.controller;

import com.epam.esm.UserService;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.afford;

import java.util.List;

@RestController
@RequestMapping("/users")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    public User getUserById(@PathVariable long id) {
        User user = service.findById(id);
        user.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        return user;
    }

    @GetMapping
    public List<User> getAllUsers(@Valid Pagination pagination) {
        List<User> userList = service.getAll(pagination);
        userList.stream().forEach(user -> user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()));
        return userList;
    }

}
