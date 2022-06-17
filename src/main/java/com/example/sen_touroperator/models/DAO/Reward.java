package com.example.sen_touroperator.models.DAO;

public class Reward {
    private Integer id;
    private String title;
    private String description;

    public Reward(Integer id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    public Reward(){

    }

    public Reward setId(Integer id) {
        this.id = id;
        return this;
    }

    public Reward setTitle(String title) {
        this.title = title;
        return this;
    }

    public Reward setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
