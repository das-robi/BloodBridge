package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.DTO.BldReqDTO;
import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Services.BldRequestService;
import com.robindas.bloodbridge.Util.ResponseStatus;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blood-request")
public class BldRequestController {

    @Autowired
    private BldRequestService bldRequestService;

    //Request for Blood
    @PostMapping("/create")
    public ResponseEntity<BldReqResponse> requestForBlood(@Valid @RequestBody BldReqDTO bldReqDTO){

        return new ResponseEntity<>(bldRequestService.requestForBlood(bldReqDTO), HttpStatus.CREATED);
    }

    //Update blood Request status
    @PutMapping("/{id}/status")
    public ResponseEntity<BloodRequest> updateRequestStatus(@Valid @PathVariable int id, @RequestBody ResponseStatus status){
        return new ResponseEntity<>(bldRequestService.updateRequestStatus(id, status), HttpStatus.OK);
    }

    //Get All BloodRequest
    @GetMapping("/{id}")
    public ResponseEntity<BloodRequest> getRequest(@PathVariable int id){
        return ResponseEntity.ok(bldRequestService.getRequests(id));
    }

    //Get All BloodRequest
    @GetMapping("/all")
    public ResponseEntity<List<BldReqResponse>> getAllRequest(){
        return ResponseEntity.ok(bldRequestService.getAllRequests());
    }

//    Get All BloodRequest
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequest(@PathVariable int id){
        return ResponseEntity.ok(bldRequestService.deleteRequest(id));
    }


}
