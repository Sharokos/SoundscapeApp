package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Authority;
import com.sharokos.soundscape.Model.CustomUser;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO implements IUserDAO{
    private EntityManager entityManager;
    @Autowired
    public UserDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public User saveUser(User theUser) {
        return entityManager.merge(theUser);
    }

    @Override
    public boolean usernameExists(String username) {
        CustomUser foundUser = entityManager.find(CustomUser.class,username);
        return foundUser != null;
    }

    @Override
    public User getUserByName(String username) {
        return entityManager.find(CustomUser.class,username);
    }

}
