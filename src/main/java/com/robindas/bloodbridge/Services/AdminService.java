package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.Model.BloodRequest;
import com.robindas.bloodbridge.Model.Donor;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.BloodRequestRepo;
import com.robindas.bloodbridge.Repositories.DonorRepository;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private DonorRepository donorRepository;

    @Autowired
    private BloodRequestRepo bloodRequestRepo;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public List<Donor> getAllDonor() {
        return donorRepository.findAll();
    }

    public List<BloodRequest> getAllBloodReq() {
        return bloodRequestRepo.findAll();
    }
}
