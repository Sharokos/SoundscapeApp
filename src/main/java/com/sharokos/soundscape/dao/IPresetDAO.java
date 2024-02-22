package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Authority;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Soundscape;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface IPresetDAO {

    Preset getPresetById(int presetId);
    List<Preset> getPresetsByUserAndSoundscape(String username, int soundscapeId);
    List<Preset> getDefaultPresetsForSoundscape(int soundscapeId);
    Preset savePreset(Preset thePreset);

}
