package org.unibl.etf.models.responses;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class VirtualVisitResponse {
    private Integer id;
    private Date date;
    private Time startTime;
    private Integer duration;
    private Double price;
    private Integer museumId;
    private boolean active = false;
}
