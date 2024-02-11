package com.sharokos.soundscape.Model;

import jakarta.persistence.*;

@Entity
@Table(name="soundscape")
public class Soundscape{
    @Column(name="name")
    private String soundscapeName;
    @Column(name="description")
    private String soundscapeDescription;
    @Column(name="soundscape_image")
    private String soundscapeImage;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    public String getSoundscapeDescription() {
        return soundscapeDescription;
    }

    public void setSoundscapeDescription(String soundscapeDescription) {
        this.soundscapeDescription = soundscapeDescription;
    }

    public String getSoundscapeImage() {
        return soundscapeImage;
    }

    public void setSoundscapeImage(String soundscapeImage) {
        this.soundscapeImage = soundscapeImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSoundscapeName() {
        return soundscapeName;
    }

    public void setSoundscapeName(String soundscapeName) {
        this.soundscapeName = soundscapeName;
    }

    public int getId() {
        return id;
    }

}
