package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.LoginRequest;
import com.robindas.bloodbridge.DTO.RegisterRequest;
import com.robindas.bloodbridge.DTO.UserResponse;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private UsersRepository repository;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtTokenServices jwtTokenServices;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Users UserRegister(RegisterRequest response) {

        Users users = new Users();

        users.setUserName(response.getUserName());
        users.setUserEmail(response.getUserEmail());
        users.setPassWord(encoder.encode(response.getPassWord()));

        Users saveUsers = repository.save(users);

        System.out.println("Saved Users Id: " + saveUsers.getUserId());
//
       return saveUsers;
    }


    public String verifyUser(LoginRequest response) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(response.getUserName(), response.getPassWord()));

        if (authentication.isAuthenticated()){
           return jwtTokenServices.generateKey(response.getUserName());
        }
        return "Fail";
    }

    public UserResponse getProfile() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users users = repository.getUserByUserName(username);

        UserResponse response = new UserResponse();

        response.setUserName(users.getUserName());
        response.setUserEmail(users.getUserEmail());

        return response;
    }

    //Update Profile
    public UserResponse userUpdateProfile(int id, UserResponse response) {

        //check Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        //Find User
        Users users = repository.getUserByUserName(username);

        //Update data
        users.setUserName(response.getUserName());
        users.setUserEmail(response.getUserEmail());

        //Convert to response
        response.setUserName(users.getUserName());
        response.setUserEmail(users.getUserEmail());

        return response;
    }
}
