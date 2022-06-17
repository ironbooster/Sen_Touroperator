package com.example.sen_touroperator.repositroy.mySql;

import com.example.sen_touroperator.models.DAO.Review;
import com.example.sen_touroperator.repositroy.ReviewRepository;
import com.example.sen_touroperator.repositroy.mySql.ResultSetManager.ResultSetManager;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

@Repository
public class MySQLReviewRepository implements ReviewRepository {

    private final JdbcTemplate jdbc;
    private final TransactionTemplate txTemplate;

    public MySQLReviewRepository(JdbcTemplate jdbc,
                                   TransactionTemplate txTemplate) {
        this.jdbc = jdbc;
        this.txTemplate = txTemplate;
    }


    @Override
    public void createReviewOnALandmark(Review review, Integer landmarkId,Integer userId) {
        final String CREATE_REVIEW_ON_LANDMARK = "INSERT INTO reviews(stars,discription,user_id,landmark_id)\n" +
                "VALUES (?,?,?,?)";

        txTemplate.execute(status -> {
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(CREATE_REVIEW_ON_LANDMARK);
                ps.setInt(1,review.getStars());
                ps.setString(2,review.getDescription());
                ps.setInt(3,userId);
                ps.setInt(4,landmarkId);
                return ps;
            });
            return null;
        });
    }


    @Override
    public void deleteReview(Integer id) {
        jdbc.update("DELETE FROM reviews WHERE id = ?", id);
    }

    @Override
    public List<Integer> getAvgRatingOfLandmark(Integer landmarkId) {
        final String GET_ALL_REVIEWS_BY_LANDMARK = "select stars from reviews where landmark_id = ?";

        List<Integer> ratingStars = jdbc.query(GET_ALL_REVIEWS_BY_LANDMARK,
                (rs, rowNum) -> rs.getInt("stars"),landmarkId);
        if(ratingStars.isEmpty()){
            return List.of(0);
        }
        return ratingStars;
    }

    @Override
    public List<Review> getAllReviewsFromPostById(Integer landmarkId) {
        final String GET_ALL_REVIEWS_FROM_POST = "select id,stars,discription from reviews where landmark_id = ?";
        return jdbc.query(GET_ALL_REVIEWS_FROM_POST,
                (rs, rowNum) -> ResultSetManager.fromResultSetReview(rs),landmarkId);
    }
}
