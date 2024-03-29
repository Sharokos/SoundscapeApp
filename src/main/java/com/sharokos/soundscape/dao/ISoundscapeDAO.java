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
    List<Soundscape> getAllSoundscapes();
}
