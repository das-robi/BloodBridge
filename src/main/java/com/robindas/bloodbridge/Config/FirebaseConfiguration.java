package com.robindas.bloodbridge.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
@ConditionalOnProperty(name = "firebase.enabled", havingValue = "true")
public class FirebaseConfiguration {

    private static final Logger log = LoggerFactory.getLogger(FirebaseConfiguration.class);

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        // Uses GOOGLE_APPLICATION_CREDENTIALS, keeping the service-account JSON out of Git.
        FirebaseApp app = FirebaseApp.getApps().isEmpty()
                ? FirebaseApp.initializeApp(FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.getApplicationDefault()).build())
                : FirebaseApp.getInstance();

        log.info("Firebase Admin SDK initialized for push notifications");

        return FirebaseMessaging.getInstance(app);
    }
}
