package com.projet.fatma.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="forestDegradationAndManagement")

public class ForestManagementProducerr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stringId ;
    private ForestVegetation forestVegetation;
    private ForestDegradationLevel startForestDegradationLevel;
    private ForestDegradationLevel withoutForestDegradationLevel;
    private ForestDegradationLevel withForestDegradationLevel;
    private Boolean withoutFireOccurrence;
    private Boolean withFireOccurrence;
    static final int FirePeriodicity = 1;

    private float withoutFireImpact;
    private float withFireImpact;
    private int startForestedAreaManagement;
    private int withoutForestedAreaManagement;
    private Type typeWithoutForestedAreaManagement;
    private int withForestedAreaManagement;
    private Type typeWithForestedAreaManagement;
    private int withoutTotEmissionsForest;
    private int withTotEmissionsForest;
    private int balanceForest;
    private int withoutTotForest;
    private int withTotForest;
    private int balanceTotForest;
    private ForestDegradationLevel degradationLevel;
    private int biomassLost;




}
