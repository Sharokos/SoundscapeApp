package com.sharokos.soundscape.Model;

import jakarta.persistence.*;
@Entity
@Table(name="sound")
public class Sound {
    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public Soundscape getSoundscape() {
        return soundscape;
    }

    public void setSoundscape(Soundscape soundscape) {
        this.soundscape = soundscape;
    }

    public int getSoundId() {
        return soundId;
    }

    public void setSoundId(int soundId) {
        this.soundId = soundId;
    }

    @Column(name="sound_name")
    private String soundName;
    @Column(name="path")
    private String soundPath;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Soundscape soundscape;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="sound_id")
    private int soundId;
}
