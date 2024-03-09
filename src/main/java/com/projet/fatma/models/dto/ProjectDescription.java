package com.projet.fatma.models.dto;

import com.projet.fatma.models.ProjectDescriptionProducer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDescription {

    private ProjectDescriptionProducer projectDescription ;
    private String eventType ;
}
