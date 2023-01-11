package com.ebn.calendar.model.dao;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
public class User extends Identifiable<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, unique = true, length = 36)
    private String id;

    @Column(name = "username", nullable = false, unique = true, length = 20)
    @Size(min = 3, max = 20)
    private String username;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role")
    private List<String> roles;

    public User() {
    }

    @Override
    public String getIdentifier() {
        return getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
