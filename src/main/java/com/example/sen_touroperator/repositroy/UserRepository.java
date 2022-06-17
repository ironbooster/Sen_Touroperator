package com.example.sen_touroperator.repositroy;

import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DAO.User;
import com.example.sen_touroperator.models.DTO.user.UserLoginDto;
import com.example.sen_touroperator.models.DTO.user.UserRegisterDto;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByUsername(String userName);
    Optional<User> findUserById(Integer id);
    void registerUser(User userRegisterDto);
    void visitLandmark(String landmarkName,Integer userId);
    void earnReward(Integer rewardId,Integer userId);
    void setUserRegion(Integer id,String regionName);


}
