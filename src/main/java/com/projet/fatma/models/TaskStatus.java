package com.projet.fatma.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatus implements Serializable {
    @JsonProperty("taskId")
    private Long taskId;
    @JsonProperty("taskName")
    private String taskName;
    @JsonProperty("percentageComplete")
    private float percentageComplete;
    @JsonProperty("status")
    private Status status;

    public enum Status {
        SUBMITTED, STARTED, RUNNING, FINISHED, TERMINATED
    }
}
