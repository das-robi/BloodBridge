package com.robindas.bloodbridge.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String notTitle;
    private String notMessage;
    private boolean isRead;

    private String bloodgrp;
    private String usernameDonor;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "blood_request_id")
    private BloodRequest bloodRequest;

}
