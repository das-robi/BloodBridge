package com.robindas.bloodbridge.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int bldId;

    private String patientName;
    private String bldGroup;
    private String city;
    private String hospital;
    private String status;
    private String createBy;

}
