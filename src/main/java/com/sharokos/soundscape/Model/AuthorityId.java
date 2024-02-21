package com.sharokos.soundscape.Model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AuthorityId implements Serializable {

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "authority", length = 50)
    private String authority;

    // Constructors, getters, and setters
    public AuthorityId(){
        this.username = "default_user";
        this.authority = "ROLE_USER";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorityId that = (AuthorityId) o;
        return Objects.equals(username, that.username) &&
                Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, authority);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}