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
@Table(name="grasslandSystemsTotal")

public class GrasslandProducer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stringId ;
    private String descriptionGrassland;
    private String startGrasslandManagement;
    private String withoutGrasslandManagement;
    private String withGrasslandManagement;
    private Boolean withoutFireManagement;
    static final int year = 5;
    private Boolean withFireManagement;
    static final int year2 = 5;
    private Double startYield;
    private Double withoutYield;
    private Double withYield;
    private Double startAreaGrassland;
    private Double withoutAreaGrassland;
    private Double withAreaGrassland;
    private Double withoutTotEmissionsGrassland;
    private Double withTotEmissionsGrassland;
    private Double balanceGrassland;
    private Double totGrasslandSystemWithout;
    private Double totGrasslandSystemWith;
    private Double totGrasslandSystemBalance;


}
