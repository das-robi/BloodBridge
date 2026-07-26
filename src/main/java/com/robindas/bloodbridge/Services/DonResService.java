package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.Exceptions.BadRequestException;
import com.robindas.bloodbridge.Exceptions.ForbiddenException;
import com.robindas.bloodbridge.Exceptions.ResourceNotFoundException;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.DonationResponse;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.BloodRequestRepo;
import com.robindas.bloodbridge.Repositories.DonResRepository;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import com.robindas.bloodbridge.Util.ResponseStatus;
import com.robindas.bloodbridge.Util.Role;
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
    DonorService donorService;


    //Blood Request Accepted
    public String requestAccepted(int requestId) {

        //Checking here user are authenticated or not
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = usersRepository.getUserByUserName(username);

        //check DONOR profile
        Donor donor = donorRepository.findByUsers(users);

        //check bloodRequest
        BloodRequest bloodRequest = bloodRequestRepo.findById(requestId).orElseThrow(()-> new RuntimeException("Blood Request not found"));

        if (donor == null){
            throw new ForbiddenException("Only donor can accept the request");
        }

        //Check if already accept
        if (donResRepository.existsByBloodRequestAndDonor(bloodRequest, donor)){
            return "You already accepted the request.";
        }

        //Check if already accept request
        if (bloodRequest.getStatus() == ResponseStatus.Accepted){
//            throw new RuntimeException("Blood Request is already accepted by: " + donor.getUsers().getUserName());
            return "Blood Request is already accepted";
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



            notificationService.createNotificationForAccept(
                    "Blood Request Status Accepted",
                    donor.getUsers().getUserName() + "has accept your blood donation request",
                    bloodRequest.getBldGroup(),
                    donor.getUsers().getUserName()
            );

        return "Blood Request Accepted";
    }

    public String requestRejected(int requestId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = usersRepository.getUserByUserName(username);

        Donor donor = donorRepository.findByUsers(users);

        BloodRequest bloodRequest = bloodRequestRepo.findById(requestId).orElseThrow(()-> new ResourceNotFoundException("Blood Request not found"));

        if (donResRepository.existsByBloodRequestAndDonor(bloodRequest, donor)){
            throw new BadRequestException("You already rejected the request");
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
