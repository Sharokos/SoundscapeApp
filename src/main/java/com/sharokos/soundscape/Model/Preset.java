package com.sharokos.soundscape.Model;

import java.util.Map;

public class Preset {
    private Map<String, Integer> soundVolumes;
    // Getters and setters

    public Map<String, Integer> getSoundVolumes() {
        return soundVolumes;
    }

    public void setSoundVolumes(Map<String, Integer> soundVolumes) {
        this.soundVolumes = soundVolumes;
    }
}
