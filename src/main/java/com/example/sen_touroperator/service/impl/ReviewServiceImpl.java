package com.example.sen_touroperator.service.impl;

import com.example.sen_touroperator.exception_handler.exceptions.LandmarkException;
import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DAO.Review;
import com.example.sen_touroperator.models.DTO.ReviewDto;
import com.example.sen_touroperator.models.DTO.RewardDto;
import com.example.sen_touroperator.repositroy.LandmarkRepository;
import com.example.sen_touroperator.repositroy.ReviewRepository;
import com.example.sen_touroperator.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final LandmarkRepository landmarkRepository;
    private final ModelMapper modelMapper;

    public ReviewServiceImpl(ReviewRepository reviewRepository,
                             LandmarkRepository landmarkRepository,
                             ModelMapper modelMapper) {
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
        this.landmarkRepository = landmarkRepository;
    }

    @Override
    public void createReview(ReviewDto rewardDto, Integer landmarkId,Integer userId) {
        Review review = modelMapper.map(rewardDto,Review.class);
        Landmark landmark = landmarkRepository.getLandmarkById(landmarkId).orElse(null);
        if(landmark == null){
            throw new LandmarkException("Landmark doesn't exist");
        }
        reviewRepository.createReviewOnALandmark(review,landmarkId,userId);
    }
    @Override
    public void deleteReview(Integer id) {
        reviewRepository.deleteReview(id);
    }
}
