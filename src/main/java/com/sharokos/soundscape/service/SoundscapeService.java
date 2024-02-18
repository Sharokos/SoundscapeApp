package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;
import com.sharokos.soundscape.dao.SoundscapeDAO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SoundscapeService implements ISoundscapeService{

    private SoundscapeDAO soundscapeDAO;
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
}
