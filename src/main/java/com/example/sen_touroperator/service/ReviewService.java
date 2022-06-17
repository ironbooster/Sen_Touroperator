package com.example.sen_touroperator.service;

import com.example.sen_touroperator.models.DAO.Review;
import com.example.sen_touroperator.models.DTO.LandmarkDto;
import com.example.sen_touroperator.models.DTO.ReviewDto;
import com.example.sen_touroperator.models.DTO.RewardDto;

import java.util.List;

public interface ReviewService {

    void createReview(ReviewDto reviewDto, Integer landmarkId,Integer userID);
    void deleteReview(Integer id);
}
