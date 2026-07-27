package com.robindas.bloodbridge.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DonorSearchRequest {

    private String bldGrp;
    private String district;
    private String city;
    private boolean available;

}
