package com.robindas.bloodbridge.DTO;

public class RegisterRequest {

    private String userName;
    private String passWord;
    private String userEmail;

    public RegisterRequest(String userName, String passWord, String userEmail) {
        this.userName = userName;
        this.passWord = passWord;
        this.userEmail = userEmail;
    }

    public RegisterRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
