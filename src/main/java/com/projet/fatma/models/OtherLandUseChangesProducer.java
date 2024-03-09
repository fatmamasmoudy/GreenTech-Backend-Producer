package com.projet.fatma.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="other_landuse")
public class OtherLandUseChangesProducer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    private String stringId ;
    private String userNotes;
    private Boolean fireUsed;
    private LandUseType initialLandUse3;
    private LandUseType finalLandUse3;
    private int withoutAreaLandUse;
    private Type typeWithoutAreaLandUse;
    private int withAreaLandUse;
    private Type typeWithAreaLandUse;
    private int withoutTotEmissions3;
    private int withTotEmissions3;
    private int balance3;
    private int withoutTotNonForestLandUse;
    private int withTotNonForestLandUse;
    private int balanceTotNonForestLandUse;
}
