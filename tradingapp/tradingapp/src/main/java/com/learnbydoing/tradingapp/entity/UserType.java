package com.learnbydoing.tradingapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users_type")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private Integer userTypeId;

    @Column(name = "user_type_name")
    private String userTypeName;

    // Advanced mapping: 1 userType can have many Users. MappedBy = "..." should exist in User clss referencing UserType

    @OneToMany(mappedBy = "userType", cascade = {CascadeType.MERGE, CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public void addUser(User user){
        if(users == null){
            users = new ArrayList<>();
        }

        users.add(user);
        user.setUserType(this);  //Maintain bidirectional relationship
    }

    public UserType() {
    }

    public UserType(int userTypeId, String userTypeName, List<User> users) {
        this.userTypeId = userTypeId;
        this.userTypeName = userTypeName;
        this.users = users;
    }

    public Integer getUserTypeId() {
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
