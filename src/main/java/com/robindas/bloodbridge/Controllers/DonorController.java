package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.DTO.DonorSearchRequest;
import com.robindas.bloodbridge.DTO.Users.DonorRequest;
import com.robindas.bloodbridge.DTO.Users.DonorResponse;
import com.robindas.bloodbridge.Services.DonorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<DonorResponse> createDonor(@Valid @RequestBody DonorRequest request){
        return new ResponseEntity<>(donorService.createDonor(request), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('DONOR','USER')")
    @PutMapping("/me")
    public ResponseEntity<DonorResponse> updateProfile(@Valid @RequestBody DonorRequest request){

        return new ResponseEntity<>(donorService.updateProfile(request), HttpStatus.OK);
    }


    @PreAuthorize("hasAnyRole('DONOR','USER')")
    @DeleteMapping("/delete")
    public String deleteDonorProfile(){
        donorService.deleteDonorProfile();

        return "Success";
    }

    //Search Blood DONOR
    @GetMapping("/search")
    public ResponseEntity<Page<DonorResponse>> getSearch(DonorSearchRequest request, @RequestParam int page, @RequestParam int size,
                                                         @RequestParam String sortBy, @RequestParam String direction){
        return new ResponseEntity<>(donorService.searchDonorByDistrict(request, page, size, sortBy, direction), HttpStatus.OK);
    }

}
