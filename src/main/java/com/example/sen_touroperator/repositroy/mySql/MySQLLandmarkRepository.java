package com.example.sen_touroperator.repositroy.mySql;

import com.example.sen_touroperator.models.DAO.Landmark;
import com.example.sen_touroperator.models.DAO.Review;
import com.example.sen_touroperator.models.DTO.LandmarkDto;
import com.example.sen_touroperator.models.DTO.binding.LandmarkBindingDto;
import com.example.sen_touroperator.repositroy.LandmarkRepository;
import com.example.sen_touroperator.repositroy.mySql.ResultSetManager.ResultSetManager;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MySQLLandmarkRepository implements LandmarkRepository {

    private final JdbcTemplate jdbc;
    private final TransactionTemplate txTemplate;

    public MySQLLandmarkRepository(JdbcTemplate jdbc,
                                   TransactionTemplate txTemplate) {
        this.jdbc = jdbc;
        this.txTemplate = txTemplate;
    }



    @Override
    public List<Landmark> getAllLandmarks() {

        final String GET_ALL_LANDMARKS = "SELECT id,name,location,img,region,QR_code,landmark_type,visitings FROM landmarks";
        return jdbc.query(GET_ALL_LANDMARKS,((rs, rowNum) -> ResultSetManager.fromResultSetLandmark(rs)));
    }


    @Override
    public List<Landmark> getLandmarksByType(String landmarkType) {
        final String GET_LANDMARKS_BY_TYPE = "SELECT id,name,location,img,region,QR_code,landmark_type,visitings FROM landmarks where landmark_type = ?";
        return jdbc.query(GET_LANDMARKS_BY_TYPE,
                ((rs, rowNum) -> ResultSetManager.fromResultSetLandmark(rs)),landmarkType);
    }

    @Override
    public void deleteLandmark(Integer id) {
        jdbc.update("DELETE FROM landmarks WHERE id = ?", id);
    }

    @Override
    public List<Landmark> getLandmarksByUserLocation(String region) {


        final String GET_LANDMARKS_BY_USER_LOCATION =
                "select l.id,l.name,l.location,l.img,l.region,l.QR_code,l.landmark_type,l.visitings from landmarks as l where region = ?;";
        return jdbc.query(GET_LANDMARKS_BY_USER_LOCATION,
                ((rs, rowNum) -> ResultSetManager.fromResultSetLandmark(rs)),region);
    }

    @Override
    public void createLandmark(Landmark landmarkDao) {
        final String CREATE_LANDMARK = "INSERT INTO landmarks" +
                " (name, location, img, region, QR_code, landmark_type,visitings)\n" +
                "VALUES (?,?,?,?,?,?,?)";

        txTemplate.execute(status -> {
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(CREATE_LANDMARK);
                ps.setString(1,landmarkDao.getName());
                ps.setString(2,landmarkDao.getLocation());
                ps.setString(3,landmarkDao.getImg());
                ps.setString(4,landmarkDao.getRegion());
                ps.setString(5,landmarkDao.getQrCode());
                ps.setString(6,landmarkDao.getLandmarkType());
                ps.setInt(7,landmarkDao.getVisitings());
                return ps;
            });
            return null;
        });
    }

    @Override
    public Optional<Landmark> getLandmarkById(Integer id) {
        final String GET_LANDMARK_BY_ID = "SELECT id,name,location,img,region,QR_code,landmark_type,visitings FROM landmarks WHERE id = ?";
        List<Landmark> landmarkList= jdbc.query(GET_LANDMARK_BY_ID,
                ((rs, rowNum) -> ResultSetManager.fromResultSetLandmark(rs)),id);

        final String GET_LANDMARK_REVIEWS = "SELECT id , stars , discription from reviews where landmark_id = ?";
        List<Review> reviews= jdbc.query(GET_LANDMARK_REVIEWS,
                ((rs, rowNum) -> ResultSetManager.fromResultSetReview(rs)),id);


        Optional<Landmark> landmarkDAO = landmarkList.stream().findFirst();
        return landmarkDAO.stream().map(landmark -> landmark.setReviewList(reviews)).findFirst();
    }
}
