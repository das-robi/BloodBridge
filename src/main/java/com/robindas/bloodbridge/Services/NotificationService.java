package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Notification;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    public void createNotification(String notTitle, String notMessage, Users users, BloodRequest bloodRequest){

        Notification notification = new Notification();

        notification.setNotTitle(notTitle);
        notification.setNotMessage(notMessage);
        notification.setUsers(users);
        notification.setBloodRequest(bloodRequest);

        notificationRepo.save(notification);
    }


    public void markRead(int id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();



    }

    public void createNotificationForAccept(String title, String message, String bloodRequest, String users) {
        Notification notification = new Notification();

        notification.setNotTitle(title);
        notification.setNotMessage(message);
        notification.setBloodgrp(bloodRequest);
        notification.setUsernameDonor(users);
    }
}
