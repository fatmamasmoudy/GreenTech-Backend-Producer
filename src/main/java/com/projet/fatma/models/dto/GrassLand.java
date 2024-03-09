package com.projet.fatma.models.dto;
import com.projet.fatma.models.GrasslandProducer;
import com.projet.fatma.models.OtherLandUseChangesProducer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrassLand {
    private GrasslandProducer grassland ;
    private String eventType ;
}
