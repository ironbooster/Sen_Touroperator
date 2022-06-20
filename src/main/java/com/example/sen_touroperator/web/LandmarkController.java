package com.example.sen_touroperator.web;

import com.example.sen_touroperator.config.security.jwt.UserTokenIdManager;
import com.example.sen_touroperator.exception_handler.exceptions.AccessDeniedException;
import com.example.sen_touroperator.models.DTO.LandmarkDto;
import com.example.sen_touroperator.models.DTO.LandmarkInfoDto;
import com.example.sen_touroperator.models.DTO.ReviewDto;
import com.example.sen_touroperator.service.LandmarkService;
import com.example.sen_touroperator.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/landmark")
@CrossOrigin
public class LandmarkController {

    @Autowired
    LandmarkService landmarkService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserTokenIdManager userTokenIdManager;


    @PostMapping("/review/{landmarkID}")
    public String writeReview(@PathVariable Integer landmarkID,
                            @RequestBody ReviewDto reviewDto,
                            @RequestHeader("Authorization") String token){
        Integer userId = userTokenIdManager.getIdFromToken(token);
        reviewService.createReview(reviewDto,landmarkID,userId );
        return "Succesfull";
    }
    @DeleteMapping("/review/delete/{id}")
    public void deleteReview(@PathVariable Integer id,
                             @RequestHeader("Authorization") String token){
        String userRole = userTokenIdManager.getRoleFromToken(token);
        if(!userRole.equals("admin")){
            throw new AccessDeniedException();
        }
        reviewService.deleteReview(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLandmark(@PathVariable Integer id,
                               @RequestHeader("Authorization") String token){
        String userRole = userTokenIdManager.getRoleFromToken(token);
        if(!userRole.equals("admin")){
            throw new AccessDeniedException();
        }
        landmarkService.deleteLandmark(id);
    }

    @GetMapping("/info/{id}")
    public LandmarkInfoDto landmarkInfo(@PathVariable Integer id){


        return landmarkService.findById(id);
    }

    @PostMapping("/create")
    public void createLandmark(@RequestBody LandmarkDto landmarkDto,
                               @RequestHeader("Authorization") String token){
        String userRole = userTokenIdManager.getRoleFromToken(token);
        if(!userRole.equals("admin")){
            throw new AccessDeniedException();
        }
        landmarkService.createLandmark(landmarkDto);
    }

    @GetMapping("/all")
    public List<LandmarkDto> allLandmarks(){
       return landmarkService.getAllLandmarks();
    }

    @GetMapping("/type")
    public List<LandmarkDto> landmarkByType(@RequestParam String landmarkType){
        return landmarkService.getLandmarkByType(landmarkType);
    }

    @GetMapping("/region")
    public List<LandmarkDto> getAllLandmarksInUsersRegion(@RequestHeader("Authorization") String token){
        Integer userId = userTokenIdManager.getIdFromToken(token);
        return landmarkService.getLandmarkByUserLocation(userId);

    }

}
