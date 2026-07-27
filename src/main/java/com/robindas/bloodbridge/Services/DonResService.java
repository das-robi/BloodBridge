package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.Exceptions.BadRequestException;
import com.robindas.bloodbridge.Exceptions.ConflictException;
import com.robindas.bloodbridge.Exceptions.ForbiddenException;
import com.robindas.bloodbridge.Exceptions.ResourceNotFoundException;
import com.robindas.bloodbridge.Model.*;
import com.robindas.bloodbridge.Repositories.BloodRequestRepo;
import com.robindas.bloodbridge.Repositories.DonResRepository;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import com.robindas.bloodbridge.Util.ResponseStatus;
import com.robindas.bloodbridge.Util.Role;
import com.robindas.bloodbridge.Util.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DonResService {

    @Autowired
    private DonResRepository donResRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodRequestRepo bloodRequestRepo;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private BldRequestService bldRequestService;

    @Autowired
    private UserAuthentication userAuthentication;


    //Blood Request Accepted
    public String requestAccepted(int requestId) {

        //Checking here user are authenticated or not
        Users users = userAuthentication.getUserAuthenticated();

        //check DONOR profile
        Donor donor = donorRepository.findByUsers(users);

        //check bloodRequest
        BloodRequest bloodRequest = bloodRequestRepo.findById(requestId).orElseThrow(()-> new RuntimeException("Blood Request not found"));

        if (donor == null){
            throw new ForbiddenException("Only donor can accept the request");
        }

        //Check if already accept
        if (donResRepository.existsByBloodRequestAndDonor(bloodRequest, donor)){
            throw new BadRequestException("You have already accepted request");
        }

        //Check if already accept request
        if (bloodRequest.getStatus() == ResponseStatus.Accepted){
            throw new ConflictException("Blood Request is already accepted");
        }


        //save DonationResponse
            DonationResponse response = new DonationResponse();

            response.setBloodRequest(bloodRequest);
            response.setDonor(donor);
            response.setResponseStatus(ResponseStatus.Accepted);
            response.setResponseTime(LocalDate.from(LocalDateTime.now()));

            donResRepository.save(response);


            //Updated Blood Request status
            bldRequestService.updateRequestStatus(
                    bloodRequest.getBldId(),
                    ResponseStatus.Accepted
            );


            Users requesters = bloodRequest.getCreatedBy();

              notificationService.createNotificationForAccept(
                    "Blood Request Accepted",
                    donor.getUsers().getUserName() + "has accept your blood donation request",
                    bloodRequest.getBldGroup(),
                    donor.getUsers().getUserName(),
                    requesters,
                    LocalDateTime.now()
            );



        return "Blood Request Accepted";
    }

    public String requestRejected(int requestId) {

        Users users = userAuthentication.getUserAuthenticated();

        Donor donor = donorRepository.findByUsers(users);

        BloodRequest bloodRequest = bloodRequestRepo.findById(requestId).orElseThrow(()-> new ResourceNotFoundException("Blood Request not found"));

        if (donResRepository.existsByBloodRequestAndDonor(bloodRequest, donor)){
            throw new BadRequestException("You have already rejected the request");
        }



        DonationResponse response = new DonationResponse();

        response.setBloodRequest(bloodRequest);
        response.setDonor(donor);
        response.setResponseStatus(ResponseStatus.Rejected);
        response.setResponseTime(LocalDate.from(LocalDateTime.now()));

        donResRepository.save(response);

        //updated Request
        bldRequestService.updateRequestStatus(
                bloodRequest.getBldId(),
                ResponseStatus.Rejected
        );

        return "Blood Request Rejected";
    }
}
