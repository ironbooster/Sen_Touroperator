package com.example.sen_touroperator.service;

import com.example.sen_touroperator.models.DTO.RewardDto;

import java.util.List;

public interface RewardService {
    void createReward(RewardDto rewardDto);
    List<RewardDto> getRewardList();
    void deleteReward(Integer id);

    RewardDto rewardInfo(Integer id);
}
