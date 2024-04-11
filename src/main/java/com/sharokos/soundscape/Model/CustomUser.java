package com.sharokos.soundscape.Model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class CustomUser extends User {
    @Column(unique = true, name="username")
    @Id
    @NotEmpty(message = "Username is required")
    private String username;
    @Column(name="password")
    @NotEmpty(message = "Password is required")
    private String password;
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean enabled;
    @OneToMany(mappedBy = "id.username", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Authority> authorities;
    @Transient
    @NotEmpty(message = "Confirmation is required")
    private String confirmPassword;

    public int getNumberOfPresets() {
        return numberOfPresets;
    }

    public void setNumberOfPresets(int numberOfPresets) {
        this.numberOfPresets = numberOfPresets;
    }

    @Column(name="preset_number")
    private int numberOfPresets;

    public void incrementPresets(){
        numberOfPresets++;
    }
    public void decrementPresets(){
        if (numberOfPresets>0) {
            numberOfPresets--;
        }
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Set<Authority> getAuth() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public CustomUser(){
        super("default", "default", AuthorityUtils.createAuthorityList("ROLE_USER"));

    }
    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }
    @Override
    public String getUsername() {
        return username;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
