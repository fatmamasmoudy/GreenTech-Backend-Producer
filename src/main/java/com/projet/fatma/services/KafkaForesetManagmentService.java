package com.projet.fatma.services;

import com.projet.fatma.models.*;
import com.projet.fatma.models.dto.ForestManagement;
import com.projet.fatma.models.dto.OtherLandUseChanges;
import com.projet.fatma.repository.ForestManagementRepository;
import com.projet.fatma.repository.OtherLandUseChangesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class KafkaForesetManagmentService {


        private final Logger LOGGER = LoggerFactory.getLogger(com.projet.fatma.services.KafkaForesetManagmentService.class);

        @Autowired
        ForestManagementRepository forestManagementRepository ;
        @Autowired
        KafkaTemplate<Long, ForestManagement> forestManagementKafkaTemplate;





        public ForestManagementProducerr create(ForestManagement forestManagement) {
            String topicName = "forest-management-topic";
            forestManagement.setEventType("create");

            forestManagementKafkaTemplate.send(topicName, forestManagement);
            LOGGER.info("forest management created and sent to Kafka: " + forestManagement);
            return forestManagementRepository.save(forestManagement.getForestManagement());
        }

@Transactional
        public ForestManagementProducerr update(String forestId, ForestManagement forestManagement) {
            String topicName = "forest-management-topic";
            forestManagement.setEventType("update");
            // Retrieve existing project description from the database
            ForestManagementProducerr existingForestP = forestManagementRepository.findByStringId(forestId)
                    .orElseThrow(() -> new EntityNotFoundException("forest management not found with ID: " + forestId));

            // Get the new project description from the request body
            ForestManagementProducerr newForest  =      forestManagement.getForestManagement() ;
            LOGGER.info("this forest : " + existingForestP);
            LOGGER.info("new forest : " + newForest);

            // Update the existing project description with values from the new project description
            existingForestP.setForestVegetation(newForest.getForestVegetation());
            existingForestP.setStartForestDegradationLevel(newForest.getStartForestDegradationLevel());
            existingForestP.setWithoutForestDegradationLevel(newForest.getWithoutForestDegradationLevel());
            existingForestP.setWithForestDegradationLevel(newForest.getWithForestDegradationLevel());
            existingForestP.setWithoutFireOccurrence(newForest.getWithoutFireOccurrence());
            existingForestP.setWithFireOccurrence(newForest.getWithFireOccurrence());
            existingForestP.setWithoutFireImpact(newForest.getWithoutFireImpact());
            existingForestP.setWithFireImpact(newForest.getWithFireImpact());
            existingForestP.setStartForestedAreaManagement(newForest.getStartForestedAreaManagement());
            existingForestP.setWithoutForestedAreaManagement(newForest.getWithoutForestedAreaManagement());
            existingForestP.setTypeWithoutForestedAreaManagement(newForest.getTypeWithoutForestedAreaManagement());
            existingForestP.setWithForestedAreaManagement(newForest.getWithForestedAreaManagement());
            existingForestP.setTypeWithForestedAreaManagement(newForest.getTypeWithForestedAreaManagement());
            existingForestP.setWithoutTotEmissionsForest(newForest.getWithoutTotEmissionsForest());
            existingForestP.setWithTotEmissionsForest(newForest.getWithTotEmissionsForest());
            existingForestP.setBalanceForest(newForest.getBalanceForest());
            existingForestP.setWithoutTotForest(newForest.getWithoutTotForest());
            existingForestP.setWithTotForest(newForest.getWithTotForest());
            existingForestP.setBalanceTotForest(newForest.getBalanceTotForest());
            existingForestP.setDegradationLevel(newForest.getDegradationLevel());
            existingForestP.setBiomassLost(newForest.getBiomassLost());



            // Set event type and update project description in the request body

            forestManagement.setForestManagement(existingForestP);

            // Send the updated project description to Kafka topic
            forestManagementKafkaTemplate.send(topicName, forestManagement);

            // Save the updated projectDescription to the database and return the updated object
            return forestManagementRepository.save(existingForestP);
        }
@Transactional
        public void delete(String forestId) {
            // Check if the project with the given ID exists in the database

            ForestManagementProducerr existingForestP = forestManagementRepository.findByStringId(forestId)
                    .orElseThrow(() -> new EntityNotFoundException("forest management not found with ID: " + forestId));
            String ssd = existingForestP.getStringId();
            Long iid = existingForestP.getId();


            Optional<ForestManagementProducerr> existingForest = forestManagementRepository.findByStringId(forestId);
            if (existingForest.isPresent()) {
                // Delete the project from the database
                forestManagementRepository.deleteByStringId(forestId);

                // Send a delete message to Kafka indicating the project deletion
                String topicName = "forest-management-topic";
                String eventType = "delete";

                // Create a message object indicating the deletion
                ForestManagementProducerr forestManagementProducerr = new ForestManagementProducerr();
                forestManagementProducerr.setId(iid);
                forestManagementProducerr.setStringId(ssd);
                ForestManagement forestManagement = new ForestManagement();
                forestManagement.setForestManagement(forestManagementProducerr);
                forestManagement.setEventType(eventType);

                // Send the deletion message to Kafka
                forestManagementKafkaTemplate.send(topicName, iid, forestManagement);

            } else {
                // Handle the case where the project with the given ID is not found
                throw new EntityNotFoundException("Project not found with ID: " + forestId);
            }
        }
    }



