package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Authority;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface ISoundscapeDAO {
    void addSoundscape();
    Soundscape getSoundscapeById(int id);
    List<Sound> getSoundsFromSoundscape(Soundscape soundscape);
    List<Soundscape> getAllSoundscapes();

    Preset getPresetById(int presetId);
    List<Preset> getPresetsByUserAndSoundscape(String username, int soundscapeId);
    List<Preset> getDefaultPresetsForSoundscape(int soundscapeId);

    Preset savePreset(Preset thePreset);
    User saveUser(User theUser);
    Authority saveAuthority(Authority authority);
}
