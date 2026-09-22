package com.robindas.bloodbridge.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.robindas.bloodbridge.Util.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int donId;

    @OneToOne
    @JoinColumn(name = "user_id")
    public Users users;

    private String donorName;
    private String bldGroup;
    private String city;
    private String district;

    // WGS84 coordinates supplied by Android. PostGIS uses longitude first, latitude second.
    @Column(columnDefinition = "double precision")
    private Double latitude;

    @Column(columnDefinition = "double precision")
    private Double longitude;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate lastDonateDate;
    private String phone;
    private boolean available;
}
