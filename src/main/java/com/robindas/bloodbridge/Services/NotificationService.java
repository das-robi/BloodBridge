package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.NotificResponse;
import com.robindas.bloodbridge.Exceptions.ResourceNotFoundException;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Notification;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.NotificationRepo;
import com.robindas.bloodbridge.Util.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private UserAuthentication userAuthentication;

    public void createNotification(String notTitle, String notMessage, Users users, LocalDateTime localDateTime, BloodRequest bloodRequest){

        Notification notification = new Notification();

        notification.setNotTitle(notTitle);
        notification.setNotMessage(notMessage);
        notification.setUsers(users);
        notification.setLocalDateTime(localDateTime);
        notification.setBloodRequest(bloodRequest);
        notification.setRead(false);

        notificationRepo.save(notification);
    }


    public void markRead(int id) {

        Users users = userAuthentication.getUserAuthenticated();

        Notification notification = notificationRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Notification not found"));

        notification.setRead(true);
        notificationRepo.save(notification);
    }

    //Crete request accept notific and sending Requester.
    public void createNotificationForAccept(String title, String message, String bloodRequest, String users, Users requester, LocalDateTime localDateTime) {

        Notification notification = new Notification();

        notification.setNotTitle(title);
        notification.setNotMessage(message);
        notification.setBloodgrp(bloodRequest);
        notification.setUsernameDonor(users);
        notification.setUsers(requester);
        notification.setLocalDateTime(localDateTime);

        notificationRepo.save(notification);

        System.out.println("Notification sends to Requester " + notification.getUsers().getUserName() + notification.getUsers().getUserId());
    }

    public List<NotificResponse> getAllNotification() {

        List<Notification> notifications = notificationRepo.findAll();
        List<NotificResponse> responses = new ArrayList<>();

        for (Notification notification : notifications){

            NotificResponse response = new NotificResponse();

            response.setNotTitle(notification.getNotTitle());
            response.setNotMessage(notification.getNotMessage());
            response.setBloodgrp(notification.getBloodgrp());
            response.setRead(notification.isRead());
            response.setUsernameDonor(notification.getUsernameDonor());

            responses.add(response);
        }


        return responses;
    }
}
