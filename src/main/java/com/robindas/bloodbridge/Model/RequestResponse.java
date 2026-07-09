package com.robindas.bloodbridge.Model;

import jakarta.persistence.*;

@Entity
public class RequestResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int reqId;

    @OneToOne
    @JoinColumn(name = "req_id")
    private BloodRequest bloodRequest;

    @OneToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

}
