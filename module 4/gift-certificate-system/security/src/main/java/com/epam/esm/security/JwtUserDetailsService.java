package com.epam.esm.security;

import com.epam.esm.UserService;
import com.epam.esm.entity.User;
import com.epam.esm.security.jwt.JwtUser;
import com.epam.esm.security.jwt.JwtUserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {
    private static final String EXCEPTION_KEY = "exception.auth.not_authorized";
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(EXCEPTION_KEY);
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        return jwtUser;
    }
}
