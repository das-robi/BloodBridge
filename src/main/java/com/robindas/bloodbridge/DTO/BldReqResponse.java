package com.robindas.bloodbridge.DTO;

import lombok.Data;

@Data
public class BldReqResponse {

    private String patientName;
    private String bldGroup;
    private String city;
    private String district;
    private String hospital;
    private String unit;
    private String disease;
    private String status;

}
