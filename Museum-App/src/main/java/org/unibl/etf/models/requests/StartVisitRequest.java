package org.unibl.etf.models.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StartVisitRequest {

    @NotBlank
    private String ticketNumber;
}
