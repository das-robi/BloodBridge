package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.BldReqDTO;
import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.DTO.DonorRequest;
import com.robindas.bloodbridge.DTO.DonorResponse;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Services.DonorService;
import com.robindas.bloodbridge.Util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/donors")
public class DonorController {

    @Autowired
    private DonorService donorService;

    @PreAuthorize("hasAnyRole('DONOR','USER')")
    @GetMapping("/me")
    public ResponseEntity<DonorResponse> donorProfile(){
        return new ResponseEntity<>(donorService.getDonorProfile(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/me")
    public ResponseEntity<DonorResponse> createDonor(@RequestBody DonorRequest request){
        return new ResponseEntity<>(donorService.createDonor(request), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('DONOR','USER')")
    @PutMapping("/donor/me")
    public ResponseEntity<DonorResponse> updateProfile(@RequestBody DonorRequest request){

        return new ResponseEntity<>(donorService.updateProfile(request), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('DONOR','USER')")
    @DeleteMapping("/me")
    public String deleteDonorProfile(){
        donorService.deleteDonorProfile();

        return "Success";
    }

//    //Search Blood DONOR
//    @GetMapping("/search-donor/{keyword}")
//    public ResponseEntity<List<BloodRequest>> getSearch(String keyword){
//        return donorService.searchDonor(keyword);
//    }

}
