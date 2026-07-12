package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.LoginRequest;
import com.robindas.bloodbridge.DTO.RegisterRequest;
import com.robindas.bloodbridge.Services.UserServices;
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
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest response){
        userServices.UserRegister(response);

        return ResponseEntity.ok("Register Success");
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginRequest response){
       return userServices.verifyUser(response);
    }

}
