package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface ISoundscapeService {
    Soundscape getSoundscapeById(int id);
    public List<Soundscape> getAllSoundscapes();
}
