package com.example.sen_touroperator.models.DTO.user;

import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DTO.LandmarkInfoDto;
import com.example.sen_touroperator.models.DTO.VisitedLandmarkDto;

import java.util.List;

public class UserProfileDto {
    private String username;
    private String region;
    private String role;
    private List<VisitedLandmarkDto> visitedLandmarks;

    public String getUsername() {
        return username;
    }

    public UserProfileDto setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public UserProfileDto setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserProfileDto setRole(String role) {
        this.role = role;
        return this;
    }

    public List<VisitedLandmarkDto> getVisitedLandmarks() {
        return visitedLandmarks;
    }

    public UserProfileDto setVisitedLandmarks(List<VisitedLandmarkDto> visitedLandmarks) {
        this.visitedLandmarks = visitedLandmarks;
        return this;
    }
}
