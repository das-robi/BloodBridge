package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Services.AdminService;
import com.robindas.bloodbridge.Services.BldRequestService;
import com.robindas.bloodbridge.Services.DonorService;
import com.robindas.bloodbridge.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private DonorService donorService;

    @Autowired
    private BldRequestService bldRequestService;




    @GetMapping("/all_users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Users>> getAllUsers(){
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @GetMapping("/all_donors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Donor>> getAllDonor(){
        return ResponseEntity.ok(adminService.getAllDonor());
    }


    @GetMapping("/all_blood_request")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<BloodRequest>> getAllBloodReq(){
        return ResponseEntity.ok(adminService.getAllBloodReq());
    }
}
