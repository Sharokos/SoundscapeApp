package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Sound;
import com.sharokos.soundscape.Model.Soundscape;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Sound> getSoundsFromSoundscape(Soundscape soundscape) {
        int soundScapeId = soundscape.getId();
        System.out.println(soundScapeId);
        //SELECT b.* FROM books b JOIN authors a ON b.author_id = a.author_id WHERE a.author_id = :authorId
//SELECT b FROM Book b JOIN b.author a WHERE a.authorId = :authorId
        String sql = "SELECT b FROM Sound b JOIN b.soundscape a WHERE a.id = :soundScapeId";
        TypedQuery<Sound> sounds = entityManager.createQuery(sql, Sound.class);
        sounds.setParameter("soundScapeId", soundScapeId);
        System.out.println(sounds.getResultList());
        return sounds.getResultList();
    }
}
