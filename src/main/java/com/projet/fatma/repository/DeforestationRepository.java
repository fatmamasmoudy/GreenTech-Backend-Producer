package com.projet.fatma.repository;

import com.projet.fatma.models.DeforestationProducer;
import com.projet.fatma.models.GrasslandProducer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeforestationRepository extends JpaRepository<DeforestationProducer,Long> {

    Optional<DeforestationProducer> findById(Long deforestationId);
    Optional<DeforestationProducer> findByStringId(String deforestationId);
    void deleteByStringId(String stringId);


}