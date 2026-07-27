package com.robindas.bloodbridge.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank
    private String userName;
    @Email
    private String userEmail;

    @NotBlank(message = "Password")
    @Size(min = 8, max = 20, message = "Password must between be 8 and 20 characters")
    private String passWord;


    public LoginRequest(String userName, String userEmail, String passWord) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.passWord = passWord;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
//

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
