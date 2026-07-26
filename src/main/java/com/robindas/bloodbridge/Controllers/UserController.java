package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.UserResponse;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PreAuthorize("hasAnyRole('USER','DONOR')")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getProfile(){
        return ResponseEntity.ok(userServices.getProfileById());
    }

    @PreAuthorize("hasAnyRole('USER','DONOR')")
    @PutMapping("/profile/me/{id}")
    public Users updateProfile(@PathVariable int id, @RequestBody UserResponse response){
        return userServices.userUpdateProfile(id, response);
    }

}
