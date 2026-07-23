package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.Model.DonationResponse;
import com.robindas.bloodbridge.Services.DonResService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/response")
public class DonResponseController {

    @Autowired
    private DonResService donResService;


    @PostMapping("/accepted/{requestId}")
    public ResponseEntity<String> requestAccepted(@PathVariable int requestId){
        return ResponseEntity.ok(donResService.requestAccepted(requestId));
    }

    @PostMapping("/reject/{requestId}")
    public ResponseEntity<String> requestRejected(@PathVariable int requestId){
        return ResponseEntity.ok(donResService.requestRejected(requestId));
    }

}
