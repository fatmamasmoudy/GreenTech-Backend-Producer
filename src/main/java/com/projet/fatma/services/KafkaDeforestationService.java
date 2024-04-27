package com.projet.fatma.services;

import com.projet.fatma.models.DeforestationProducer;
import com.projet.fatma.models.OtherLandUseChangesProducer;
import com.projet.fatma.models.ProjectDescriptionProducer;
import com.projet.fatma.models.dto.Deforestation;
import com.projet.fatma.models.dto.OtherLandUseChanges;
import com.projet.fatma.models.dto.ProjectDescription;
import com.projet.fatma.repository.DeforestationRepository;
import com.projet.fatma.repository.OtherLandUseChangesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class KafkaDeforestationService {

    private final Logger LOGGER = LoggerFactory.getLogger(KafkaDeforestationService.class);

    @Autowired
    DeforestationRepository deforestationRepository ;
    @Autowired
    KafkaTemplate<Long, Deforestation> deforestationKafkaTemplate;





    public DeforestationProducer create(Deforestation deforestation) {
        String topicName = "land-use-topic";
        deforestation.setEventType("create");

        deforestationKafkaTemplate.send(topicName, deforestation);
        LOGGER.info("Deforestation created and sent to Kafka: " + deforestation);
        return deforestationRepository.save(deforestation.getDeforestationProducer());
    }


    public DeforestationProducer update(String deforestationId, Deforestation deforestation) {
        String topicName = "land-use-topic";
        deforestation.setEventType("update");
        // Retrieve existing project description from the database
        DeforestationProducer existingDeforestationP = deforestationRepository.findByStringId(deforestationId)
                .orElseThrow(() -> new EntityNotFoundException("Deforestation not found with ID: " + deforestationId));

        // Get the new project description from the request body
        DeforestationProducer newDeforestation  =      deforestation.getDeforestationProducer() ;
        LOGGER.info("this deforestation : " + existingDeforestationP);
        LOGGER.info("new deforestation : " + newDeforestation);

        // Update the existing project description with values from the new project description
        existingDeforestationP.setClimate(newDeforestation.getClimate());
        existingDeforestationP.setVegetationUsed(newDeforestation.getVegetationUsed());
        existingDeforestationP.setHwps(newDeforestation.getHwps());
        existingDeforestationP.setFireUsed(newDeforestation.isFireUsed());
        existingDeforestationP.setLandUseType(newDeforestation.getLandUseType());
        existingDeforestationP.setAgroforestrySystem(newDeforestation.getAgroforestrySystem());
        existingDeforestationP.setStartForestedArea1(newDeforestation.getStartForestedArea1());
        existingDeforestationP.setWithoutForestedArea1(newDeforestation.getWithoutForestedArea1());
        existingDeforestationP.setTypeWithout1(newDeforestation.getTypeWithout1());
        existingDeforestationP.setWithForestedArea1(newDeforestation.getWithForestedArea1());
        existingDeforestationP.setTypeWith1(newDeforestation.getTypeWith1());
        existingDeforestationP.setWithoutDeforestedArea1(newDeforestation.getWithoutDeforestedArea1());
        existingDeforestationP.setWithDeforestedArea1(newDeforestation.getWithDeforestedArea1());
        existingDeforestationP.setWithoutTotEmissions1(newDeforestation.getWithoutTotEmissions1());
        existingDeforestationP.setWithTotEmissions1(newDeforestation.getWithTotEmissions1());
        existingDeforestationP.setBalance1(newDeforestation.getBalance1());

        //kamel mba3d

        // Set event type and update project description in the request body

        deforestation.setDeforestationProducer(existingDeforestationP);

        // Send the updated project description to Kafka topic
        deforestationKafkaTemplate.send(topicName, deforestation);

        // Save the updated projectDescription to the database and return the updated object
        return deforestationRepository.save(existingDeforestationP);
    }

    @Transactional
    public void delete(String deforestationId) {
        DeforestationProducer existingDeforestationP = deforestationRepository.findByStringId(deforestationId)
                .orElseThrow(() -> new EntityNotFoundException("Deforestation not found with ID: " + deforestationId));

        String ssd = existingDeforestationP.getStringId();
        Long iid = existingDeforestationP.getId();

        // Check if the project with the given ID exists in the database
        Optional<DeforestationProducer> existingDeforestation = deforestationRepository.findByStringId(deforestationId);
        if (existingDeforestation.isPresent()) {
            // Delete the project from the database
            deforestationRepository.deleteByStringId(deforestationId);

            // Send a delete message to Kafka indicating the project deletion
            String topicName = "land-use-topic";
            String eventType = "delete";

            // Create a message object indicating the deletion
            DeforestationProducer deforestationProducer = new DeforestationProducer();
            deforestationProducer.setId(iid);
            deforestationProducer.setStringId(ssd);
            Deforestation deforestation = new Deforestation();
            deforestation.setDeforestationProducer(deforestationProducer);
            deforestation.setEventType(eventType);

            // Send the deletion message to Kafka
            deforestationKafkaTemplate.send(topicName, iid, deforestation);

        } else {
            // Handle the case where the project with the given ID is not found
            throw new EntityNotFoundException("Deforestation not found with ID: " + deforestationId);
        }
    }
}
