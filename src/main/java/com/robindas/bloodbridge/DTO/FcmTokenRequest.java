package com.robindas.bloodbridge.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FcmTokenRequest(@NotBlank @Size(max = 4096) String fcmToken) {
}
