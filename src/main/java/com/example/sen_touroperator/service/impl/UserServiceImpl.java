package com.example.sen_touroperator.service.impl;

import com.example.sen_touroperator.config.security.PasswordHash;
import com.example.sen_touroperator.exception_handler.exceptions.*;
import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DAO.Reward;
import com.example.sen_touroperator.models.DAO.User;
import com.example.sen_touroperator.models.DTO.RewardDto;
import com.example.sen_touroperator.models.DTO.user.UserLoginDto;
import com.example.sen_touroperator.models.DTO.user.UserProfileDto;
import com.example.sen_touroperator.models.DTO.user.UserRegisterDto;
import com.example.sen_touroperator.models.DTO.user.UserRewardsDto;
import com.example.sen_touroperator.repositroy.LandmarkRepository;
import com.example.sen_touroperator.repositroy.RewardRepository;
import com.example.sen_touroperator.repositroy.UserRepository;
import com.example.sen_touroperator.repositroy.mySql.MySQLRewardRepository;
import com.example.sen_touroperator.repositroy.mySql.MySQLUserRepository;
import com.example.sen_touroperator.service.UserService;
import com.example.sen_touroperator.service.mail_gun.MailSenderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final MailSenderService mailSenderService;

    //private final JavaMailSender mailSender = new JavaMailSenderImpl();
    private final RewardRepository rewardRepository;
    private final LandmarkRepository landmarkRepository;
    private final ModelMapper modelMapper;
    PasswordHash passwordHash;

    public UserServiceImpl(UserRepository userRepository,
                           MailSenderService mailSenderService,
                           PasswordHash passwordHash,
                           LandmarkRepository landmarkRepository,
                           ModelMapper modelMapper,
                           RewardRepository rewardRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.rewardRepository = rewardRepository;
        this.passwordHash = passwordHash;
        this.landmarkRepository = landmarkRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public void registerUser(UserRegisterDto userRegisterDto) {
        if(checkIfUserExists(userRegisterDto.getEmail())){
            throw new UserException("User with email " +userRegisterDto.getEmail() +" already exists");
        }
        if(!verifyEmailPattern(userRegisterDto)){
            throw new EmailPatternInvalid();
        }
        if(!verifyPasswordPattern(userRegisterDto)){
            throw new PasswordPatternInvalid();
        }
        String hashedPassword = passwordHash.doHashing(userRegisterDto.getPassword());
        userRegisterDto.setPassword(hashedPassword);

        User userDao = modelMapper.map(userRegisterDto,User.class);

        try {
            userRepository.registerUser(userDao);
        }catch (DataIntegrityViolationException e){
            throw new UserException("User already Exist");
        }


    }
    @Override
    public UserProfileDto userProfileInfo(Integer id) {
        User userDao = userRepository.findUserById(id).orElse(null);
       return modelMapper.map(userDao, UserProfileDto.class);

    }

    @Override
    public UserRewardsDto getAllRewardsFromUser(Integer id) {
        User userDao = userRepository.findUserById(id).orElse(null);
        if(userDao == null){
            throw new  UserException("User not found");
        }
        if(userDao.getRewardsList() == null){
            throw new RewardException("User dont has any rewards");
        }
        return modelMapper.map(userDao,UserRewardsDto.class);
    }

    @Override
    public void setUserRegion(Integer id, String regionName) {
        userRepository.setUserRegion(id,regionName);
    }
    @Override
    public void visitLandmark(String landmarkName, Integer userId) {
        Random random = new Random();

        userRepository.visitLandmark(landmarkName,userId);
        User userDao = userRepository.findUserById(userId).orElseThrow();

        List<Landmark> landmarkList = landmarkRepository.getLandmarksByUserLocation(userDao.getRegion());
        Landmark validLandmark = landmarkList
                .stream()
                .filter(landmark -> landmark.getName().equals(landmarkName))
                .toList()
                .stream()
                .findFirst()
                .orElse(null);

        if(validLandmark == null){
            throw new LandmarkException("You cannot use the QR_code of a landmark when you are not in its region");
        }


        if(userDao.getVisitedLandmarks().size() % 3 ==0){
            List<Reward> rewardList = rewardRepository.getAllRewards();
            Reward randomReward = rewardList.get(random.nextInt(1,rewardList.size()));

            userRepository.earnReward(randomReward.getId(),userId);
        }
    }

    @Override
    public void redeemReward(Integer rewardId, Integer userId) {
        Reward reward = rewardRepository.getRewardById(rewardId);
        User user = userRepository.findUserById(userId).orElseThrow();
        final String emailBody = "" + user.getUsername() + "received " + reward.getTitle()+": " +reward.getDescription();

        mailSenderService.sendMail("SenTouroperator Reward Redeemed",emailBody,"petar.krastev@trading212.com");

        rewardRepository.redeemReward(rewardId,userId);
    }

    private boolean checkIfUserExists(String email) {
        Optional<User> userDao = userRepository.findUserByUsername(email);
        return userDao.isPresent();
    }

    public boolean verifyEmailPattern(UserRegisterDto userRegisterDto) {
        Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcher = pattern.matcher(userRegisterDto.getEmail());
        System.out.println(userRegisterDto.getEmail() + " " + matcher.matches());
        return matcher.matches();
    }

    public boolean verifyPasswordPattern(UserRegisterDto userRegisterDto){
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z]).{8,20}$");
        Matcher matcher = pattern.matcher(userRegisterDto.getPassword());
        System.out.println(matcher.matches());
        return matcher.matches();
    }
//    @Async
//    public void sendMailReward(User user,Reward rewardDto)
//            throws MessagingException, UnsupportedEncodingException {
//        String toAddress = "kikad11767@serosin.com";
//        final String subject = "Reward Redeemd";
//        String content = "Congratulations you have successfully redeemed " + rewardDto.getTitle();
//
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom(new InternetAddress("noreply@localhost.com", "noreply"));
//        helper.setTo(toAddress);
//        helper.setSubject(subject);
//
//        helper.setText(content);
//
//        mailSender.send(message);
//    }

}
