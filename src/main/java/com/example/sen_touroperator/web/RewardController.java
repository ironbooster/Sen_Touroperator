package com.example.sen_touroperator.web;

import com.example.sen_touroperator.config.security.jwt.UserTokenIdManager;
import com.example.sen_touroperator.exception_handler.exceptions.AccessDeniedException;
import com.example.sen_touroperator.models.DTO.RewardDto;
import com.example.sen_touroperator.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rewards")
@CrossOrigin
public class RewardController {
    @Autowired
    RewardService rewardService;
    @Autowired
    private UserTokenIdManager userTokenIdManager;

    @GetMapping("/all")
    public List<RewardDto> allRewards(@RequestHeader("Authorization") String token){
        String userRole = userTokenIdManager.getRoleFromToken(token);
        if(!userRole.equals("admin")){
            throw new AccessDeniedException();
        }
        return rewardService.getRewardList();
    }

    @PostMapping("/create")
    public void createReward(@RequestHeader("Authorization") String token, @RequestBody RewardDto rewardDto){
        String userRole = userTokenIdManager.getRoleFromToken(token);
        if(!userRole.equals("admin")){
            throw new AccessDeniedException();
        }
        rewardService.createReward(rewardDto);
    }

    @GetMapping("/info/{rewardId}")
    public RewardDto rewardInfo(@PathVariable Integer rewardId){
       return rewardService.rewardInfo(rewardId);
    }
    @DeleteMapping("/delete/{rewardId}")
    public void deleteReward(@PathVariable Integer rewardId,@RequestHeader("Authorization") String token){
        String userRole = userTokenIdManager.getRoleFromToken(token);
        if(!userRole.equals("admin")){
            throw new AccessDeniedException();
        }
        rewardService.deleteReward(rewardId);
    }
}
