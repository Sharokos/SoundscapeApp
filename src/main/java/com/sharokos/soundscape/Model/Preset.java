package com.sharokos.soundscape.Model;

import jakarta.persistence.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
@Entity
@Table(name = "preset")
public class Preset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="preset_name")
    private String presetName;

    // Other preset-related fields

    @ElementCollection
    @CollectionTable(name = "presetsound", joinColumns = @JoinColumn(name = "preset_id"))
    @MapKeyColumn(name = "sound_name")
    @Column(name = "volume")
    private Map<String, Integer> soundVolumes;
    @ManyToOne
    @JoinColumn(name = "associated_soundscape_id", nullable = false)
    private Soundscape associatedSoundscape;
    @Column(name = "associated_username", nullable = false)
    private String associatedUsername;

    public Soundscape getAssociatedSoundscape() {
        return associatedSoundscape;
    }

    public void setAssociatedSoundscape(Soundscape associatedSoundscape) {
        this.associatedSoundscape = associatedSoundscape;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPresetName() {
        return presetName;
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }

    public Map<String, Integer> getSoundVolumes() {
        return soundVolumes;
    }

    public void setSoundVolumes(Map<String, Integer> soundVolumes) {
        this.soundVolumes = soundVolumes;
    }
// Getters and setters
}
