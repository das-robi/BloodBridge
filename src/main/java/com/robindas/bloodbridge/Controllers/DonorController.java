package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.BldReqDTO;
import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.DTO.DonorRequest;
import com.robindas.bloodbridge.DTO.DonorResponse;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Services.DonorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @GetMapping("/donor/me")
    public ResponseEntity<DonorResponse> donorProfile(){
        return new ResponseEntity<>(donorService.getDonorProfile(), HttpStatus.FOUND);
    }

    @PostMapping("/create-donor")
    public ResponseEntity<DonorResponse> createDonor(@RequestBody DonorRequest request){
        return new ResponseEntity<>(donorService.createDonor(request), HttpStatus.OK);
    }

    @PutMapping("/donor/profile-update")
    public ResponseEntity<DonorResponse> updateProfile(@RequestBody DonorRequest request){
        return ResponseEntity.ok(donorService.updateProfile(request));
    }


    @DeleteMapping("/delete-profile")
    public String deleteDonorProfile(){
        donorService.deleteDonorProfile();

        return "Success";
    }

//    //Search Blood Donor
//    @GetMapping("/search-donor/{keyword}")
//    public ResponseEntity<List<BloodRequest>> getSearch(String keyword){
//        return donorService.searchDonor(keyword);
//    }


    //Request for Blood
    @PostMapping("/blood-request")
    public ResponseEntity<BldReqResponse> requestForBlood(@RequestBody BldReqDTO bldReqDTO){

        return new ResponseEntity<>(donorService.requestForBlood(bldReqDTO), HttpStatus.CREATED);
    }

}
