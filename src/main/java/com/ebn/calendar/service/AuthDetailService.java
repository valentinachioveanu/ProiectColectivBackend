package com.ebn.calendar.service;

import com.ebn.calendar.model.dao.User;
import com.ebn.calendar.repository.UserRepository;
import com.ebn.calendar.security.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AuthDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.readByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return AuthUserDetails.build(user);
    }
}
