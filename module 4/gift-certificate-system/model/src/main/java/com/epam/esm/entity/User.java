package com.epam.esm.entity;

import com.epam.esm.audit.listener.UserListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(UserListener.class)
@Table(name = "User")
public class User extends RepresentationModel<User> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank
    private String name;
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private Role role;

}
