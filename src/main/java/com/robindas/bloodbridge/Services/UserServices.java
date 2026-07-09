package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.LoginResponse;
import com.robindas.bloodbridge.DTO.RegisterResponse;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UsersRepository repository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtTokenServices jwtTokenServices;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users UserRegister(RegisterResponse response) {

        Users users = new Users();

        users.setUserName(response.getUserName());
        users.setUserEmail(response.getUserEmail());
        users.setPassWord(encoder.encode(response.getPassWord()));

        Users saveUsers = repository.save(users);

        System.out.println("Saved Users Id: " + saveUsers.getUserId());
//
       return saveUsers;
    }


    public String verifyUser(LoginResponse response) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(response.getUserName(), response.getPassWord()));

        if (authentication.isAuthenticated()){
           return jwtTokenServices.generateKey(response.getUserName());
        }
        return "Fail";
    }

    public List<Users> getAllUsers() {

        return repository.findAll();
    }
}
