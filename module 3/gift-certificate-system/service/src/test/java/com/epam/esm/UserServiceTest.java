package com.epam.esm;

import com.epam.esm.config.SpringConfig;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.EntityNotFoundException;
import com.epam.esm.impl.TagServiceImpl;
import com.epam.esm.impl.UserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringJUnitConfig(SpringConfig.class)
@SpringBootTest
@EnableConfigurationProperties
@ActiveProfiles("test")
public class UserServiceTest {
    private List<User> userList;
    private User user1;
    private User user2;
    private Pagination pagination;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @BeforeEach
    public void setUp() {
        pagination = new Pagination(5, 0);
        user1 = new User(1, "Alex");
        user2 = new User(2, "Fedya");
        userList = Arrays.asList(user1, user2);
    }

    @Test
    public void getAllUsers() {
        Mockito.when(userRepository.getAll(pagination)).thenReturn(userList);
        Assertions.assertIterableEquals(userList, userService.getAll(pagination));
    }

    @Test
    public void findUserById_whenTagExist_thenReturnUser() {
        long tagId = 1;
        Mockito.when(userRepository.findById(tagId)).thenReturn(user1);
        User expected = user1;
        User actual = userService.findById(tagId);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findUserById_whenUserNotExisting_thenUserNotFoundException() {
        long tagId = 0;
        Mockito.when(userRepository.findById(tagId)).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.findById(tagId));
    }

}
