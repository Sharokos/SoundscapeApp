package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Preset;
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
        String sql = "SELECT b FROM Sound b JOIN b.soundscape a WHERE a.id = :soundScapeId";
        TypedQuery<Sound> sounds = entityManager.createQuery(sql, Sound.class);
        sounds.setParameter("soundScapeId", soundScapeId);
        System.out.println(sounds.getResultList());
        return sounds.getResultList();
    }

    @Override
    public List<Soundscape> getAllSoundscapes() {
        TypedQuery<Soundscape> soundScapes = entityManager.createQuery("from Soundscape", Soundscape.class);
        return soundScapes.getResultList();
    }

    @Override
    public Preset getPresetById(int presetId) {
        Preset preset = entityManager.find(Preset.class, presetId);
        return preset;
    }

    @Override
    public List<Preset> getPresetsByUserAndSoundscape(String username, int soundscapeId) {
        TypedQuery<Preset> presets = entityManager.createQuery(
                "SELECT p FROM Preset p JOIN p.associatedSoundscape s " +
                        "WHERE p.associatedUsername = :username " +
                        "AND s.id = :soundscapeId", Preset.class)
                .setParameter("username", username)
                .setParameter("soundscapeId", soundscapeId);

        return presets.getResultList();
    }

    @Override
    public List<Preset> getDefaultPresetsForSoundscape(int soundscapeId) {
        TypedQuery<Preset> presets = entityManager.createQuery(
                        "SELECT p FROM Preset p JOIN p.associatedSoundscape s " +
                                "WHERE p.associatedUsername = :username " +
                                "AND s.id = :soundscapeId", Preset.class)
                .setParameter("username", "default")
                .setParameter("soundscapeId", soundscapeId);

        return presets.getResultList();
    }

    @Override
    public Preset savePreset(Preset thePreset) {
        if(thePreset.getPresetName() == null){
            thePreset.setPresetName("DefaultSave");
        }
        return entityManager.merge(thePreset);
    }
}
