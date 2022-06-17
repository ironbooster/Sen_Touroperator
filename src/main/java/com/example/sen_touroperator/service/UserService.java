package com.example.sen_touroperator.service;

import com.example.sen_touroperator.models.DTO.RewardDto;
import com.example.sen_touroperator.models.DTO.user.UserLoginDto;
import com.example.sen_touroperator.models.DTO.user.UserProfileDto;
import com.example.sen_touroperator.models.DTO.user.UserRegisterDto;
import com.example.sen_touroperator.models.DTO.user.UserRewardsDto;

import java.util.List;

public interface UserService {

    void registerUser (UserRegisterDto userServiceDto);


    UserProfileDto userProfileInfo(Integer id);

    UserRewardsDto getAllRewardsFromUser(Integer id);
    void setUserRegion(Integer id ,String regionName);

    void visitLandmark(String landmarkName,Integer userId);
    void redeemReward(Integer rewardId,Integer userId);
}
