package com.sharokos.soundscape.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Map;
@Entity
@Table(name = "preset")
public class Preset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="preset_name")
    @Size(min = 2, max = 30, message = "Preset name must have 2-30 characters.")
    @NotBlank(message = "Preset must not be blank!")
    private String presetName;
    @ElementCollection
    @CollectionTable(name = "presetsound", joinColumns = @JoinColumn(name = "preset_id"))
    @MapKeyColumn(name = "sound_name")
    @Column(name = "volume")
    private Map<String, Integer> soundVolumes;
    @ElementCollection
    @CollectionTable(name = "presetfreq", joinColumns = @JoinColumn(name = "preset_id"))
    @MapKeyColumn(name = "sound_name")
    @Column(name = "frequency")
    private Map<String, Integer> soundFrequency;
    @ManyToOne
    @JoinColumn(name = "associated_soundscape_id", nullable = false)
    private Soundscape associatedSoundscape;
    @Column(name = "associated_username", nullable = false)
    private String associatedUsername;

    public Preset() {
    }

    public Preset(int id,String presetName, Map<String, Integer> soundVolumes, Map<String, Integer> soundFrequency, Soundscape associatedSoundscape, String associatedUsername) {
        this.id = id;
        this.presetName = presetName;
        this.soundVolumes = soundVolumes;
        this.soundFrequency = soundFrequency;
        this.associatedSoundscape = associatedSoundscape;
        this.associatedUsername = associatedUsername;
    }

    public Preset getPresetDeepCopy(){
        int id = this.getId();
        String name = this.getPresetName();
        Map<String, Integer> volumes = this.getSoundVolumes();
        Map<String, Integer> freqs = this.getSoundFrequency();
        Soundscape scape = this.getAssociatedSoundscape();
        String username = this.getAssociatedUsername();
        return new Preset(id, name, volumes, freqs, scape, username);
    };
    public Map<String, Integer> getSoundFrequency() {
        return soundFrequency;
    }

    public void setSoundFrequency(Map<String, Integer> soundFrequency) {
        this.soundFrequency = soundFrequency;
    }

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

}
