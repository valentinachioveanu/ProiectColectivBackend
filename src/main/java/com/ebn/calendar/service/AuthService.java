package com.ebn.calendar.service;

import com.ebn.calendar.model.dao.User;
import com.ebn.calendar.repository.UserRepository;
import com.ebn.calendar.security.AuthUserDetails;
import com.ebn.calendar.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public User create(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(List.of("ROLE_USER"));
        return userRepository.create(user);
    }

    public User readByUsername(String username) {
        return userRepository.readByUsername(username);
    }

    public String authenticate(User user) {
        user.setPassword(encoder.encode(user.getPassword()));

        User foundUser = userRepository.readByUsername(user.getUsername());
        if (foundUser == null || !foundUser.getPassword().equals(user.getPassword())) {
            return null;
        }
        user.setRoles(foundUser.getRoles());
        return jwtUtils.generateToken(user.getUsername());
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
