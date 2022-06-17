package com.example.sen_touroperator.models.DTO;

public class ReviewDto {
    private int stars;
    private String description;

    public int getStars() {
        return stars;
    }

    public ReviewDto setStars(int stars) {
        this.stars = stars;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ReviewDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
