package com.example.sen_touroperator.models.DTO;

public class LandmarkDto {


    private Integer id;
    private String name;
    private String location;
    private String img;
    private String region;
    private String landmarkType;
    private double avgRating;

    public Integer getId() {
        return id;
    }

    public LandmarkDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LandmarkDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public LandmarkDto setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getImg() {
        return img;
    }

    public LandmarkDto setImg(String img) {
        this.img = img;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public LandmarkDto setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getLandmarkType() {
        return landmarkType;
    }

    public LandmarkDto setLandmarkType(String landmarkType) {
        this.landmarkType = landmarkType;
        return this;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public LandmarkDto setAvgRating(double avgRating) {
        this.avgRating = avgRating;
        return this;
    }
}
