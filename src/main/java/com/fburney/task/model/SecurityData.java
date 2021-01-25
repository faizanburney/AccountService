package com.fburney.task.model;

import lombok.Data;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@Document
public class SecurityData {
    @Id
    private String id;
    private String name;
    private String symbol;
    private Integer volume;
    private Date startTime;
    private Date   endTime;
    private Double currentPrice;
    private Double highPrice;
    private Double lowPrice;
}
