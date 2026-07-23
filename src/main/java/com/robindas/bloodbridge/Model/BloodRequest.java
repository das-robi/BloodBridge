package com.robindas.bloodbridge.Model;

import com.robindas.bloodbridge.Util.ResponseStatus;
import jakarta.persistence.*;
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
    private String district;
    private String hospital;
    private String unit;
    private String disease;

    @Enumerated(EnumType.STRING)
    private ResponseStatus status;

//    @ManyToMany
//    @JoinColumn(name = "create_by")
//    private Users createBy;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users createdBy;

}
