package com.ebn.calendar.service;

import com.ebn.calendar.model.dao.User;
import com.ebn.calendar.repository.UserRepository;
import com.ebn.calendar.security.AuthUserDetails;
import com.ebn.calendar.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthService{

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    private final JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder encoder, JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
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
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtUtils.generateToken(authentication);

            AuthUserDetails userDetails = (AuthUserDetails) authentication.getPrincipal();

            user.setId(userDetails.getId());
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
            user.setRoles(roles);

            return token;
        }catch (Exception e){
            return null;
        }
    }
}
