package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.BldReqDTO;
import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.BloodRequestRepo;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.NotificationRepo;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import com.robindas.bloodbridge.Util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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



    //Saving Users BloodRequest and Matching Request Blood Group and District
    public BldReqResponse requestForBlood(BldReqDTO bldReqDTO) {

        //Check USER is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = usersRepository.getUserByUserName(username);

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
                    saveRequest);



            //Check Notification are sending
            System.out.println("Notification Sent to users: " + donor1.getUsers().getUserName());
        }

        return response;
    }

    //Update Request Status
    public BloodRequest updateRequestStatus(int id, ResponseStatus status) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = usersRepository.getUserByUserName(username);

        BloodRequest bloodRequest = bloodRequestRepo.findById(id).orElseThrow(()-> new RuntimeException("Blood request not found"));

        bloodRequest.setStatus(status);

        return bloodRequestRepo.save(bloodRequest);

    }
}
