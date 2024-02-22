package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import com.sharokos.soundscape.dao.SoundDAO;
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
public class SoundService implements ISoundService{

    private SoundDAO soundDAO;

    @Autowired
    public SoundService(SoundDAO soundDAO){
        this.soundDAO = soundDAO;

    }
    @Override
    public List<Sound> getSoundsBySoundscape(Soundscape scape) {
        return soundDAO.getSoundsFromSoundscape(scape);
    }

}
