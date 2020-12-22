package com.epam.esm.controller;

import com.epam.esm.UserService;
import com.epam.esm.dto.AuthenticationRequestDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.JwtResponse;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL_FORMS)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        UserDto user = userService.findById(id);
        user.add(linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel());
        return user;
    }

    @GetMapping
    public List<UserDto> getAllUsers(@Valid Pagination pagination) {
        List<UserDto> userList = userService.getAll(pagination);
        userList.stream().forEach(user -> user.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel()));
        return userList;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            User user = userService.findByLogin(username);
            String token = tokenProvider.createToken(username);
            JwtResponse jwtResponse = new JwtResponse(user.getId(), user.getName(), user.getLogin(), token);
            return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestBody User user) {
        UserDto createdUser = userService.create(user);
        return ResponseEntity.ok(createdUser);
    }

}
