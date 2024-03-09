package com.projet.fatma.repository;

import com.projet.fatma.models.ProjectDescriptionProducer;
import com.projet.fatma.models.dto.ProjectDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectDescriptionRepository extends JpaRepository<ProjectDescriptionProducer,Long> {
    Optional<ProjectDescriptionProducer> findById(Long projectId);
    Optional<ProjectDescriptionProducer> findByStringId(String projectStringId);

    void deleteByStringId(String stringId);



}
