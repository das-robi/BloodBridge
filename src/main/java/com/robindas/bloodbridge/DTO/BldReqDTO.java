package com.robindas.bloodbridge.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BldReqDTO {

    @NotBlank
    private String patientName;
    @NotBlank
    private String bldGroup;
    @NotBlank
    private String city;
    @NotBlank
    private String district;
    @NotBlank
    private String hospital;
    @NotBlank
    @Min(1)
    private String unit;
    @NotBlank
    private String disease;
    @NotBlank
    private String status;

}
