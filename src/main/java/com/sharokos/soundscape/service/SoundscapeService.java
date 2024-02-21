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
    public List<Sound> getSoundsBySoundscape(Soundscape scape) {
        return soundscapeDAO.getSoundsFromSoundscape(scape);
    }

    @Override
    public List<Preset> getPresetByUserAndSoundscape(String username, int soundScapeId) {
        return soundscapeDAO.getPresetsByUserAndSoundscape(username, soundScapeId);
    }

    @Override
    public List<Preset> getDefaultPresets(int soundScapeId) {
        return soundscapeDAO.getDefaultPresetsForSoundscape(soundScapeId);
    }

    @Override
    public List<Soundscape> getAllSoundscapes() {
        return soundscapeDAO.getAllSoundscapes();
    }

    @Override
    public Preset getPresetById(int presetId) {
        return soundscapeDAO.getPresetById(presetId);
    }

    @Override
    @Transactional
    public Preset savePreset(Preset thePreset) {
       return soundscapeDAO.savePreset(thePreset);
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
       return soundscapeDAO.saveUser(theUser);
    }
    @Override
    @Transactional
    public Authority saveAuthority(Authority authority) {


        return soundscapeDAO.saveAuthority(authority);
    }
}
