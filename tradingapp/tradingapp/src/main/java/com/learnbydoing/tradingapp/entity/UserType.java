package com.learnbydoing.tradingapp.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users_type")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private int userTypeId;

    @Column(name = "user_type_name")
    private String userTypeName;

    // Advanced mapping: 1 userType can have many Users. MappedBy = "..." should exist in User clss referencing UserType
    @OneToMany(mappedBy = "userType")
    private List<User> users;

    public UserType() {
    }

    public UserType(int userTypeId, String userTypeName, List<User> users) {
        this.userTypeId = userTypeId;
        this.userTypeName = userTypeName;
        this.users = users;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId=" + userTypeId +
                ", userTypeName='" + userTypeName + '\'' +
                ", users=" + users +
                '}';
    }
}
