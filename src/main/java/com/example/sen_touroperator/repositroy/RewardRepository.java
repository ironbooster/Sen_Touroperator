package com.example.sen_touroperator.repositroy;

import com.example.sen_touroperator.models.DAO.Reward;
import com.example.sen_touroperator.models.DTO.RewardDto;

import java.util.List;
import java.util.Optional;

public interface RewardRepository {
    List<Reward> getAllRewards();
    void createReward(Reward rewardDto);
    void deleteReward(Integer id);
    Reward getRewardById(Integer id);
    void redeemReward(Integer rewardId,Integer userId);

}
