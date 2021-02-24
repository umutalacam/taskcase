package com.allybros.taskcase.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfigurationManager extends WebSecurityConfigurerAdapter {

    private static final String STD_USER = "STD_USER";
    private static final String ADMIN = "ADMIN";

    @Autowired
    private TaskCaseUserDetailService taskCaseUserDetailService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(taskCaseUserDetailService);
        /**
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("1234")
                .roles(STD_USER)
                .and()
                .withUser("admin")
                .password("1234")
                .roles(STD_USER, ADMIN);
         **/
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/dashboard").hasRole(ADMIN)
                .antMatchers("/tasks/**").hasAnyRole(STD_USER, ADMIN).and().formLogin();

        // For h2 console
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
