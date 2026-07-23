package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.BldReqDTO;
import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Services.BldRequestService;
import com.robindas.bloodbridge.Util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blood_request")
public class BldRequestController {

    @Autowired
    private BldRequestService bldRequestService;

    //Request for Blood
    @PostMapping("/create_request")
    public ResponseEntity<BldReqResponse> requestForBlood(@RequestBody BldReqDTO bldReqDTO){

        return new ResponseEntity<>(bldRequestService.requestForBlood(bldReqDTO), HttpStatus.CREATED);
    }

    //Update blood Request status
    @PutMapping("/update_status/{id}")
    public ResponseEntity<BloodRequest> updateRequestStatus(@PathVariable int id, @RequestBody ResponseStatus status){
        return new ResponseEntity<>(bldRequestService.updateRequestStatus(id, status), HttpStatus.OK);
    }

}
