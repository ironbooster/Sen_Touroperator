package com.example.sen_touroperator.repositroy;

import com.example.sen_touroperator.models.DAO.Review;

import java.util.List;

public interface ReviewRepository {
    void createReviewOnALandmark(Review review , Integer landmarkId,Integer userId);
    void deleteReview(Integer id);
    List<Integer> getAvgRatingOfLandmark(Integer landmarkId);
    List<Review> getAllReviewsFromPostById(Integer landmarkId);
}
