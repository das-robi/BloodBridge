package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.UserResponse;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/users/profile/{id}")
    public ResponseEntity<UserResponse> getProfile(){
        return ResponseEntity.ok(userServices.getProfile());
    }

    @PutMapping("/users/profile/update/{id}")
    public UserResponse updateProfile(@PathVariable int id, @RequestBody UserResponse response){

        return userServices.userUpdateProfile(id, response);
    }

}
