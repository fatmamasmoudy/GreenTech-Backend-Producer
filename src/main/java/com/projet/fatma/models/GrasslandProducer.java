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
    private String userNotesGrassland;


    private Boolean withoutFireMang2;
    static final int year3 = 5;
    private Boolean withFireMang2;
    static final int year4 = 5;
    private int startYield2;
    private int withoutYield2;
    private int withYield2;
    private int startAreaGrassland2;
    private int withoutAreaGrassland2;
    private Type typeWithoutAreaGrassland2;
    private int withAreaGrassland2;
    private Type typeWithAreaGrassland2;
    private int withoutTotEmissionsGrassland2;
    private int withTotEmissionsGrassland2;
    private int balanceGrassland2;
    private int withoutTotGrasslandSys;
    private int withTotGrasslandSys;
    private int balanceTotGrasslandSys;

}
