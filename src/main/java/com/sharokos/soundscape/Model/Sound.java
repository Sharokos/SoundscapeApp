package com.sharokos.soundscape.Model;

import jakarta.persistence.*;
@Entity
@Table(name="sound")
public class Sound {

    @Column(name="sound_name")
    private String soundName;
    @Column(name="sound_path")
    private String soundPath;
    @Column(name="image_path")
    private String imagePath;
    @Column(name="is_drone")
    private Boolean drone;
    @ManyToOne
    @JoinColumn(name = "id", nullable = false)
    private Soundscape soundscape;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="sound_id")
    private int soundId;

    public Boolean getDrone() {
        return drone;
    }

    public void setDrone(Boolean drone) {
        this.drone = drone;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

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

}
