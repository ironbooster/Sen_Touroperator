package com.example.sen_touroperator.models.DTO;

public class BestHotelInRegionDto {
    private String name;
    private double rating;


    public String getName() {
        return name;
    }

    public BestHotelInRegionDto setName(String name) {
        this.name = name;
        return this;
    }

    public double getRating() {
        return rating;
    }

    public BestHotelInRegionDto setRating(double rating) {
        this.rating = rating;
        return this;
    }
}
