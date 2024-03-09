package com.projet.fatma.repository;

import com.projet.fatma.models.ForestManagementProducerr;
import com.projet.fatma.models.GrasslandProducer;
import com.projet.fatma.models.OtherLandUseChangesProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForestManagementRepository extends JpaRepository<ForestManagementProducerr,Long>{

        Optional<ForestManagementProducerr> findById(Long forestId);
        Optional<ForestManagementProducerr> findByStringId(String forestStringId);
        void deleteByStringId(String stringId);
}
