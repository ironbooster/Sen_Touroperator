package com.example.sen_touroperator.repositroy.mySql.ResultSetManager;

import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DAO.Review;
import com.example.sen_touroperator.models.DAO.Reward;
import com.example.sen_touroperator.models.DAO.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResultSetManager {
    public static User fromResultSetUser(ResultSet rs) throws SQLException {
        return new User(
                (Integer) rs.getInt("id"),
                rs.getString("username"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("roles"),
                rs.getString("current_region"));
    }

    public static Landmark fromResultSetLandmark(ResultSet rs) throws SQLException {
        return new Landmark(
                (Integer) rs.getInt("id"),
                rs.getString("name"),
                rs.getString("location"),
                rs.getString("img"),
                rs.getString("region"),
                rs.getString("QR_code"),
                rs.getString("landmark_type"),
                rs.getInt("visitings"));
    }
    public static Review fromResultSetReview(ResultSet rs) throws SQLException{
        return new Review(
                rs.getInt("id"),
                rs.getInt("stars"),
                rs.getString("discription"));
    }
    public static Reward fromResultSetReward(ResultSet rs) throws SQLException{
        return  new Reward(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("discription"));
    }

}
