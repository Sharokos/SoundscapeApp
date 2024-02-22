package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import com.sharokos.soundscape.dao.SoundscapeDAO;
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
public class SoundscapeService implements ISoundscapeService{

    private SoundscapeDAO soundscapeDAO;
    @Autowired
    public SoundscapeService(SoundscapeDAO soundscapeDAO){
        this.soundscapeDAO = soundscapeDAO;
    }
    @Override
    public Soundscape getSoundscapeById(int id) {
        return soundscapeDAO.getSoundscapeById(id);
    }

    @Override
    public List<Soundscape> getAllSoundscapes() {
        return soundscapeDAO.getAllSoundscapes();
    }

}
