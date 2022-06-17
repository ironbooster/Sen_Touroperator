package com.example.sen_touroperator.service.impl;

import com.example.sen_touroperator.exception_handler.exceptions.RewardException;
import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DAO.Reward;
import com.example.sen_touroperator.models.DTO.LandmarkDto;
import com.example.sen_touroperator.models.DTO.RewardDto;
import com.example.sen_touroperator.repositroy.RewardRepository;
import com.example.sen_touroperator.service.RewardService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RewardServiceImpl implements RewardService {
    private final RewardRepository rewardRepository;
    private final ModelMapper modelMapper;

    public RewardServiceImpl(RewardRepository rewardRepository, ModelMapper modelMapper) {
        this.rewardRepository = rewardRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createReward(RewardDto rewardDto) {

        Reward reward = modelMapper.map(rewardDto,Reward.class);
        try {
            rewardRepository.createReward(reward);
        }catch (DataIntegrityViolationException e){
            throw new RewardException("Reward already exists");
        }
    }
    @Override
    public List<RewardDto> getRewardList() {
        List<Reward> rewards = rewardRepository.getAllRewards();
        return rewards
                .stream()
                .map(reward -> modelMapper.map(reward, RewardDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteReward(Integer id) {
        rewardRepository.deleteReward(id);
    }

    @Override
    public RewardDto rewardInfo(Integer id) {
        Reward reward =  rewardRepository.getRewardById(id);
        return modelMapper.map(reward,RewardDto.class);
    }

}
