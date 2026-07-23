package com.robindas.bloodbridge.Model;

import com.robindas.bloodbridge.Util.Role;
import jakarta.persistence.*;
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

    @Enumerated(EnumType.STRING)
    private Role role;

}
