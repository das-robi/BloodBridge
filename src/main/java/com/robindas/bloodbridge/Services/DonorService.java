package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.BldReqDTO;
import com.robindas.bloodbridge.DTO.BldReqResponse;
import com.robindas.bloodbridge.DTO.DonorRequest;
import com.robindas.bloodbridge.DTO.DonorResponse;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.BloodRequestRepo;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BloodRequestRepo bldRepository;
    @Autowired
    private NotificationService notificationService;
    
    public DonorResponse getDonorProfile() {

        //Check User is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = usersRepository.getUserByUserName(username);

        System.out.println("Users Details: " + users);

        Donor donor = donorRepository.getDonorByUsers(users);

        if (donor == null){

            throw new RuntimeException("Don't have your Donor account");
        }

        DonorResponse response = new DonorResponse();

        response.setBldGroup(donor.getBldGroup());
        response.setCity(donor.getCity());
        response.setDistrict(donor.getDistrict());
        response.setPhone(donor.getPhone());
        response.setLastDonateDate(donor.getLastDonateDate());
        response.setAvailable(donor.isAvailable());

        return response;
    }

    public DonorResponse createDonor(DonorRequest request) {

        //Check User is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = usersRepository.getUserByUserName(username);
        Donor existDonor = donorRepository.getDonorByUsers(users);

        if (existDonor != null){
            throw new RuntimeException("You have already a Donor profile");
        }

        Donor donor = new Donor();

        donor.setBldGroup(request.getBldGroup());
        donor.setPhone(request.getPhone());
        donor.setCity(request.getCity());
        donor.setDistrict(request.getDistrict());
        donor.setLastDonateDate(request.getLastDonateDate());
        donor.setAvailable(request.isAvailable());
        donor.setUsers(users);

        //Save users
        donor = donorRepository.save(donor);

        System.out.println("Donors: " + donor);

        //Convert it into response
        DonorResponse response = new DonorResponse();

        response.setDonId(donor.getDonId());
        response.setPhone(donor.getPhone());
        response.setCity(donor.getCity());
        response.setDistrict(donor.getDistrict());
        response.setLastDonateDate(donor.getLastDonateDate());
        response.setAvailable(donor.isAvailable());

        return response;
    }

    //Donor Update profile
    public Donor updateProfile(DonorRequest request){

        //Check User is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = usersRepository.getUserByUserName(username);

        Donor donor = donorRepository.getDonorByUsers(users);

//        donor.setDonId(request.);
        donor.setCity(request.getCity());
        donor.setPhone(request.getPhone());
        donor.setDistrict(request.getDistrict());
        donor.setLastDonateDate(request.getLastDonateDate());

        System.out.println("User Update: " + donor);

        //Convert to response
//        DonorResponse response = new DonorResponse();
//
//        response.setDonId(donor.getDonId());
//        response.setCity(request.getCity());
//        response.setDistrict(request.getDistrict());
//        response.setPhone(request.getPhone());
//        response.setLastDonateDate(request.getLastDonateDate());
//        response.setAvailable(donor.isAvailable());

        return donorRepository.save(donor);
    }

    //Donor Profile Delete
    public void deleteDonorProfile() {

        //Check User is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = usersRepository.getUserByUserName(username);

        Donor donor = donorRepository.getDonorByUsers(users);

        System.out.println("Donor user: " + donor);

        donorRepository.delete(donor);
    }


    //Saving Users BloodRequest and Matching Request Blood Group and District
    public BldReqResponse requestForBlood(BldReqDTO bldReqDTO) {

        //Check User is Logged-In
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
        saveRequest.setStatus("Pending");

        saveRequest.setCreatedBy(users);

        //Save Blood Request
        saveRequest = bldRepository.save(saveRequest);

        //Finding matching Donor
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
        response.setStatus(saveRequest.getStatus());

        System.out.println("Finding All Donor: " + donor.size());

        for (Donor donor1 : donor){

            //Debug
            System.out.println("Donor BloodGroup: " + donor1.getBldGroup());
            System.out.println("Donor City: " + donor1.getCity());
            System.out.println("Donor District " + donor1.getDistrict());
            System.out.println("Donor Phone " + donor1.getPhone());
            System.out.println("Donor last date of blood donate" + donor1.getLastDonateDate());


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

}
