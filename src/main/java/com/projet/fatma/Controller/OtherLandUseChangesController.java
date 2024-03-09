package com.projet.fatma.Controller;

import com.projet.fatma.models.dto.OtherLandUseChanges;
import com.projet.fatma.models.dto.ProjectDescription;
import com.projet.fatma.services.KafkaProducerLandUseService;
import com.projet.fatma.services.KafkaProducerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class OtherLandUseChangesController {

    @Autowired
    KafkaProducerLandUseService kafkaProducerLandUseService ;

    @PostMapping("/Create_Land_Use_Changes")
    ResponseEntity<String>createLandUse(@RequestBody OtherLandUseChanges otherLandUseChanges){
        kafkaProducerLandUseService.create(otherLandUseChanges);
        return new ResponseEntity<>("Project added successfully", HttpStatus.CREATED);

    }



    @PutMapping("/Update_Land_Use_Changes/{landUseId}")
    public ResponseEntity<String> updateLandUse(
            @PathVariable String landUseId,
            @RequestBody OtherLandUseChanges updatedOtherLandUse) {
        try {
            kafkaProducerLandUseService.update(landUseId,updatedOtherLandUse);
            return new ResponseEntity<>("LandUse updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LandUse not found with ID: " + landUseId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error updating LandUse with ID: {}", landUseId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating project");
        }
    }

    @DeleteMapping("/Delete_Land_Use_Changes/{landUseId}")
    public ResponseEntity<String> deleteLandUse(@PathVariable String landUseId) {
        try {
            // Delete the project description in the database
            kafkaProducerLandUseService.delete(landUseId );

            // Log the success
            log.info("Deleted LandUse with ID: {}", landUseId);

            // Return a success response
            return ResponseEntity.ok("LandUse deleted successfully");
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("LandUse not found with ID: " + landUseId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error deleting LandUse with ID: {}", landUseId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting project");
        }
    }

}
