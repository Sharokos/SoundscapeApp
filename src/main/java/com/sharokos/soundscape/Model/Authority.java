package com.sharokos.soundscape.Model;

import jakarta.persistence.*;


@Entity
@Table(name = "authorities")
public class Authority {

    @EmbeddedId
    private AuthorityId id;

    // Constructors, getters, and setters

    public Authority() {
    }

    public AuthorityId getId() {
        return id;
    }

    public void setId(AuthorityId id) {
        this.id = id;
    }
}
