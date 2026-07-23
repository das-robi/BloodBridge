package com.robindas.bloodbridge.Model;

import com.robindas.bloodbridge.Util.ResponseStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class DonationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int donatId;
    private LocalDate responseTime;

    @Enumerated(EnumType.STRING)
    private ResponseStatus responseStatus;

    @ManyToOne
    @JoinColumn(name = "requestId")
    private BloodRequest bloodRequest;

    @ManyToOne
    @JoinColumn(name = "donId")
    private Donor donor;

}
