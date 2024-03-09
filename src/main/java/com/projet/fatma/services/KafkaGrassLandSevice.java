package com.projet.fatma.services;


import com.projet.fatma.models.GrasslandProducer;
import com.projet.fatma.models.dto.GrassLand;
import com.projet.fatma.repository.GrasslandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service

public class KafkaGrassLandSevice {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerLandUseService.class);

    @Autowired
    GrasslandRepository grasslandRepository ;
    @Autowired
    KafkaTemplate<Long, GrassLand> grassLandKafkaTemplate;





    public GrasslandProducer create(GrassLand grassLand) {
        String topicName = "grass-land-topic";
        grassLand.setEventType("create");

        grassLandKafkaTemplate.send(topicName, grassLand);
        LOGGER.info("Project description created and sent to Kafka: " + grassLand);
        return grasslandRepository.save(grassLand.getGrassland());
    }

    @Transactional

    public GrasslandProducer update(String grassLandId, GrassLand grassLand) {
        String topicName = "grass-land-topic";
        grassLand.setEventType("update");
        // Retrieve existing project description from the database
        GrasslandProducer existingGrassP = grasslandRepository.findByStringId(grassLandId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + grassLandId));

        // Get the new project description from the request body
        GrasslandProducer newgrassLand  =      grassLand.getGrassland() ;
        LOGGER.info("this grassLand : " + existingGrassP);
        LOGGER.info("new project : " + newgrassLand);

        // Update the existing project description with values from the new project description
        existingGrassP.setUserNotesGrassland(newgrassLand.getUserNotesGrassland());
        existingGrassP.setWithoutFireMang2(newgrassLand.getWithoutFireMang2());
        existingGrassP.setWithFireMang2(newgrassLand.getWithFireMang2());
        existingGrassP.setStartYield2(newgrassLand.getStartYield2());
        existingGrassP.setWithoutYield2(newgrassLand.getWithoutYield2());
        existingGrassP.setStartAreaGrassland2(newgrassLand.getStartAreaGrassland2());
        existingGrassP.setWithYield2(newgrassLand.getWithYield2());
        existingGrassP.setWithoutAreaGrassland2(newgrassLand.getWithoutAreaGrassland2());
        existingGrassP.setTypeWithAreaGrassland2(newgrassLand.getTypeWithAreaGrassland2());
        existingGrassP.setWithoutTotEmissionsGrassland2(newgrassLand.getWithoutTotEmissionsGrassland2());
        existingGrassP.setWithAreaGrassland2(newgrassLand.getWithAreaGrassland2());
        existingGrassP.setBalanceGrassland2(newgrassLand.getBalanceGrassland2());
        existingGrassP.setWithoutTotGrasslandSys(newgrassLand.getWithoutTotGrasslandSys());
        existingGrassP.setWithTotGrasslandSys(newgrassLand.getWithTotGrasslandSys());
        existingGrassP.setBalanceTotGrasslandSys(newgrassLand.getBalanceTotGrasslandSys());


        // Set event type and update project description in the request body

        grassLand.setGrassland(existingGrassP);

        // Send the updated project description to Kafka topic
        grassLandKafkaTemplate.send(topicName, grassLand);

        // Save the updated projectDescription to the database and return the updated object
        return grasslandRepository.save(existingGrassP);
    }



    @Transactional
    public void delete(String grassLandId) {
        GrasslandProducer existingGrassP = grasslandRepository.findByStringId(grassLandId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + grassLandId));
        // Check if the project with the given ID exists in the database
        String ssd = existingGrassP.getStringId();
        Long iid = existingGrassP.getId();

        Optional<GrasslandProducer> existingLandUse = grasslandRepository.findByStringId(grassLandId);
        if (existingLandUse.isPresent()) {
            // Delete the project from the database


            grasslandRepository.deleteByStringId(grassLandId);

            // Send a delete message to Kafka indicating the project deletion
            String topicName = "grass-land-topic";
            String eventType = "delete";

            // Create a message object indicating the deletion
            GrasslandProducer grasslandProducer = new GrasslandProducer();
            grasslandProducer.setId(iid);
            grasslandProducer.setStringId(ssd);
            GrassLand grassLand = new GrassLand();
            grassLand.setGrassland(grasslandProducer);
            grassLand.setEventType(eventType);

            // Send the deletion message to Kafka
            grassLandKafkaTemplate.send(topicName,iid ,grassLand);

        } else {
            // Handle the case where the project with the given ID is not found
            throw new EntityNotFoundException("Project not found with ID: " + grassLandId);
        }
    }

}
