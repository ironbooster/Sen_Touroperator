package com.example.sen_touroperator.models.DTO;

import java.util.List;

public class LandmarkInfoDto {
    private Integer id;
    private String name;
    private String location;
    private String img;
    private String region;
    private String landmarkType;
    private double avgRating;
    private List<ReviewDto> landmarkReviews;
    private BestHotelInRegionDto bestHotelInRegion;

    public BestHotelInRegionDto getBestHotelInRegion() {
        return bestHotelInRegion;
    }

    public LandmarkInfoDto setBestHotelInRegion(BestHotelInRegionDto bestHotelInRegion) {
        this.bestHotelInRegion = bestHotelInRegion;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public LandmarkInfoDto setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public LandmarkInfoDto setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public LandmarkInfoDto setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getImg() {
        return img;
    }

    public LandmarkInfoDto setImg(String img) {
        this.img = img;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public LandmarkInfoDto setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getLandmarkType() {
        return landmarkType;
    }

    public LandmarkInfoDto setLandmarkType(String landmarkType) {
        this.landmarkType = landmarkType;
        return this;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public LandmarkInfoDto setAvgRating(double avgRating) {
        this.avgRating = avgRating;
        return this;
    }

    public List<ReviewDto> getLandmarkReviews() {
        return landmarkReviews;
    }

    public LandmarkInfoDto setLandmarkReviews(List<ReviewDto> landmarkReviews) {
        this.landmarkReviews = landmarkReviews;
        return this;
    }
}
