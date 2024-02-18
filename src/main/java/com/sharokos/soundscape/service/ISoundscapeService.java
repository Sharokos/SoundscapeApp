package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;

import java.util.List;

public interface ISoundscapeService {
    Soundscape getSoundscapeById(int id);
    public List<Sound> getSoundsBySoundscape(Soundscape scape);
    public List<Preset> getPresetByUserAndSoundscape(String username, int soundScapeId);
    public List<Preset> getDefaultPresets(int soundScapeId);
    public List<Soundscape> getAllSoundscapes();

    Preset getPresetById(int presetId);

    Preset savePreset(Preset thePreset);
}
