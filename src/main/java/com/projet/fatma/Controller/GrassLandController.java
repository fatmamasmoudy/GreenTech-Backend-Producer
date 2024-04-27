package com.projet.fatma.Controller;


import com.projet.fatma.models.GrasslandProducer;
import com.projet.fatma.models.dto.Deforestation;
import com.projet.fatma.models.dto.GrassLand;
import com.projet.fatma.models.dto.MessageDto;
import com.projet.fatma.models.dto.ProjectDescription;
import com.projet.fatma.services.KafkaGrassLandSevice;
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
public class GrassLandController {

    @Autowired
    KafkaGrassLandSevice kafkaGrassLandSevice ;


    @PostMapping("/Create_Grassland")
    ResponseEntity<?> createGrassland(@RequestBody GrassLand grassLand){
        grassLand.getGrassland().setStringId(UUID.randomUUID().toString());
        kafkaGrassLandSevice.create(grassLand);
        return new ResponseEntity<>(new MessageDto("Grassland added successfully"), HttpStatus.CREATED);

    }

    @PutMapping("/Update_Grassland/{grassLandId}")
    public ResponseEntity<?> updateGrassland(
            @PathVariable String grassLandId,
            @RequestBody GrassLand updatedGrass) {
        try {
            kafkaGrassLandSevice.update(grassLandId,updatedGrass);
            return new ResponseEntity<>(new MessageDto("Grassland updated successfully"), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grassland not found with ID: " + grassLandId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error updating Grassland with ID: {}", grassLandId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating Grassland");
        }
    }

    @DeleteMapping("/Delete_Grass_Land/{grassLandId}")
    public ResponseEntity<?> deleteGrassland(@PathVariable String grassLandId) {
        try {
            // Delete the project description in the database
            kafkaGrassLandSevice.delete(grassLandId );

            // Log the success
            log.info("Deleted Grassland with ID: {}", grassLandId);

            // Return a success response
            return ResponseEntity.ok(new MessageDto("Grassland deleted successfully"));
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grassland not found with ID: " + grassLandId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error deleting Grassland with ID: {}", grassLandId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting Grassland");
        }
    }


}
