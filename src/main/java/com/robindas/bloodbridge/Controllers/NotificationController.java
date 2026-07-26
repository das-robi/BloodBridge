package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.NotificResponse;
import com.robindas.bloodbridge.Model.Notification;
import com.robindas.bloodbridge.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/all")
    public ResponseEntity<List<NotificResponse>> getAllNotification(){
        return ResponseEntity.ok(notificationService.getAllNotification());
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<String> markRead(@PathVariable int id){
        notificationService.markRead(id);

        return ResponseEntity.ok("Notification marked as Read");
    }

}
