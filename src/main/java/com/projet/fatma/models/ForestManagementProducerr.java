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
    private String forestVegetation;
    private String startForestDegradationLevel;
    private String withoutForestDegradationLevel;
    private String withForestDegradationLevel;

    private Boolean withoutFireOccurrence;
    private Boolean withFireOccurrence;

    static final int FirePeriodicityWithout = 1;
    static final int FirePeriodicityWith = 7;
    private Double withoutFireImpact;
    private Double withFireImpact;
    private Double startForestedAreaManagement;
    private Double withoutForestedAreaManagement;
    private String typeWithoutForestedAreaManagement;
    private Double withForestedAreaManagement;
    private String typeWithForestedAreaManagement;
    private Double withoutTotEmissionsForest;
    private Double withTotEmissionsForest;
    private Double balanceForest;
    private Double withoutTotForest;
    private Double withTotForest;
    private Double balanceTotForest;
    private String degradationLevel;
    private int biomassLost;




}
