package com.robindas.bloodbridge.Services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.robindas.bloodbridge.Model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PushNotificationService {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationService.class);
    private final ObjectProvider<FirebaseMessaging> firebaseMessagingProvider;

    public PushNotificationService(ObjectProvider<FirebaseMessaging> firebaseMessagingProvider) {
        this.firebaseMessagingProvider = firebaseMessagingProvider;
    }

    @Async("notificationTaskExecutor")
    public void sendAsync(Users recipient, String title, String body, Map<String, String> data) {

        if (recipient.getFcmToken() == null || recipient.getFcmToken().isBlank()) {
            log.debug("FCM skipped: userId={} has no registered device", recipient.getUserId());
            return;
        }

        FirebaseMessaging messaging = firebaseMessagingProvider.getIfAvailable();

        if (messaging == null) {
            log.debug("FCM skipped: Firebase is disabled");
            return;
        }

        try {

            Message message = Message.builder()
                    .setToken(recipient.getFcmToken())
                    .setNotification(Notification.builder().setTitle(title).setBody(body).build())
                    .putAllData(data)
                    .build();
            String messageId = messaging.send(message);
            log.info("FCM sent: event={}, userId={}, messageId={}", data.get("event"), recipient.getUserId(), messageId);

        }
        catch (Exception exception) {

            // Push delivery must never fail the blood-request transaction.
            log.warn("FCM delivery failed: event={}, userId={}", data.get("event"), recipient.getUserId(), exception);
        }
    }
}
