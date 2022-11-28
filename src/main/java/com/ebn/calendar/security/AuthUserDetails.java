package com.ebn.calendar.security;

import com.ebn.calendar.model.dao.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AuthUserDetails implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;

    private final String username;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public AuthUserDetails(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static AuthUserDetails build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new AuthUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),authorities);
    }

    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuthUserDetails user = (AuthUserDetails) o;
        return Objects.equals(id, user.id);
    }
}

