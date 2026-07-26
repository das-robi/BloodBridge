package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.*;
import com.robindas.bloodbridge.DTO.LoginRequest;
import com.robindas.bloodbridge.DTO.RegisterRequest;
import com.robindas.bloodbridge.DTO.Users.DonorResponse;
import com.robindas.bloodbridge.DTO.Users.UserRequest;
import com.robindas.bloodbridge.DTO.Users.UserResponse;
import com.robindas.bloodbridge.Exceptions.BadRequestException;
import com.robindas.bloodbridge.Exceptions.ForbiddenException;
import com.robindas.bloodbridge.Exceptions.ResourceNotFoundException;
import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.BloodRequestRepo;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import com.robindas.bloodbridge.Util.Role;
import com.robindas.bloodbridge.Util.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UsersRepository repository;
    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private JwtTokenServices jwtTokenServices;

    @Autowired
    private UserAuthentication userAuthentication;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodRequestRepo bloodRequestRepo;

    @Autowired
    private DonorService donorService;
    @Autowired
    private BldRequestService requestService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);



    //Users
    public Users UserRegister(RegisterRequest response) {

        Users users = new Users();

        users.setUserName(response.getUserName());
        users.setUserEmail(response.getUserEmail());
        users.setPassWord(encoder.encode(response.getPassWord()));
        users.setRole(Role.USER);

        Users saveUsers = repository.save(users);

        System.out.println("Saved Users Id: " + saveUsers.getUserId());

       return saveUsers;
    }


    public String verifyUser(LoginRequest request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassWord()));

        if (authentication.isAuthenticated()){
            return jwtTokenServices.generateKey(request.getUserName());
        }
        else {
            throw new BadRequestException("Invalid your password and name");
        }
    }



    public UserResponse getProfileById() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users users = repository.getUserByUserName(username);

        UserResponse userResponse = new UserResponse();

        repository.findById(users.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));

        userResponse.setUserName(users.getUserName());
        userResponse.setUserEmail(users.getUserEmail());


        return userResponse;
    }

    //Update Profile
    public UserResponse userUpdateProfile(UserRequest request) {

        //check Authentication
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Users users = repository.getUserByUserName(username);
        Users users = userAuthentication.getUserAuthenticated();



        //Update data

        if (request.getUserEmail() == null && request.getUserName() != null){
            users.setUserName(request.getUserName());
            users.setUserEmail(users.getUserEmail());
        }
        else if (request.getUserName() == null && request.getUserEmail() != null){
            users.setUserName(request.getUserName());
            users.setUserEmail(request.getUserEmail());
        }
        else if (request.getUserName() != null && request.getUserEmail() != null){
            users.setUserName(request.getUserName());
            users.setUserEmail(request.getUserEmail());
        }
        else {
            throw new BadRequestException("Can't update");
        }

//        Users existingusers = usersRepository.findById(users.getUserId()).orElseThrow(()-> new ResourceNotFoundException("User not found"));
//
//        existingusers.setUserName(request.getUserName());
//        existingusers.setUserEmail(request.getUserEmail());

        usersRepository.save(users);

        //Convert into Response
        UserResponse response = new UserResponse();

        response.setUserName(users.getUserName());
        response.setUserEmail(users.getUserEmail());


        return response;
    }








    //Admin
    public List<UserResponse> getAllUsers() {

        Users usersAuth = userAuthentication.getUserAuthenticated();

        if (usersAuth.getRole() != Role.ADMIN){
            throw new ForbiddenException("Only admins can perform this action.");
        }

        List<Users> users = usersRepository.findAll();
        List<UserResponse> responses = new ArrayList<>();

        for (Users users1 : users){
            UserResponse response = new UserResponse();

            response.setUserName(users1.getUserName());
            response.setUserEmail(users1.getUserEmail());

            responses.add(response);
        }


        return responses;
    }

    public List<DonorResponse> getAllDonor() {

        userAuthentication.getUserAuthenticated();

        List<Donor> donors = donorRepository.findAll();
        List<DonorResponse> responses = new ArrayList<>();

        for (Donor donors1 : donors){

            DonorResponse response = new DonorResponse();

            response.setDonorName(donors1.getDonorName());
            response.setBldGroup(donors1.getBldGroup());
            response.setCity(donors1.getCity());
            response.setDistrict(donors1.getDistrict());
            response.setPhone(donors1.getPhone());
            response.setLastDonateDate(donors1.getLastDonateDate());
            response.setAvailable(donors1.isAvailable());

            responses.add(response);
        }

        return responses;
    }

    public List<BldReqResponse> getAllBloodReq() {

        Users users = userAuthentication.getUserAuthenticated();

        List<BloodRequest> bloodRequests = bloodRequestRepo.findAll();
        List<BldReqResponse> reqResponses = new ArrayList<>();

        for (BloodRequest request : bloodRequests){

            BldReqResponse response = new BldReqResponse();

            response.setPatientName(request.getPatientName());
            response.setBldGroup(request.getBldGroup());
            response.setStatus(request.getStatus());
            response.setDisease(request.getDisease());
            response.setCity(request.getCity());
            response.setDistrict(request.getDistrict());
            response.setHospital(request.getHospital());
            response.setUnit(request.getUnit());
            response.setRequesterName(request.getRequesterName());

            reqResponses.add(response);

        }

        return reqResponses;
    }

}
