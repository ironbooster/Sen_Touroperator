package com.example.sen_touroperator.models.DAO;

public class Review {
    private Integer id;
    private int stars;
    private String description;

    public Review(Integer id, int stars, String description) {
        this.id = id;
        this.stars = stars;
        this.description = description;
    }
    public Review(){

    }

    public Review setId(Integer id) {
        this.id = id;
        return this;
    }

    public Review setStars(int stars) {
        this.stars = stars;
        return this;
    }

    public Review setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public int getStars() {
        return stars;
    }

    public String getDescription() {
        return description;
    }
}
