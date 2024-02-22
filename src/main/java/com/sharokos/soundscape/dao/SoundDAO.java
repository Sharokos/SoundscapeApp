package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class SoundDAO implements ISoundDAO{
    private EntityManager entityManager;
    @Autowired
    public SoundDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public List<Sound> getSoundsFromSoundscape(Soundscape soundscape) {
        int soundScapeId = soundscape.getId();
        String sql = "SELECT b FROM Sound b JOIN b.soundscape a WHERE a.id = :soundScapeId";
        TypedQuery<Sound> sounds = entityManager.createQuery(sql, Sound.class);
        sounds.setParameter("soundScapeId", soundScapeId);
        System.out.println(sounds.getResultList());
        return sounds.getResultList();
    }
}
