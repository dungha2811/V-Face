package com.example.vface.java.entity;

public class User{

    private String id;
    private String emailAddress;
    private String username;
    private String phoneNumber;
    private String password;
    private String faceVector;
    private String gender;
    private String dateOfBirth;
    private String imageLink;


    public User() {
    }

    public User(String id, String emailAddress, String username, String phoneNumber, String password, String faceVector, String gender, String dateOfBirth, String imageLink) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.faceVector = faceVector;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.imageLink = imageLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFaceVector() {
        return faceVector;
    }

    public void setFaceVector(String faceVector) {
        this.faceVector = faceVector;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
