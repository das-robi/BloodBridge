package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.Users.UserRequest;
import com.robindas.bloodbridge.DTO.Users.UserResponse;
import com.robindas.bloodbridge.Services.UserServices;
import jakarta.validation.Valid;
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
    @PutMapping("/me")
    public UserResponse updateProfile(@Valid @RequestBody UserRequest request){
        return userServices.userUpdateProfile(request);
    }

}
