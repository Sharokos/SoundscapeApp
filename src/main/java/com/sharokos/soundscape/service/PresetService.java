package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import com.sharokos.soundscape.dao.PresetDAO;
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
public class PresetService implements IPresetService{
    private PresetDAO presetDAO;

    @Autowired
    public PresetService(PresetDAO presetDAO){
        this.presetDAO = presetDAO;
    }
    @Override
    public List<Preset> getPresetByUserAndSoundscape(String username, int soundScapeId) {
        return presetDAO.getPresetsByUserAndSoundscape(username, soundScapeId);
    }
    @Override
    public List<Preset> getDefaultPresets(int soundScapeId) {
        return presetDAO.getDefaultPresetsForSoundscape(soundScapeId);
    }

    @Override
    public Preset getPresetById(int presetId) {
        return presetDAO.getPresetById(presetId);
    }

    @Override
    @Transactional
    public Preset savePreset(Preset thePreset) {
        return presetDAO.savePreset(thePreset);
    }
    @Override
    @Transactional
    public void deletePreset(int presetId) {
        presetDAO.deletePreset(presetId);
    }

}
