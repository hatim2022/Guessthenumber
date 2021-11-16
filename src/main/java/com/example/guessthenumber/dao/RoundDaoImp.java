package com.example.guessthenumber.dao;

import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class RoundDaoImp implements RoundDao{

    JdbcTemplate jdbc;


    public RoundDaoImp(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Round addRound(Round round) {
        final String INSERT_ROUND = "INSERT INTO round(guess,guesstime,result,gameID)"
                + "VALUES(?,?,?,?)";
        jdbc.update(INSERT_ROUND,
                round.getGuess(),
                round.getTime(),
                round.getResult(),
                round.getGameID());
        int newId = jdbc.queryForObject("SELECT MAX(roundId) FROM round", Integer.class);
        round.setId(newId);
        return round;
    }

    @Override
    public List<Round> getRoundsByGame(int gameId) {
        final String SELECT_ROUNDS_FOR_GAME = "SELECT * FROM round r "
                + " WHERE r.gameID = ?";
        List<Round> rounds = jdbc.query(SELECT_ROUNDS_FOR_GAME,
                new RoundMapper(), gameId);
        return rounds;
    }

    @Override
    public void removeRound(int roundId) {
        final String DELETE_GAME_BY_ID = "DELETE FROM round where roundId = ?";
        jdbc.update(DELETE_GAME_BY_ID,roundId);
    }

    @Override
    public Round getRoundById(int roundId) {
        try {
            final String SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE roundId = ?";
            Round round= jdbc.queryForObject(SELECT_ROUND_BY_ID,new RoundMapper(), roundId);
            return round;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setId(rs.getInt("roundId"));
            round.setGuess(rs.getString("guess"));
            round.setTime(rs.getDate("guesstime"));
            round.setMatch(rs.getString("result"));
            round.setGameID(Integer.parseInt(rs.getString("gameID")));

            return round;
        }
    }


}
