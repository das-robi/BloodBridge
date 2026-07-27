package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.BldReqDTO;
import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.Exceptions.ResourceNotFoundException;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.BloodRequestRepo;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import com.robindas.bloodbridge.Util.ResponseStatus;
import com.robindas.bloodbridge.Util.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BldRequestService {

    @Autowired
    private BloodRequestRepo bloodRequestRepo;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserAuthentication userAuthentication;



    //Saving Users BloodRequest and Matching Request Blood Group and District
    public BldReqResponse requestForBlood(BldReqDTO bldReqDTO) {

        //Check USER is Logged-In
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
        Users users = userAuthentication.getUserAuthenticated();

        //Set users saveRequest into BloodRequest Entity
        BloodRequest saveRequest = new BloodRequest();

        saveRequest.setPatientName(bldReqDTO.getPatientName());
        saveRequest.setBldGroup(bldReqDTO.getBldGroup());
        saveRequest.setCity(bldReqDTO.getCity());
        saveRequest.setDistrict(bldReqDTO.getDistrict());
        saveRequest.setDisease(bldReqDTO.getDisease());
        saveRequest.setHospital(bldReqDTO.getHospital());
        saveRequest.setUnit(bldReqDTO.getUnit());
        saveRequest.setStatus(ResponseStatus.Pending);
        saveRequest.setRequesterName(users.getUserName());

        saveRequest.setCreatedBy(users);

        //Save Blood Request
        saveRequest = bloodRequestRepo.save(saveRequest);

        //Finding matching DONOR
        List<Donor> donor = donorRepository.findByBldGroupAndDistrict(
                saveRequest.getBldGroup(), saveRequest.getDistrict());


        BldReqResponse response = new BldReqResponse();

        response.setPatientName(saveRequest.getPatientName());
        response.setBldGroup(saveRequest.getBldGroup());
        response.setBldGroup(saveRequest.getDisease());
        response.setHospital(saveRequest.getHospital());
        response.setUnit(saveRequest.getUnit());
        response.setDistrict(saveRequest.getDistrict());
        response.setCity(saveRequest.getCity());
        response.setStatus(ResponseStatus.Pending);
        response.setRequesterName(saveRequest.getRequesterName());

        System.out.println("Finding All DONOR: " + donor.size());

        for (Donor donor1 : donor){

            //Debug
            System.out.println("DONOR BloodGroup: " + donor1.getBldGroup());
            System.out.println("DONOR City: " + donor1.getCity());
            System.out.println("DONOR District " + donor1.getDistrict());
            System.out.println("DONOR Phone " + donor1.getPhone());
            System.out.println("DONOR last date of blood donate" + donor1.getLastDonateDate());


            //Create Notification and Send matching users
            notificationService.createNotification(
                    "Urgent Need Blood",
                    "A patient need " + saveRequest.getBldGroup() + "blood at " + saveRequest.getHospital(),
                    donor1.getUsers(),
                    LocalDateTime.now(),
                    saveRequest
            );



            //Check Notification are sending
            System.out.println("Notification Sent to Donors: " + donor1.getUsers().getUserName());
        }

        return response;
    }

    //Update Request Status
    public BloodRequest updateRequestStatus(int id, ResponseStatus status) {
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
        Users users = userAuthentication.getUserAuthenticated();

        BloodRequest bloodRequest = bloodRequestRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Blood request not found"));

        bloodRequest.setStatus(status);

        return bloodRequestRepo.save(bloodRequest);

    }

    public BloodRequest getRequests(int id) {

        userAuthentication.getUserAuthenticated();

        return bloodRequestRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found "));
    }

    public List<BldReqResponse> getAllRequests() {

        userAuthentication.getUserAuthenticated();

        List<BloodRequest> bloodRequests = bloodRequestRepo.findAll();
        List<BldReqResponse> reqResponses = new ArrayList<>();

        for (BloodRequest request : bloodRequests){

            BldReqResponse response = new BldReqResponse();

            response.setPatientName(request.getPatientName());
            response.setBldGroup(request.getBldGroup());
            response.setDisease(request.getDisease());
            response.setStatus(request.getStatus());
            response.setHospital(request.getHospital());
            response.setUnit(request.getUnit());
            response.setDistrict(request.getDistrict());
            response.setCity(request.getCity());
            response.setRequesterName(request.getCreatedBy().getUserName());

            reqResponses.add(response);
        }


        return reqResponses;
    }

    public String deleteRequest(int id) {

        userAuthentication.getUserAuthenticated();

        BloodRequest request = bloodRequestRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Blood Request not found"));
        System.out.println("Blood Request Details: " + request.getRequesterName());

        bloodRequestRepo.delete(request);

        return "Deleted Success";
    }

}
