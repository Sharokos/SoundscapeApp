package com.sharokos.soundscape.dao;

import com.sharokos.soundscape.Model.Preset;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PresetDAO implements IPresetDAO{

    private EntityManager entityManager;
    @Autowired
    public PresetDAO(EntityManager entityManager){
        this.entityManager = entityManager;
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

    @Override
    public void deletePreset(int presetId) {
        Preset preset = entityManager.find(Preset.class, presetId);
        entityManager.remove(preset);
    }
}
