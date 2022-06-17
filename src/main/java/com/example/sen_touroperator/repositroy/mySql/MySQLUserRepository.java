package com.example.sen_touroperator.repositroy.mySql;

import com.example.sen_touroperator.exception_handler.exceptions.LandmarkException;
import com.example.sen_touroperator.exception_handler.exceptions.RegionException;
import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DAO.Reward;
import com.example.sen_touroperator.models.DAO.User;
import com.example.sen_touroperator.models.DTO.user.UserLoginDto;
import com.example.sen_touroperator.models.DTO.user.UserRegisterDto;
import com.example.sen_touroperator.repositroy.UserRepository;
import com.example.sen_touroperator.repositroy.mySql.ResultSetManager.ResultSetManager;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.swing.text.html.Option;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MySQLUserRepository implements UserRepository {

    private final JdbcTemplate jdbc;
    private final TransactionTemplate txTemplate;

    public MySQLUserRepository(JdbcTemplate jdbc, TransactionTemplate txTemplate) {
        this.jdbc = jdbc;
        this.txTemplate = txTemplate;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        final String USERNAME_QUERY = "SELECT id,username,email,password,roles,current_region " +
                "FROM users where username = ?";
        List<User> userDao = jdbc.query(USERNAME_QUERY,(rs, rowNum) -> ResultSetManager.fromResultSetUser(rs),username);

        return userDao.stream().findFirst();
    }

    @Override
    @Transactional
    public Optional<User> findUserById(Integer id) {
        final String USERNAME_QUERY = "SELECT id,username,email,password,roles,current_region FROM users where id = ?";
        List<User> userDaoResult = jdbc.query(USERNAME_QUERY,(rs, rowNum) -> ResultSetManager.fromResultSetUser(rs),id);

        final String REWARDS_OWNED_BY_USERNAME = "select r.id , r.title,r.discription from rewards as r\n" +
                "join users_rewards as ur on ur.reward_id = r.id\n" +
                "join users on ur.user_id = users.id where ur.user_id = ?";
        List<Reward> rewardsDaoList = jdbc.query(REWARDS_OWNED_BY_USERNAME,
                (rs,rowNum) -> ResultSetManager.fromResultSetReward(rs),id);

        final String LANDMARKS_VISITED_BY_USER = "select l.id , l.name , l.location , l.img , l.region, l.QR_code , l.landmark_type,\n" +
                "l.visitings from landmarks as l\n" +
                "join users_landmarks as ul on ul.landmark_id = l.id\n" +
                "join users on ul.user_id = users.id where ul.user_id = ?";
        List<Landmark> landmarksDaoList = jdbc.query(LANDMARKS_VISITED_BY_USER,
                (rs,rowNum) -> ResultSetManager.fromResultSetLandmark(rs),id);

        return userDaoResult
                .stream()
                .findFirst()
                .map(user -> {
                    user.setRewardsList(rewardsDaoList);
                    user.setVisitedLandmarks(landmarksDaoList);
                    return user;});

    }

    @Override
    public void registerUser(User user){

        final String REGISTER_USER = "INSERT INTO users(username, email,password)VALUES (?,?,?)";

        txTemplate.execute(status -> {
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(REGISTER_USER);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                return ps;
            });
            return null;
        });
    }

    @Override
    public void visitLandmark(String landmarkName, Integer userId) {
        final String VISITED_LANDMARKS_NAME = "SELECT landmarks.name FROM users_landmarks\n" +
                "join landmarks on landmarks.id = landmark_id where user_id = ?;";
        final String VISIT_LANDMARK = "INSERT INTO users_landmarks(landmark_id,user_id)VALUES (?,?)";

        List<String> landmarksId = jdbc.query(VISITED_LANDMARKS_NAME,
                ((rs, rowNum) -> rs.getString("landmarks.name")),userId );

        if(landmarksId.stream().anyMatch(n->Objects.equals(n,landmarkName))){
            throw new LandmarkException("Landmark Already Visited");
        }

        Landmark landmark = getLandmarkByName(landmarkName);
        int landmarkId= landmark.getId();

        txTemplate.execute(status -> {
            incrementVisingsOnLandmark(landmark);
            jdbc.update(con -> {

                PreparedStatement ps = con.prepareStatement(VISIT_LANDMARK);
                ps.setInt(1,landmarkId);
                ps.setInt(2,userId);
                return ps;
            });
            return null;
        });
    }


    private Landmark getLandmarkByName(String landmarkName){
        final String LANDMARK_BY_NAME = "SELECT id,name,location,img," +
                "region,QR_code,landmark_type,visitings from landmarks where name = ? ";

        List<Landmark> landmarkDAO = jdbc.query(LANDMARK_BY_NAME,
                (rs,rowNum) -> ResultSetManager.fromResultSetLandmark(rs),landmarkName);
        Landmark landmark = landmarkDAO.stream().findFirst().orElse(null);
        if(landmark==null){
            throw new LandmarkException("Landmark doesnt exist");
        }
        return landmark;

    }

    private void incrementVisingsOnLandmark(Landmark landmark){
        final String INC_VISITINGS_LANDMARK = "update landmarks\n" +
                "set visitings = ? where id = ?;";
        final int visitings = landmark.getVisitings() + 1;
        final int landmarkId = landmark.getId();

        jdbc.update(INC_VISITINGS_LANDMARK,visitings,landmarkId);
    }

    @Override
    public void earnReward(Integer rewardId, Integer userId) {
        final String EARN_REWARD = "INSERT INTO users_rewards(user_id,reward_id)\n" +
                "values(?,?)";

        txTemplate.execute(status -> {
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(EARN_REWARD);
                ps.setInt(1,userId);
                ps.setInt(2,rewardId);
                return ps;
            });
            return null;
        });
    }

    @Override
    public void setUserRegion(Integer id, String regionName) {
        String SET_USER_REGION = "update users set current_region = ? where id = ?";
        String GET_ALL_REGIONS = "SELECT region FROM regions";

        List<String> regionList = jdbc.query(GET_ALL_REGIONS,
                ((rs, rowNum) -> rs.getString("region")));
        if(regionList.stream().noneMatch(n->Objects.equals(n,regionName))){
            throw new RegionException("Region doesnt exist");
        };

        jdbc.update(SET_USER_REGION,regionName,id);
    }
}
