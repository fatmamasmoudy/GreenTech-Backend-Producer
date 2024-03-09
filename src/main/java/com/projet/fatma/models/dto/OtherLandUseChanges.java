package com.projet.fatma.models.dto;

import com.projet.fatma.models.OtherLandUseChangesProducer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherLandUseChanges {

    private OtherLandUseChangesProducer otherLandUseChanges ;
    private String eventType ;
}
