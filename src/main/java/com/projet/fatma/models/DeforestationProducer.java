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
@Table(name="deforestation")
public class DeforestationProducer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stringId ;
    private String climate;
    private String vegetationUsed;
    private float hwps;
    private boolean fireUsed;
    private String landUseType;
    private String agroforestrySystem;
    private double startForestedArea1;
    private double withoutForestedArea1;
    private String typeWithout1;
    private double withForestedArea1;
    private String typeWith1;
    private double withoutDeforestedArea1;
    private double withDeforestedArea1;
    private double withoutTotEmissions1;
    private double withTotEmissions1;
    private double balance1;

}