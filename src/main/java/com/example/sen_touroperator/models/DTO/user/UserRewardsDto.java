package com.example.sen_touroperator.models.DTO.user;

import com.example.sen_touroperator.models.DAO.Reward;

import java.util.List;

public class UserRewardsDto {
    private String username;
    private String role;
    private List<Reward> rewardsList;


    public String getUsername() {
        return username;
    }

    public UserRewardsDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserRewardsDto setRole(String role) {
        this.role = role;
        return this;
    }

    public List<Reward> getRewardsList() {
        return rewardsList;
    }

    public UserRewardsDto setRewardsList(List<Reward> rewardsList) {
        this.rewardsList = rewardsList;
        return this;
    }
}
