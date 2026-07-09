package com.robindas.bloodbridge.Controllers;

import com.robindas.bloodbridge.Model.Users;
import com.robindas.bloodbridge.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getAllUsers(){
        return new ResponseEntity<>(userServices.getAllUsers(), HttpStatus.OK);
    }

}
