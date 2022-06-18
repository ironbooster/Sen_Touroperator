package com.example.sen_touroperator.web;

import com.example.sen_touroperator.config.security.PasswordHash;
import com.example.sen_touroperator.config.security.jwt.JwtUtil;
import com.example.sen_touroperator.config.security.MyUserSec;
import com.example.sen_touroperator.config.security.jwt.UserTokenIdManager;
import com.example.sen_touroperator.exception_handler.exceptions.UserException;
import com.example.sen_touroperator.models.DTO.user.UserProfileDto;
import com.example.sen_touroperator.models.DTO.user.UserRegisterDto;
import com.example.sen_touroperator.models.DTO.user.UserRewardsDto;
import com.example.sen_touroperator.models.securitymodels.AuthenticationRequest;
import com.example.sen_touroperator.models.securitymodels.AuthenticationResponse;
import com.example.sen_touroperator.service.UserService;
import com.example.sen_touroperator.service.qr_service.QrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordHash passwordHash;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserTokenIdManager userTokenIdManager;

//UserPAGE
    @GetMapping(value = "/profile")
    public UserProfileDto userInfo(@RequestHeader("Authorization") String token){
        Integer userId = userTokenIdManager.getIdFromToken(token);
        return  userService.userProfileInfo(userId);
    }
//USER REGION SET
    @PostMapping("/set-region")
    public void setUserRegion(@RequestParam String regionName,@RequestHeader("Authorization") String token){
        Integer userId = userTokenIdManager.getIdFromToken(token);
        userService.setUserRegion(userId,regionName);
    }

//USER REWARD PAGE
    @GetMapping("/rewards")
    public UserRewardsDto userRewards(@RequestHeader("Authorization") String token){
        Integer userId = userTokenIdManager.getIdFromToken(token);
        return userService.getAllRewardsFromUser(userId);
    }
    //VISIT LANDMARK
    @GetMapping(value = "/visit-landmark/{landmarkName}")
    public String visitLandmark(@RequestHeader("Authorization") String token,
                              @PathVariable String landmarkName){
        Integer userId = userTokenIdManager.getIdFromToken(token);
        userService.visitLandmark(landmarkName,userId);
        return "You visited "+landmarkName;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            passwordHash.doHashing( authenticationRequest.getPassword())));
        }catch (BadCredentialsException e){
            throw new UserException("Incorrect username or password");
        }
        final MyUserSec userDetails = (MyUserSec) userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt).getJwt());
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserRegisterDto userRegisterDto){
        userService.registerUser(userRegisterDto);
    }
//REDEEM REWARD
    @PostMapping("/redeem-reward/{rewardId}")
    public String redeemReward(@PathVariable Integer rewardId,@RequestHeader("Authorization") String token){
        Integer userId = userTokenIdManager.getIdFromToken(token);
        userService.redeemReward(rewardId,userId);
        return "Reward redeemed";
    }

}
