package com.robindas.bloodbridge.DTO;

public class LoginResponse{

    private String userName;
    private String userEmail;
    private String passWord;


    public LoginResponse(String userName, String userEmail, String passWord) {
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

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
}
