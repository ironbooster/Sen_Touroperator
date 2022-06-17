package com.example.sen_touroperator.models.DAO;

import java.util.List;

public class Landmark {

    private Integer id;
    private String name;
    private String location;
    private String img;
    private String region;
    private String qrCode;
    private String landmarkType;
    private Integer visitings;
    private List<Review> reviewList;

    public Landmark(Integer id,
                    String name,
                    String location,
                    String img,
                    String region,
                    String qrCode,
                    String landmarkType,
                    Integer visitings) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.img = img;
        this.region = region;
        this.qrCode = qrCode;
        this.landmarkType = landmarkType;
        this.visitings = visitings;
    }
    public Landmark(){

    }

    public Integer getId() {
        return id;
    }

    public Landmark setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Landmark setName(String name) {
        this.name = name;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public Landmark setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getImg() {
        return img;
    }

    public Landmark setImg(String img) {
        this.img = img;
        return this;
    }

    public String getRegion() {
        return region;
    }

    public Landmark setRegion(String region) {
        this.region = region;
        return this;
    }

    public String getQrCode() {
        return qrCode;
    }

    public Landmark setQrCode(String qrCode) {
        this.qrCode = qrCode;
        return this;
    }

    public String getLandmarkType() {
        return landmarkType;
    }

    public Landmark setLandmarkType(String landmarkType) {
        this.landmarkType = landmarkType;
        return this;
    }

    public Integer getVisitings() {
        return visitings;
    }

    public Landmark setVisitings(Integer visitings) {
        this.visitings = visitings;
        return this;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public Landmark setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        return this;
    }
}
