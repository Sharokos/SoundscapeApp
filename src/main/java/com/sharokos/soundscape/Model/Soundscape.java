package com.sharokos.soundscape.Model;

import jakarta.persistence.*;

@Entity
@Table(name="soundscape")
public class Soundscape{
    @Column(name="name")
    private String soundscapeName;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

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
