package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Authority;
import com.sharokos.soundscape.Model.Preset;
import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SoundscapeDAO implements ISoundscapeDAO{

    private EntityManager entityManager;
    @Autowired
    public SoundscapeDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    public void addSoundscape() {
    }

    @Override
    public Soundscape getSoundscapeById(int id) {
        Soundscape scape = entityManager.find(Soundscape.class, id);
        return scape;
    }


    @Override
    public List<Soundscape> getAllSoundscapes() {
        TypedQuery<Soundscape> soundScapes = entityManager.createQuery("from Soundscape", Soundscape.class);
        return soundScapes.getResultList();
    }



}
