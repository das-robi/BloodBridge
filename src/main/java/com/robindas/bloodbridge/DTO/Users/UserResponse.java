package com.robindas.bloodbridge.DTO.Users;

public class UserResponse {

    private String userName;
    private String userEmail;

    public UserResponse(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public UserResponse() {
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
}
