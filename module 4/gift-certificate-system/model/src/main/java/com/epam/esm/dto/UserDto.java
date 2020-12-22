package com.epam.esm.dto;

import com.epam.esm.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto extends RepresentationModel<UserDto> {
    private Long id;
    private String username;
    private String name;

    public User toUser(){
        User user = new User();
        user.setId(id);
        user.setLogin(username);
        user.setName(name);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getLogin());
        userDto.setName(user.getName());

        return userDto;
    }
}
