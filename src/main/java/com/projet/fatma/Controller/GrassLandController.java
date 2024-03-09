package com.projet.fatma.Controller;


import com.projet.fatma.models.GrasslandProducer;
import com.projet.fatma.models.dto.GrassLand;
import com.projet.fatma.services.KafkaGrassLandSevice;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
public class GrassLandController {

    @Autowired
    KafkaGrassLandSevice kafkaGrassLandSevice ;

    @PostMapping("/Create_Grass_Land")
    ResponseEntity<String> createLandUse(@RequestBody GrassLand grassLand){
        grassLand.getGrassland().setStringId(UUID.randomUUID().toString());
        kafkaGrassLandSevice.create(grassLand);
        return new ResponseEntity<>("Forest added successfully", HttpStatus.CREATED);

    }



    @PutMapping("/Update_Grass_Land/{grassLandId}")
    public ResponseEntity<String> updateForestManagement(
            @PathVariable String grassLandId,
            @RequestBody GrassLand updatedGrass) {
        try {
            kafkaGrassLandSevice.update(grassLandId,updatedGrass);
            return new ResponseEntity<>("forest updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("forest not found with ID: " + grassLandId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error updating forest with ID: {}", grassLandId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating forest");
        }
    }

    @DeleteMapping("/Delete_Grass_Land/{grassLandId}")
    public ResponseEntity<String> deleteLandUse(@PathVariable String grassLandId) {
        try {
            // Delete the project description in the database
            kafkaGrassLandSevice.delete(grassLandId );

            // Log the success
            log.info("Deleted forest with ID: {}", grassLandId);

            // Return a success response
            return ResponseEntity.ok("forest deleted successfully");
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("forest not found with ID: " + grassLandId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error deleting forest with ID: {}", grassLandId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting forest");
        }
    }


}
