package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.DonorRequest;
import com.robindas.bloodbridge.DTO.DonorResponse;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private UsersRepository usersRepository;
    
    public DonorResponse getDonorProfile() {

        //check authentication and get Name
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //Find user by username and also find donor by user and set Donors details
        Users users = usersRepository.getUserByUserName(username);

        System.out.println("Users Details: " + users);

        Donor donor = donorRepository.getDonorByUsers(users);

//        System.out.println("Donors Info : " + donor);


        if (donor == null){

            throw new RuntimeException("Dont have your Donor account");
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

    public DonorResponse updateProfile(DonorRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Users users = usersRepository.getUserByUserName(username);

        Donor donor = donorRepository.getDonorByUsers(users);

//        donor.setDonId(request.get);
        donor.setCity(request.getCity());
        donor.setPhone(request.getPhone());
        donor.setDistrict(request.getDistrict());
        donor.setLastDonateDate(request.getLastDonateDate());

        donor = donorRepository.save(donor);

        //Convert to response
        DonorResponse response = new DonorResponse();

        response.setDonId(donor.getDonId());
        response.setCity(request.getCity());
        response.setDistrict(request.getDistrict());
        response.setPhone(request.getPhone());
        response.setLastDonateDate(request.getLastDonateDate());
        response.setAvailable(donor.isAvailable());

        return response;
    }

    public void deleteDonorProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = usersRepository.getUserByUserName(username);

        Donor donor = donorRepository.getDonorByUsers(users);

        System.out.println("Donor user: " + donor);

        donorRepository.delete(donor);
    }
}
