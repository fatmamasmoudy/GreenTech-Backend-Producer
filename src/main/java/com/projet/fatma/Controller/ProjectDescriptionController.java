    package com.projet.fatma.Controller;

    import com.projet.fatma.models.dto.ProjectDescription;
    import com.projet.fatma.services.KafkaProducerService;
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
    public class ProjectDescriptionController {

        @Autowired
        KafkaProducerService kafkaProducerService ;

        @PostMapping("/CreateProjectDescription")
        ResponseEntity<String>createProject(@RequestBody ProjectDescription projectDescription){
            projectDescription.getProjectDescription().setStringId(UUID.randomUUID().toString());
            kafkaProducerService.create(projectDescription);
            return new ResponseEntity<>("Project added successfully", HttpStatus.CREATED);

        }


    @PutMapping("/Update_Project_Description/{projectId}")
    public ResponseEntity<String> updateProjectDescription(
            @PathVariable String projectId,
            @RequestBody ProjectDescription updatedProjectDescription) {
        try {
            kafkaProducerService.update(projectId,updatedProjectDescription);
            return new ResponseEntity<>("Project updated successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found with ID: " + projectId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error updating project with ID: {}", projectId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating project");
        }
    }

    @DeleteMapping("/Delete_Project_Description/{projectId}")
    public ResponseEntity<String> deleteProjectDescription(@PathVariable String projectId) {
        try {
            // Delete the project description in the database
            kafkaProducerService.delete(projectId );

            // Log the success
            log.info("Deleted project with ID: {}", projectId);

            // Return a success response
            return ResponseEntity.ok("Project deleted successfully");
        } catch (EntityNotFoundException e) {
            // Handle the case where the project with the given ID is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found with ID: " + projectId);
        } catch (Exception e) {
            // Handle other exceptions
            log.error("Error deleting project with ID: {}", projectId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting project");
        }
    }

}
