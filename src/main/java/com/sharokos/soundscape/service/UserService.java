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
       // Fill in the remaining fields for the new user and encrypt password
       theUser.setAuthorities(generateDefaultAuthorityForUser(theUser));
       theUser.setPassword("{bcrypt}" + encoder.encode(theUser.getPassword()));
       theUser.setEnabled(true);

       return userDAO.saveUser(theUser);
    }

    @Override
    public boolean usernameExists(String username) {

        return userDAO.usernameExists(username);
    }

    //Utility function to keep the code clean in the saveUser function
    public Set<Authority> generateDefaultAuthorityForUser(CustomUser theUser){
        //Create return set
        Set<Authority> returnAuth = new HashSet<>();
        //Create authority and authorityId and populate them
        Authority auth = new Authority();
        AuthorityId authId = new AuthorityId();
        authId.setUsername(theUser.getUsername());
        auth.setId(authId);

        returnAuth.add(auth);
        return returnAuth;
    }
}
