package com.robindas.bloodbridge.Services;

import com.robindas.bloodbridge.DTO.LoginRequest;
import com.robindas.bloodbridge.DTO.RegisterRequest;
import com.robindas.bloodbridge.DTO.UserResponse;
import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

       return saveUsers;
    }


    public String verifyUser(LoginRequest request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassWord()));

        if (authentication.isAuthenticated()){
            return jwtTokenServices.generateKey(request.getUserName());
        }

        return "User not found";
    }

    public Users getProfileById(int id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Users users = repository.getUserByUserName(username);

        return repository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
    }

    //Update Profile
    public Users userUpdateProfile(int id, UserResponse response) {

        //check Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Users users = repository.getUserByUserName(username);


        Users existingUsers = repository.findById(id).orElseThrow(()-> new RuntimeException("Something went wrong"));

        //Update data
        existingUsers.setUserName(response.getUserName());
        existingUsers.setUserEmail(response.getUserEmail());


        return repository.save(existingUsers);
    }
}
