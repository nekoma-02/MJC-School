package com.epam.esm.security.config;

import com.epam.esm.entity.Role;
import com.epam.esm.security.jwt.JwtConfigurer;
import com.epam.esm.security.jwt.JwtTokenProvider;
import com.epam.esm.security.util.DeniedAccessHandler;
import com.epam.esm.security.util.SecurityEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    private SecurityEntryPoint securityEntryPoint;
    @Autowired
    private DeniedAccessHandler deniedAccessHandler;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/users/login").permitAll()
                .antMatchers(HttpMethod.GET,"/tags").permitAll()
                .antMatchers(HttpMethod.GET,"/tags/{\\d+}").permitAll()
                .antMatchers(HttpMethod.DELETE,"/tags/{\\d+}").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/tags").hasRole(Role.ADMIN.name())
                .antMatchers("/users/create").permitAll()
                .antMatchers("/users/{id}").
                    access("@userSecurity.hasUserId(authentication, #id) or hasRole('" + Role.ADMIN.name() + "')")
                .antMatchers(HttpMethod.GET,"/users").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/certificates").permitAll()
                .antMatchers(HttpMethod.POST,"/certificates").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.POST,"/certificates/binding/{\\d+}").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET,"/certificates/{\\d+}").permitAll()
                .antMatchers(HttpMethod.PATCH,"/certificates/{\\d+}").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.DELETE,"/certificates/{\\d+}").hasRole(Role.ADMIN.name())
                .antMatchers(HttpMethod.GET, "/users/{userId}/order/{\\d+}", "/users/{userId}/orders")
                    .access("@userSecurity.hasUserId(authentication, #userId) or hasRole('" + Role.ADMIN.name() + "')")
                .antMatchers(HttpMethod.POST, "/users/{userId}/orders")
                    .access("@userSecurity.hasUserId(authentication, #userId) or hasRole('" + Role.ADMIN.name() + "')")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

        http.exceptionHandling().authenticationEntryPoint(securityEntryPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(deniedAccessHandler);
    }
}
