package com.example.sen_touroperator.service;

import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DTO.LandmarkDto;
import com.example.sen_touroperator.models.DTO.LandmarkInfoDto;
import com.example.sen_touroperator.models.DTO.binding.LandmarkBindingDto;

import java.util.List;

public interface LandmarkService {

    List<LandmarkDto> getAllLandmarks();
    void createLandmark(LandmarkDto landmarkDto);
    LandmarkInfoDto findById(Integer id);
    void deleteLandmark(Integer id);
    List<LandmarkDto> getLandmarkByType(String landmarkType);
    List<LandmarkDto> getLandmarkByUserLocation(Integer id);
}
