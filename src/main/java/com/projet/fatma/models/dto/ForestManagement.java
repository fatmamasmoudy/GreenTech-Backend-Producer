package com.projet.fatma.models.dto;
import com.projet.fatma.models.ForestManagementProducerr;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class ForestManagement {
    private ForestManagementProducerr forestManagement ;
    private String eventType ;

}
