package com.projet.fatma.Controller;


import com.projet.fatma.models.dto.Deforestation;
import com.projet.fatma.models.dto.MessageDto;
import com.projet.fatma.models.dto.OtherLandUseChanges;
import com.projet.fatma.services.KafkaDeforestationService;
import com.projet.fatma.services.KafkaProducerLandUseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@CrossOrigin("*")
@RestController
@Slf4j
public class DeforestationController {

    @Autowired
    KafkaDeforestationService kafkaDeforestationService ;

    @PostMapping("/Create_Deforestation")
    ResponseEntity<?> createDeforestation(@RequestBody Deforestation deforestation){
        deforestation.getDeforestationProducer().setStringId(UUID.randomUUID().toString());
        kafkaDeforestationService.create(deforestation);
        return new ResponseEntity<>(new MessageDto("Deforestation added successfully"), HttpStatus.CREATED);

    }



    @PutMapping("/Update_Deforestation/{deforestationId}")
    public ResponseEntity<?> updateDeforestation(
            @PathVariable String deforestationId,
            @RequestBody Deforestation updatedDeforestation) {
        try {
            kafkaDeforestationService.update(deforestationId,updatedDeforestation);
            return new ResponseEntity<>(new MessageDto("Deforestation updated successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deforestation not found with ID: " + deforestationId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error updating Deforestation with ID: {}", deforestationId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Deforestation");
        }
    }


    @DeleteMapping("/Delete_Deforestation/{deforestationId}")
    public ResponseEntity<?> deleteDeforestation(@PathVariable String deforestationId) {
        try {
            // Delete the project description in the database
            kafkaDeforestationService.delete(deforestationId );

            // Log the success
            log.info("Deleted Deforestation with ID: {}", deforestationId);

            // Return a success response
            return ResponseEntity.ok(new MessageDto("Deforestation deleted successfully"));
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Deforestation not found with ID: " + deforestationId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error deleting Deforestation with ID: {}", deforestationId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Deforestation");
        }
    }

}
