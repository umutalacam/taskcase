package com.allybros.taskcase.security;

import com.allybros.taskcase.data.domain.User;
import com.allybros.taskcase.data.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class TaskCaseUserDetailService implements UserDetailsService {


    private final UserRepository userRepository;

    public TaskCaseUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // Load user details from JPA
        User user = userRepository.findUserByUsername(s);
        if (user == null) throw new UsernameNotFoundException("No user found with the given username");

        // Create user details
        return new TaskCaseUserDetail(user);
    }
}
