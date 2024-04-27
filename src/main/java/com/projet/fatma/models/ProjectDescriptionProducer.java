package com.projet.fatma.models;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ProjectDescriptionProducer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String stringId ;
    private String userName;
    @Temporal (TemporalType.DATE)
    private Date date;
    private String projectName;
    private int projectCode;
    private float projectCost;
    private String executingAgency;
    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;
    @Enumerated(EnumType.STRING)
    private Continent continent;
    @Enumerated(EnumType.STRING)
    private Country country;
    @Enumerated(EnumType.STRING)
    private Moisture moisture;
    @Enumerated(EnumType.STRING)
    private SoilType soilType;
    private int implementationPhase;
    private int capitalizationPhase;
    private int totalDurationOfAccounting;
    private Source source;
    private int co2;
    private int ch4;
    private int n2o;
    private float soCref;
}
