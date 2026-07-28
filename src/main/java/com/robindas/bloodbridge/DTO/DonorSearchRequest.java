package com.robindas.bloodbridge.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class DonorSearchRequest {

    private String bldGrp;
    private String district;
    private String city;
    private Boolean available;

}
