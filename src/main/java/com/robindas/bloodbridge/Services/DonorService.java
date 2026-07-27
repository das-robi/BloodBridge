package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.DonorSearchRequest;
import com.robindas.bloodbridge.DTO.Users.DonorRequest;
import com.robindas.bloodbridge.DTO.Users.DonorResponse;
import com.robindas.bloodbridge.Exceptions.BadRequestException;
import com.robindas.bloodbridge.Exceptions.ResourceNotFoundException;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import com.robindas.bloodbridge.Specification.DonorSpecification;
import com.robindas.bloodbridge.Util.Role;
import com.robindas.bloodbridge.Util.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DonorService {

    @Autowired
    private DonorRepository donorRepository;
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserAuthentication userAuthentication;



    public DonorResponse getDonorProfile() {

        //Check USER is Logged-In
        Users users = userAuthentication.getUserAuthenticated();

//        System.out.println("Users Details: " + users);

        Donor donor = donorRepository.findByUsers(users);

        if (donor == null){

            throw new ResourceNotFoundException("Don't have your DONOR account");
        }

        DonorResponse response = new DonorResponse();

        response.setDonorName(donor.getDonorName());
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
        Users users = userAuthentication.getUserAuthenticated();

        Donor existDonor = donorRepository.findByUsers(users);

        if (existDonor != null){
            throw new BadRequestException("You have already a DONOR profile");
        }

        Donor donor = new Donor();

        donor.setDonorName(users.getUserName());
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

//        response.setDonId(donor.getDonId());
        response.setDonorName(donor.getDonorName());
        response.setPhone(donor.getPhone());
        response.setCity(donor.getCity());
        response.setDistrict(donor.getDistrict());
        response.setLastDonateDate(donor.getLastDonateDate());
        response.setAvailable(donor.isAvailable());

        return response;
    }

    //DONOR Update profile
    public DonorResponse updateProfile(DonorRequest request){

        //Check USER is Logged-In
        Users users = userAuthentication.getUserAuthenticated();

        Donor donor = donorRepository.findByUsers(users);


        if (donor == null) {
            throw new ResourceNotFoundException("Donor profile not found.");
        }

        if (request.getCity() != null) {
            donor.setCity(request.getCity());
        }

        if (request.getDistrict() != null) {
            donor.setDistrict(request.getDistrict());
        }

        if (request.getPhone() != null) {
            donor.setPhone(request.getPhone());
        }

        if (request.getLastDonateDate() != null) {
            donor.setLastDonateDate(request.getLastDonateDate());
        }

        System.out.println("USER Update: " + donor);

        Donor updateDonor = donorRepository.save(donor);

        //Convert to response
        DonorResponse response = new DonorResponse();

        response.setDonorName(users.getUserName());
        response.setCity(updateDonor.getCity());
        response.setDistrict(updateDonor.getDistrict());
        response.setPhone(updateDonor.getPhone());
        response.setLastDonateDate(updateDonor.getLastDonateDate());
        response.setAvailable(updateDonor.isAvailable());



        return response;
    }

    //DONOR Profile Delete
    public void deleteDonorProfile() {

        //Check USER is Logged-In
        Users users = userAuthentication.getUserAuthenticated();

        Donor donor = donorRepository.findByUsers(users);

        System.out.println("DONOR user: " + donor);

        donorRepository.delete(donor);
    }

    public Page<DonorResponse> searchDonorByDistrict(DonorSearchRequest request, int page, int size, String sortBy, String direction) {

        userAuthentication.getUserAuthenticated();

        Specification<Donor> specification = Specification.where((Specification<Donor>) null);

        //Search and Filtering by Blood Group
        if (request.getBldGrp() != null && !request.getBldGrp().isBlank()){
            specification = specification.and(DonorSpecification.hasBloodGroup(request.getBldGrp()));
        }

        //Search and Filtering by District
        if (request.getDistrict() != null && !request.getDistrict().isBlank()){
            specification = specification.and(DonorSpecification.hasDistrict(request.getDistrict()));
        }

        //Search and Filtering by City
        if (request.getCity() != null && !request.getCity().isBlank()){
            specification = specification.and(DonorSpecification.hasCity(request.getCity()));
        }

        //Search and Filtering by available
        if (!request.isAvailable()){
            specification = specification.and(DonorSpecification.hasAvailable(request.isAvailable()));
        }

        //Sorting and Pagination
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

         Page<Donor> donors = donorRepository.findAll(pageable);

         Page<DonorResponse> responses = donors.map(donor ->{

             DonorResponse response = new DonorResponse();

             response.setDonorName(donor.getDonorName());
             response.setBldGroup(donor.getBldGroup());
             response.setPhone(donor.getPhone());
             response.setDistrict(donor.getDistrict());
             response.setCity(donor.getCity());
             response.setAvailable(donor.isAvailable());
             response.setLastDonateDate(donor.getLastDonateDate());

             return response;
         });


//         for (Donor donor : donors){
//
//             DonorResponse response = new DonorResponse();
//
//             response.setDonorName(donor.getDonorName());
//             response.setBldGroup(donor.getBldGroup());
//             response.setAvailable(response.isAvailable());
//             response.setPhone(donor.getPhone());
//             response.setCity(donor.getCity());
//             response.setDistrict(donor.getDistrict());
//             response.setLastDonateDate(donor.getLastDonateDate());
//
//             responses.add(response);
//         }

         return responses;
    }
}
