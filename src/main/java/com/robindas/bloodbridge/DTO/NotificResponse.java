package com.robindas.bloodbridge.DTO;

import lombok.Data;

@Data
public class NotificResponse {

    private String notTitle;
    private String notMessage;
    private boolean isRead;

    private String bloodgrp;
    private String usernameDonor;


}
