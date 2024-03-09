package com.projet.fatma.repository;

import com.projet.fatma.models.ForestManagementProducerr;
import com.projet.fatma.models.GrasslandProducer;
import com.projet.fatma.models.ProjectDescriptionProducer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrasslandRepository extends JpaRepository<GrasslandProducer,Long> {

    Optional<GrasslandProducer> findById(Long grassLandId);
    Optional<GrasslandProducer> findByStringId(String grassStringId);
    void deleteByStringId(String stringId);


}

