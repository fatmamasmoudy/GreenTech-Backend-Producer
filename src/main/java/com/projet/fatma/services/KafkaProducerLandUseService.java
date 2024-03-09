package com.projet.fatma.services;


import com.projet.fatma.models.GrasslandProducer;
import com.projet.fatma.models.OtherLandUseChangesProducer;
import com.projet.fatma.models.dto.OtherLandUseChanges;
import com.projet.fatma.repository.OtherLandUseChangesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KafkaProducerLandUseService {
    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerLandUseService.class);

    @Autowired
    OtherLandUseChangesRepository otherLandUseChangesRepository ;
    @Autowired
    KafkaTemplate<Long, OtherLandUseChanges> otherLandUseChangesKafkaTemplate;





    public OtherLandUseChangesProducer create(OtherLandUseChanges otherLandUseChanges) {
        String topicName = "land-use-topic";
        otherLandUseChanges.setEventType("create");

        otherLandUseChangesKafkaTemplate.send(topicName, otherLandUseChanges);
        LOGGER.info("Project description created and sent to Kafka: " + otherLandUseChanges);
        return otherLandUseChangesRepository.save(otherLandUseChanges.getOtherLandUseChanges());
    }


    public OtherLandUseChangesProducer update(String landUseId, OtherLandUseChanges otherLandUseChanges) {
        String topicName = "land-use-topic";
        otherLandUseChanges.setEventType("update");
        // Retrieve existing project description from the database
        OtherLandUseChangesProducer existingLandUSeP = otherLandUseChangesRepository.findByStringId(landUseId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + landUseId));

        // Get the new project description from the request body
        OtherLandUseChangesProducer newLandUse  =      otherLandUseChanges.getOtherLandUseChanges() ;
        LOGGER.info("this project : " + existingLandUSeP);
        LOGGER.info("new project : " + newLandUse);

        // Update the existing project description with values from the new project description
        existingLandUSeP.setUserNotes(newLandUse.getUserNotes());
        existingLandUSeP.setFireUsed(newLandUse.getFireUsed());
        existingLandUSeP.setInitialLandUse3(newLandUse.getInitialLandUse3());
        existingLandUSeP.setFinalLandUse3(newLandUse.getFinalLandUse3());
        existingLandUSeP.setWithoutAreaLandUse(newLandUse.getWithoutAreaLandUse());
        existingLandUSeP.setTypeWithAreaLandUse(newLandUse.getTypeWithAreaLandUse());
        existingLandUSeP.setTypeWithoutAreaLandUse(newLandUse.getTypeWithoutAreaLandUse());
        existingLandUSeP.setWithAreaLandUse(newLandUse.getWithAreaLandUse());
        existingLandUSeP.setWithoutTotEmissions3(newLandUse.getWithoutTotEmissions3());
        existingLandUSeP.setWithTotEmissions3(newLandUse.getWithTotEmissions3());
        existingLandUSeP.setBalance3(newLandUse.getBalance3());
        existingLandUSeP.setWithTotNonForestLandUse(newLandUse.getWithTotNonForestLandUse());
        existingLandUSeP.setWithoutTotNonForestLandUse(newLandUse.getWithoutTotNonForestLandUse());
        existingLandUSeP.setBalanceTotNonForestLandUse(newLandUse.getBalanceTotNonForestLandUse());

        // Set event type and update project description in the request body

        otherLandUseChanges.setOtherLandUseChanges(existingLandUSeP);

        // Send the updated project description to Kafka topic
        otherLandUseChangesKafkaTemplate.send(topicName, otherLandUseChanges);

        // Save the updated projectDescription to the database and return the updated object
        return otherLandUseChangesRepository.save(existingLandUSeP);
    }

    public void delete(String landUseId) {
        // Check if the project with the given ID exists in the database
        OtherLandUseChangesProducer existingLandP = otherLandUseChangesRepository.findByStringId(landUseId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + landUseId));
        // Check if the project with the given ID exists in the database
        String ssd = existingLandP.getStringId();
        Long iid = existingLandP.getId();
        Optional<OtherLandUseChangesProducer> existingLandUse = otherLandUseChangesRepository.findByStringId(landUseId);
        if (existingLandUse.isPresent()) {
            // Delete the project from the database
            otherLandUseChangesRepository.deleteByStringId(landUseId);

            // Send a delete message to Kafka indicating the project deletion
            String topicName = "land-use-topic";
            String eventType = "delete";

            // Create a message object indicating the deletion
            OtherLandUseChangesProducer otherLandUseChangesProducer = new OtherLandUseChangesProducer();
            otherLandUseChangesProducer.setId(iid);
            otherLandUseChangesProducer.setStringId(ssd);

            OtherLandUseChanges otherLandUseChanges = new OtherLandUseChanges();
            otherLandUseChanges.setOtherLandUseChanges(otherLandUseChangesProducer);
            otherLandUseChanges.setEventType(eventType);

            // Send the deletion message to Kafka
            otherLandUseChangesKafkaTemplate.send(topicName, iid, otherLandUseChanges);

        } else {
            // Handle the case where the project with the given ID is not found
            throw new EntityNotFoundException("Project not found with ID: " + landUseId);
        }
    }
}

