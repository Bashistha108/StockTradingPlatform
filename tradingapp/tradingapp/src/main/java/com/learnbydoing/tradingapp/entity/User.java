package com.learnbydoing.tradingapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "user_id")
    private int userId;

    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_photo", length = 255)
    private String profilePhoto;

    @ManyToOne //Many users can have one userType
    @JoinColumn(name = "user_type_id")  //referencing the PK of the other table
    private UserType userType;   // Creating a column because it exist as FK


    public User() {
    }

    public User(int userId, String email, boolean isActive, String password, LocalDateTime registrationDate, String firstName, String lastName, String profilePhoto, UserType userType) {
        this.userId = userId;
        this.email = email;
        this.isActive = isActive;
        this.password = password;
        this.registrationDate = registrationDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePhoto = profilePhoto;
        this.userType = userType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", password='" + password + '\'' +
                ", registrationDate=" + registrationDate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", profilePhoto='" + profilePhoto + '\'' +
                ", userType=" + userType +
                '}';
    }
}
