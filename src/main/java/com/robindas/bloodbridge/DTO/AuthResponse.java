package com.robindas.bloodbridge.DTO;

public record AuthResponse(String accessToken, String refreshToken, String tokenType, long expiresIn) {
}
