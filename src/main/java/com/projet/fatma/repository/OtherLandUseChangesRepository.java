package com.projet.fatma.repository;

import com.projet.fatma.models.GrasslandProducer;
import com.projet.fatma.models.OtherLandUseChangesProducer;
import com.projet.fatma.models.ProjectDescriptionProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtherLandUseChangesRepository extends JpaRepository<OtherLandUseChangesProducer,Long> {

        Optional<OtherLandUseChangesProducer> findById(Long landUseId);
    Optional<OtherLandUseChangesProducer> findByStringId(String landStringId);
    void deleteByStringId(String stringId);
    }

