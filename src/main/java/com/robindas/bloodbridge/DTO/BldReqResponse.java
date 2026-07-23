package com.robindas.bloodbridge.DTO;

import com.robindas.bloodbridge.Util.ResponseStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private ResponseStatus status;

}
