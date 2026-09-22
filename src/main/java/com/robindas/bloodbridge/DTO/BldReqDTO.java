package com.robindas.bloodbridge.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
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
    @NotNull @DecimalMin("-90.0") @DecimalMax("90.0")
    private Double latitude;
    @NotNull @DecimalMin("-180.0") @DecimalMax("180.0")
    private Double longitude;
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
