package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.LoginRequest;
import com.robindas.bloodbridge.DTO.RegisterRequest;
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
    public String loginUser(@Valid @RequestBody LoginRequest request){
        System.out.println("Login API HIT");

        System.out.println("Usrename: " +  request.getUserName());
        System.out.println("Useremail: " + request.getUserEmail());
        System.out.println("Password: " + request.getPassWord());

       return userServices.verifyUser(request);
    }

}
