package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;

import java.util.List;

public interface ISoundscapeService {
    Soundscape getSoundscapeById(int id);
    public List<Sound> getSoundsBySoundscape(Soundscape scape);
    public List<Soundscape> getAllSoundscapes();
}
