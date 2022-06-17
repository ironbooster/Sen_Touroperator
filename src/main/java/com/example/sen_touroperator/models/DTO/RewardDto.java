package com.example.sen_touroperator.models.DTO;

public class RewardDto {
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public RewardDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RewardDto setDescription(String description) {
        this.description = description;
        return this;
    }
}
