package com.example.sen_touroperator.service.impl;

import com.example.sen_touroperator.exception_handler.exceptions.LandmarkException;
import com.example.sen_touroperator.exception_handler.exceptions.UserException;
import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DAO.User;
import com.example.sen_touroperator.models.DTO.LandmarkDto;
import com.example.sen_touroperator.models.DTO.LandmarkInfoDto;
import com.example.sen_touroperator.models.DTO.ReviewDto;
import com.example.sen_touroperator.models.DTO.binding.LandmarkBindingDto;
import com.example.sen_touroperator.repositroy.LandmarkRepository;
import com.example.sen_touroperator.repositroy.ReviewRepository;
import com.example.sen_touroperator.repositroy.UserRepository;
import com.example.sen_touroperator.repositroy.mySql.MySQLLandmarkRepository;
import com.example.sen_touroperator.service.LandmarkService;
import com.example.sen_touroperator.service.ThirdPartyApiService;
import com.example.sen_touroperator.service.qr_service.QrService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LandmarkServiceImpl implements LandmarkService {

    private final QrService qrService;
    private final LandmarkRepository landmarkRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;
    private final ThirdPartyApiService thirdPartyApiService;

    public LandmarkServiceImpl(LandmarkRepository landmarkRepository,
                               ReviewRepository reviewRepository,
                               UserRepository userRepository,
                               ModelMapper modelMapper,
                               ThirdPartyApiService thirdPartyApiService,
                               QrService qrService) {
        this.landmarkRepository = landmarkRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.qrService = qrService;
        this.reviewRepository = reviewRepository;
        this.thirdPartyApiService = thirdPartyApiService;
    }

    @Override
    public List<LandmarkDto> getAllLandmarks() {
        List<Landmark> landmarkList = landmarkRepository.getAllLandmarks();
        List<LandmarkDto> landmarkDtos = landmarkList.stream()
                .map((landmark) -> modelMapper.map(landmark, LandmarkDto.class)).toList();

        landmarkDtos.forEach(landmarkDto -> landmarkDto
                .setAvgRating(calculateAvgRating(landmarkDto.getId())));
        return landmarkDtos;

    }

    @Override
    public void createLandmark(LandmarkDto landmarkBindingDto) {
        Landmark landmarkDao = modelMapper.map(landmarkBindingDto,Landmark.class);
        String visitLinkEndPoint ="http:/localhost:8080/visit-landmark/"+landmarkDao.getName();


        String qrBytes = Base64.getEncoder()
                .encodeToString(qrService.generateByteQRCode(visitLinkEndPoint,250,250));
        landmarkDao.setQrCode(qrBytes);
        landmarkDao.setVisitings(0);
        landmarkRepository.createLandmark(landmarkDao);
    }

    @Override
    public LandmarkInfoDto findById(Integer id) {
        Landmark landmark = landmarkRepository.getLandmarkById(id).orElse(null);
        if(landmark == null){
            throw new LandmarkException("Landmark doesn't exist");
        }
        LandmarkInfoDto landmarkInfoDto = modelMapper.map(landmark,LandmarkInfoDto.class);
        landmarkInfoDto.setBestHotelInRegion(thirdPartyApiService.
                bestHotelInCurrentRegion(landmarkInfoDto.getRegion()));

        List<ReviewDto> landmarkReviews = reviewRepository
                .getAllReviewsFromPostById(landmarkInfoDto.getId())
                .stream()
                .map(ln->modelMapper.map(ln,ReviewDto.class)).toList();

        landmarkInfoDto.setLandmarkReviews(landmarkReviews);

        landmarkInfoDto.setAvgRating(calculateAvgRating(landmarkInfoDto.getId()));
        return landmarkInfoDto;
    }

    @Override
    public void deleteLandmark(Integer id) {
        Landmark landmark = landmarkRepository.getLandmarkById(id).orElse(null);
        if(landmark == null){
            throw new LandmarkException("Landmark does not exist");
        }
        landmarkRepository.deleteLandmark(id);
    }

    @Override
    public List<LandmarkDto> getLandmarkByType(String landmarkType) {
        List<Landmark> landmarkList = landmarkRepository.getLandmarksByType(landmarkType);
        List<LandmarkDto> landmarkDtos = landmarkList.stream()
                .map((landmark) -> modelMapper.map(landmark, LandmarkDto.class)).toList();

        landmarkDtos.forEach(landmarkDto -> landmarkDto
                .setAvgRating(calculateAvgRating(landmarkDto.getId())));
        return landmarkDtos;
    }

    @Override
    public List<LandmarkDto> getLandmarkByUserLocation(Integer id) {
        User user = userRepository.findUserById(id).orElse(null);
        if(user == null){
            throw new UserException("User not found");
        }
        List<Landmark> landmarkList = landmarkRepository.getLandmarksByUserLocation(user.getRegion());
        List<LandmarkDto> landmarkDtos = landmarkList.stream()
                .map((landmark) -> modelMapper.map(landmark, LandmarkDto.class)).toList();

        landmarkDtos.forEach(landmarkDto -> landmarkDto
                .setAvgRating(calculateAvgRating(landmarkDto.getId())));
        return landmarkDtos;
    }
    private double calculateAvgRating(Integer landmarkId){
        return Math.round( reviewRepository
                .getAvgRatingOfLandmark(landmarkId)
                .stream()
                .mapToDouble( d->d)
                .average()
                .orElse(0.00));
    }
}
