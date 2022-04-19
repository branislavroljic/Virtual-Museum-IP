package org.unibl.etf.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Artifact {
    private String name;
    private String type;
    private byte[] bytes;
    private String uri;
}
