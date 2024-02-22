package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface IUserService {
    User saveUser(CustomUser theUser);
    Authority saveAuthority(Authority authority);
}
