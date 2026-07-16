package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.UserResponse;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/users/profile/{id}")
    public ResponseEntity<Users> getProfile(int id){
        return ResponseEntity.ok(userServices.getProfileById(id));
    }

    @PutMapping("/users/profile/update/{id}")
    public Users updateProfile(@PathVariable int id, @RequestBody UserResponse response){
        return userServices.userUpdateProfile(id, response);
    }

}
