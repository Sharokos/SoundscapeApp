package com.sharokos.soundscape.service;

import com.sharokos.soundscape.Model.*;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface IPresetService {
    public List<Preset> getPresetByUserAndSoundscape(String username, int soundScapeId);
    public List<Preset> getDefaultPresets(int soundScapeId);
    Preset getPresetById(int presetId);
    Preset savePreset(Preset thePreset);
    void deletePreset(int presetId);
}
