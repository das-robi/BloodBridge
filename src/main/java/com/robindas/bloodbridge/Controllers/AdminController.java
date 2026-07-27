package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.DTO.Users.DonorResponse;
import com.robindas.bloodbridge.DTO.Users.UserResponse;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Services.UserServices;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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
    public ResponseEntity<Page<UserResponse>> getAllUsers(@RequestParam int page, @RequestParam int size,
                                                          @RequestParam(defaultValue = "username") String sortBy,
                                                          @RequestParam(defaultValue = "asc") String ascending){
        return ResponseEntity.ok(userServices.getAllUsers(page, size, sortBy, ascending));
    }

//    @GetMapping("/user/view_id:{id}_request_id:{reqId}")
//    public ResponseEntity<String> deleteRequest(@PathVariable int id, @PathVariable int reqId){
//        return new ResponseEntity<>(userServices.deleteRequest(id, reqId),HttpStatus.OK);
//    }

    @GetMapping("/donors")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<DonorResponse>> getAllDonor(@RequestParam int page, @RequestParam int size,
                                                           @RequestParam(defaultValue = "donorName") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String ascending){
        return ResponseEntity.ok(userServices.getAllDonor(page, size, sortBy, ascending));
    }


    @GetMapping("/blood-request")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BldReqResponse>> getAllBloodReq(@RequestParam int page, @RequestParam int size,
                                                               @RequestParam(defaultValue = "BloodGroup") String sortBy,
                                                               @RequestParam(defaultValue = "asc") String ascending){
        return ResponseEntity.ok(userServices.getAllBloodReq(page, size, sortBy, ascending));
    }

}
