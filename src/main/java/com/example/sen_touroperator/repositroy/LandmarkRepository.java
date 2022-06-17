package com.example.sen_touroperator.repositroy;

import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DTO.LandmarkDto;
import com.example.sen_touroperator.models.DTO.binding.LandmarkBindingDto;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface LandmarkRepository {
    List<Landmark> getAllLandmarks();
    void createLandmark(Landmark landmarkDao);
    Optional<Landmark> getLandmarkById(Integer id);
    List<Landmark> getLandmarksByType(String landmarkType);
    void deleteLandmark(Integer id);
    List<Landmark> getLandmarksByUserLocation(String userId);
}
