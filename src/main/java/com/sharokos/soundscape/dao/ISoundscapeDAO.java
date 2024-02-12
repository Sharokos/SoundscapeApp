package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;

import java.util.List;

public interface ISoundscapeDAO {
    void addSoundscape();
    Soundscape getSoundscapeById(int id);
    List<Sound> getSoundsFromSoundscape(Soundscape soundscape);
    List<Soundscape> getAllSoundscapes();

    Preset getPresetById(int presetId);
}
