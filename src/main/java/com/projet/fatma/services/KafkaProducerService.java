package com.projet.fatma.services;

import com.projet.fatma.models.ProjectDescriptionProducer;
import com.projet.fatma.models.dto.ProjectDescription;
import com.projet.fatma.repository.ProjectDescriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class KafkaProducerService {
    private final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    ProjectDescriptionRepository projectDescriptionRepository ;
    @Autowired
    KafkaTemplate<Long, ProjectDescription> projectDescriptionkafkaTemplate;





    public ProjectDescriptionProducer create(ProjectDescription projectDescription) {
        String topicName = "project-description-topic";
        projectDescription.setEventType("create");

        projectDescriptionkafkaTemplate.send(topicName, projectDescription);
        LOGGER.info("Project description created and sent to Kafka: " + projectDescription);
        return projectDescriptionRepository.save(projectDescription.getProjectDescription());
    }

@Transactional
    public ProjectDescriptionProducer update(String projectId, ProjectDescription projectDescription) {
        String topicName = "project-description-topic";
        projectDescription.setEventType("update");
        // Retrieve existing project description from the database
        ProjectDescriptionProducer existingProjectDescriptionP = projectDescriptionRepository.findByStringId(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectId));

        // Get the new project description from the request body
            ProjectDescriptionProducer newProject  =      projectDescription.getProjectDescription() ;
        LOGGER.info("this project : " + existingProjectDescriptionP);
        LOGGER.info("new project : " + newProject);

        // Update the existing project description with values from the new project description
        existingProjectDescriptionP.setDate(newProject.getDate());
        existingProjectDescriptionP.setContinent(newProject.getContinent());

        existingProjectDescriptionP.setCountry(newProject.getCountry());
        existingProjectDescriptionP.setCh4(newProject.getCh4());
        existingProjectDescriptionP.setCo2(newProject.getCo2());
        existingProjectDescriptionP.setProjectCode(newProject.getProjectCode());
        existingProjectDescriptionP.setProjectCost(newProject.getProjectCost());
        existingProjectDescriptionP.setProjectName(newProject.getProjectName());
        existingProjectDescriptionP.setProjectStatus(newProject.getProjectStatus());
        existingProjectDescriptionP.setMoisture(newProject.getMoisture());
        existingProjectDescriptionP.setSoCref(newProject.getSoCref());
        existingProjectDescriptionP.setN2o(newProject.getN2o());
        existingProjectDescriptionP.setSource(newProject.getSource());
        existingProjectDescriptionP.setTotalDurationOfAccounting(newProject.getTotalDurationOfAccounting());
        existingProjectDescriptionP.setCapitalizationPhase(newProject.getCapitalizationPhase());
        existingProjectDescriptionP.setImplementationPhase(newProject.getImplementationPhase());
        existingProjectDescriptionP.setSoilType(newProject.getSoilType());
        existingProjectDescriptionP.setExecutingAgency(newProject.getExecutingAgency());
        existingProjectDescriptionP.setUserName(newProject.getUserName());

        // Set event type and update project description in the request body

        projectDescription.setProjectDescription(existingProjectDescriptionP);

        // Send the updated project description to Kafka topic
        projectDescriptionkafkaTemplate.send(topicName, projectDescription);

        // Save the updated projectDescription to the database and return the updated object
        return projectDescriptionRepository.save(existingProjectDescriptionP);
    }

    @Transactional
    public void delete(String projectStringId) {
        ProjectDescriptionProducer existingProjectDescriptionP = projectDescriptionRepository.findByStringId(projectStringId)
                .orElseThrow(() -> new EntityNotFoundException("Project not found with ID: " + projectStringId));

        String ssd = existingProjectDescriptionP.getStringId();
        Long iid = existingProjectDescriptionP.getId();

        // Check if the project with the given ID exists in the database
        Optional<ProjectDescriptionProducer> existingProject = projectDescriptionRepository.findByStringId(projectStringId);
        if (existingProject.isPresent()) {
            // Delete the project from the database
            projectDescriptionRepository.deleteByStringId(projectStringId);

            // Send a delete message to Kafka indicating the project deletion
            String topicName = "project-description-topic";
            String eventType = "delete";

            // Create a message object indicating the deletion
            ProjectDescriptionProducer projectDescriptionProducer = new ProjectDescriptionProducer();
            projectDescriptionProducer.setId(iid);
            projectDescriptionProducer.setStringId(ssd);
            ProjectDescription projectDescription = new ProjectDescription();
            projectDescription.setProjectDescription(projectDescriptionProducer);
            projectDescription.setEventType(eventType);

            // Send the deletion message to Kafka
            projectDescriptionkafkaTemplate.send(topicName, iid, projectDescription);

        } else {
            // Handle the case where the project with the given ID is not found
            throw new EntityNotFoundException("Project not found with ID: " + projectStringId);
        }
    }
}

