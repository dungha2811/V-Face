package com.example.vface.java.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    private String id;
    private String emailAddress;
    private String username;
    private String phoneNumber;
    private String password;
    private String faceVector;
    private String gender;
    private String dateOfBirth;


    public User() {
    }

    private User(Parcel in){
        String[] data = new String[8];
        this.id = data[0];
        this.emailAddress = data[1];
        this.username = data[2];
        this.phoneNumber = data[3];
        this.password = data[4];
        this.faceVector = data[5];
        this.gender = data[6];
        this.dateOfBirth = data[7];
        in.readStringArray(data);
    }

    public User(String id, String emailAddress, String username, String phoneNumber, String password, String faceVector, String gender, String dateOfBirth) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.faceVector = faceVector;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.emailAddress);
        parcel.writeString(this.phoneNumber);
        parcel.writeString(this.password);
        parcel.writeString(this.faceVector);
        parcel.writeString(this.gender);
        parcel.writeString(this.dateOfBirth);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };
}
