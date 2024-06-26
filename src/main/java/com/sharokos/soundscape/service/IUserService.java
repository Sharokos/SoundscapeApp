package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface IUserService {
    User saveUser(CustomUser theUser);
    boolean usernameExists(String username);
    User getUserByName(String username);
}
