package com.example.sen_touroperator.repositroy.mySql;

import com.example.sen_touroperator.exception_handler.exceptions.RewardException;
import com.example.sen_touroperator.models.DAO.Reward;
import com.example.sen_touroperator.models.DAO.User;
import com.example.sen_touroperator.models.DTO.RewardDto;
import com.example.sen_touroperator.repositroy.RewardRepository;
import com.example.sen_touroperator.repositroy.mySql.ResultSetManager.ResultSetManager;
import org.modelmapper.ModelMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class MySQLRewardRepository implements RewardRepository {

    private final JdbcTemplate jdbc;
    private final TransactionTemplate txTemplate;

    public MySQLRewardRepository(JdbcTemplate jdbc,
                                 TransactionTemplate txTemplate) {
        this.jdbc = jdbc;
        this.txTemplate = txTemplate;
    }

    @Override
    public List<Reward> getAllRewards() {
        final String GET_ALL_REWARDS = "SELECT id,title,discription FROM rewards";
        return jdbc.query(GET_ALL_REWARDS,((rs, rowNum) -> ResultSetManager.fromResultSetReward(rs)));
    }

    @Override
    public void createReward(Reward rewardDao) {

        final String CREATE_REWARD = "INSERT INTO rewards" +
                "(title,discription)" +
                "VALUES (?,?)";

        txTemplate.execute(status -> {
            jdbc.update(con -> {
                PreparedStatement ps = con.prepareStatement(CREATE_REWARD);
                ps.setString(1,rewardDao.getTitle());
                ps.setString(2,rewardDao.getDescription());
                return ps;
            });
            return null;
        });


    }

    @Override
    public void deleteReward(Integer id){
        jdbc.update("DELETE FROM rewards WHERE id = ?", id);
    }

    @Override
    public Reward getRewardById(Integer id) {
        final String GET_REWARD_BY_ID = "SELECT id,title,discription FROM rewards WHERE id = ?";

        List<Reward> rewardById = jdbc.query(GET_REWARD_BY_ID,
                (rs, rowNum) -> ResultSetManager.fromResultSetReward(rs),id);
        Reward reward = rewardById.stream().findFirst().orElse(null);
        if(reward == null){
            throw new RewardException("Invalid Reward");
        }

        return reward;
    }

    @Override
    @Transactional
    public void redeemReward(Integer rewardId, Integer userId) {
        final String GET_USER_REWARD_IDS = "Select reward_id from users_rewards where user_id = ?";
        final String REDEEM_REWARD = "DELETE FROM users_rewards where user_id = ? AND reward_id = ?";

        List<Integer> rewardsId = jdbc.query(GET_USER_REWARD_IDS,(rs, rowNum) -> rs.getInt("reward_id"),userId);
         if(rewardsId.stream().noneMatch(n -> Objects.equals(n, rewardId))){
             throw new RewardException("User not eligible to claim this reward");
         }

        jdbc.update(REDEEM_REWARD,userId,rewardId);
    }
}
