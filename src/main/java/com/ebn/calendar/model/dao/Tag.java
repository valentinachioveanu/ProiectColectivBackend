package com.ebn.calendar.model.dao;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import javax.validation.constraints.Size;

@Entity
@Table(name = "tags")
public class Tag implements Identifiable<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", nullable = false, unique = true, length = 36)
    private String id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "color_code", nullable = false, length = 6)
    @Size(min = 6, max = 6)
    private String colorCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    public Tag() {
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

    public String getName() {
        return name;
    }

    public void setName(String title) {
        this.name = title;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
