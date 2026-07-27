package com.robindas.bloodbridge.DTO;

import lombok.Data;

@Data
public class SpecificationResponse {

    private String bldGrp;
    private String district;
    private String city;
    private boolean available;

}
