package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface ISoundService {

    public List<Sound> getSoundsBySoundscape(Soundscape scape);
}
