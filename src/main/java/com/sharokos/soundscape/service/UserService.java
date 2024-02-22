package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import com.sharokos.soundscape.dao.SoundscapeDAO;
import com.sharokos.soundscape.dao.UserDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements IUserService{

    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO){
        this.userDAO = userDAO;

    }

    @Override
    @Transactional
    public User saveUser(CustomUser theUser) {
       PasswordEncoder encoder = new BCryptPasswordEncoder();
       Authority auth = new Authority();
       AuthorityId authId = new AuthorityId();
        authId.setUsername(theUser.getUsername());
        auth.setId(authId);
        Set<Authority> singleAuth = new HashSet<>();
        singleAuth.add(auth);
       theUser.setAuthorities(singleAuth);
       theUser.setPassword("{bcrypt}" + encoder.encode(theUser.getPassword()));
       theUser.setEnabled(true);
       return userDAO.saveUser(theUser);
    }
    @Override
    @Transactional
    public Authority saveAuthority(Authority authority) {


        return userDAO.saveAuthority(authority);
    }
}
