package com.example.sen_touroperator.models.DTO;

import java.util.List;

public class VisitedLandmarkDto {
    private Integer id;
    private String name;
    private String img;
    private String region;
    private String landmarkType;
    private double avgRating;

    public Integer getId() {
        return id;
    }

    public VisitedLandmarkDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public VisitedLandmarkDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getImg() {
        return img;
    }

    public VisitedLandmarkDto setImg(String img) {
        this.img = img;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public VisitedLandmarkDto setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getLandmarkType() {
        return landmarkType;
    }

    public VisitedLandmarkDto setLandmarkType(String landmarkType) {
        this.landmarkType = landmarkType;
        return this;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public VisitedLandmarkDto setAvgRating(double avgRating) {
        this.avgRating = avgRating;
        return this;
    }
}
