package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.DonorRequest;
import com.robindas.bloodbridge.DTO.DonorResponse;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import com.robindas.bloodbridge.Util.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private NotificationService notificationService;
    
    public DonorResponse getDonorProfile() {

        //Check USER is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = usersRepository.getUserByUserName(username);

        System.out.println("Users Details: " + users);

        Donor donor = donorRepository.findByUsers(users);

        if (donor == null){

            throw new RuntimeException("Don't have your DONOR account");
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

        //Check USER is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = usersRepository.getUserByUserName(username);
        Donor existDonor = donorRepository.findByUsers(users);

        if (existDonor != null){
            throw new RuntimeException("You have already a DONOR profile");
        }

        Donor donor = new Donor();

        donor.setBldGroup(request.getBldGroup());
        donor.setPhone(request.getPhone());
        donor.setCity(request.getCity());
        donor.setDistrict(request.getDistrict());
        donor.setLastDonateDate(request.getLastDonateDate());
        donor.setAvailable(request.isAvailable());
        donor.setUsers(users);
        donor.setRole(Role.DONOR);

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

    //DONOR Update profile
    public Donor updateProfile(DonorRequest request){

        //Check USER is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = usersRepository.getUserByUserName(username);

        Donor donor = donorRepository.findByUsers(users);

//        donor.setDonId(request.);
        donor.setCity(request.getCity());
        donor.setPhone(request.getPhone());
        donor.setDistrict(request.getDistrict());
        donor.setLastDonateDate(request.getLastDonateDate());

        System.out.println("USER Update: " + donor);

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

    //DONOR Profile Delete
    public void deleteDonorProfile() {

        //Check USER is Logged-In
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = usersRepository.getUserByUserName(username);

        Donor donor = donorRepository.findByUsers(users);

        System.out.println("DONOR user: " + donor);

        donorRepository.delete(donor);
    }

}
