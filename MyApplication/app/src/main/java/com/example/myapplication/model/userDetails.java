package com.example.myapplication.model;

public class userDetails {
    private String userId, email;

    public userDetails() {
    }

    public userDetails(String userId, String userEmail) {
        this.userId = userId;
        this.email = userEmail;

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return email;
    }

    public void setUserEmail(String userEmail) {
        this.email = userEmail;
    }


}

