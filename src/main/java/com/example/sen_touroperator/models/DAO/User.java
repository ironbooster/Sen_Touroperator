package com.example.sen_touroperator.models.DAO;

import java.util.ArrayList;
import java.util.List;

public class User {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role;
    private String region;
    private List<Landmark> visitedLandmarks;
    private List<Reward> rewardsList;

    public User(Integer id,
                String username,
                String email,
                String password,
                String role,
                String region) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.region = region;
    }
    public User(){

    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setRole(String role) {
        this.role = role;
        return this;
    }

    public User setRegion(String region) {
        this.region = region;
        return this;
    }

    public User setVisitedLandmarks(List<Landmark> visitedLandmarks) {
        this.visitedLandmarks = visitedLandmarks;
        return this;
    }

    public User setRewardsList(List<Reward> rewardsList) {
        this.rewardsList = rewardsList;
        return this;
    }

    public String getRole() {
        return role;
    }

    public List<Reward> getRewardsList() {
        return rewardsList;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRoles() {
        return role;
    }

    public String getRegion() {
        return region;
    }

    public List<Landmark> getVisitedLandmarks() {
        return visitedLandmarks;
    }
}
