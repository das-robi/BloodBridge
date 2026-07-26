package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.DTO.Users.DonorResponse;
import com.robindas.bloodbridge.DTO.Users.UserResponse;
import com.robindas.bloodbridge.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private UserServices userServices;

    //Users
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userServices.getAllUsers());
    }

//    @GetMapping("/user/view_id:{id}_request_id:{reqId}")
//    public ResponseEntity<String> deleteRequest(@PathVariable int id, @PathVariable int reqId){
//        return new ResponseEntity<>(userServices.deleteRequest(id, reqId),HttpStatus.OK);
//    }

    @GetMapping("/donors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<DonorResponse>> getAllDonor(){
        return ResponseEntity.ok(userServices.getAllDonor());
    }


    @GetMapping("/blood-request")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BldReqResponse>> getAllBloodReq(){
        return ResponseEntity.ok(userServices.getAllBloodReq());
    }

}
