package com.projet.fatma.Controller;

import com.projet.fatma.models.dto.ForestManagement;
import com.projet.fatma.models.dto.MessageDto;
import com.projet.fatma.models.dto.OtherLandUseChanges;
import com.projet.fatma.services.KafkaForesetManagmentService;
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
public class ForestManagementController {

    @Autowired
    KafkaForesetManagmentService kafkaForesetManagmentService ;

    @PostMapping("/Create_Forest_Management")
    ResponseEntity<?>createLandUse(@RequestBody ForestManagement forestManagement){
        forestManagement.getForestManagement().setStringId(UUID.randomUUID().toString());
        kafkaForesetManagmentService.create(forestManagement);
        return new ResponseEntity<>(new MessageDto("Forest added successfully"), HttpStatus.CREATED);

    }



    @PutMapping("/Update_Forest_Management/{forestId}")
    public ResponseEntity<?> updateForestManagement(
            @PathVariable String forestId,
            @RequestBody ForestManagement updatedForest) {
        try {
            kafkaForesetManagmentService.update(forestId,updatedForest);
            return new ResponseEntity<>(new MessageDto("forest updated successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("forest not found with ID: " + forestId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error updating forest with ID: {}", forestId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating forest");
        }
    }

    @DeleteMapping("/Delete_Forest_Management/{forestId}")
    public ResponseEntity<?> deleteLandUse(@PathVariable String forestId) {
        try {
            // Delete the project description in the database
            kafkaForesetManagmentService.delete(forestId );

            // Log the success
            log.info("Deleted forest with ID: {}", forestId);

            // Return a success response
            return ResponseEntity.ok(new MessageDto("forest deleted successfully"));
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("forest not found with ID: " + forestId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error deleting forest with ID: {}", forestId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting forest");
        }
    }
}
