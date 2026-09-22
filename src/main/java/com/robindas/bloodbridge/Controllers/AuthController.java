package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.LoginRequest;
import com.robindas.bloodbridge.DTO.RegisterRequest;
import com.robindas.bloodbridge.DTO.AuthResponse;
import com.robindas.bloodbridge.DTO.RefreshTokenRequest;
import com.robindas.bloodbridge.Services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest response){

        userServices.UserRegister(response);

        return ResponseEntity.ok("Register Success");
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@Valid @RequestBody LoginRequest request){
       return userServices.verifyUser(request);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(userServices.refreshAccessToken(request.getRefreshToken()));
    }

}
