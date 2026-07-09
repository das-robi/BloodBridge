package com.robindas.bloodbridge.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int userId;
    private String userName;
    private String passWord;
    private String userEmail;
    private String role;

}
