package org.unibl.etf.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MuseumRequest {

    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotBlank
    private String tel;
    @NotBlank
    private String city;
    @NotBlank
    private String country;
    @NotBlank
    private Double geolat;
    @NotBlank
    private Double geolng;
    @NotBlank
    private String type;
}
