package com.example.guessthenumber.dao;

import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Round;
import com.example.guessthenumber.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class GameDaoImpl implements GameDao{
    @Autowired
    JdbcTemplate jdbc;


    public GameDaoImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    @Transactional
    public Game createGame(Game game) {
        final String INSERT_GAME = "INSERT INTO game(answer,status)"
                + "VALUES(?,?)";
        jdbc.update(INSERT_GAME,
                game.getAnswer(),
                game.getStatus().toString());
        int newId = jdbc.queryForObject("SELECT MAX(gameId) from game", Integer.class);
        game.setId(newId);
        return game;
    }


    @Transactional
    @Override
    public void updateGameStatus(int gameId,Status status) {
        final String UPDATE_GAME = "UPDATE game "
                + "SET status = ? WHERE gameId = ?";
        jdbc.update(UPDATE_GAME, status.toString(), gameId);
    }

    @Override
    @Transactional
    public void deleteGameById(int gameId) {
        final String DELETE_GAME_BY_ID = "DELETE FROM game where gameId = ?";
         jdbc.update(DELETE_GAME_BY_ID,gameId);
    }

    @Override
    @Transactional
    public List<Game> getAllGames() {
        final String SELECT_ALL_GAMES = "SELECT * FROM Game";
        return jdbc.query(SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    @Transactional
    public Game getGameById(int gameId) {
        final String SELECT_GAMES_BY_ID = "SELECT * FROM Game WHERE gameId = ?";
        try {
            Game game = jdbc.queryForObject(SELECT_GAMES_BY_ID, new GameMapper(), gameId);
            return game;
        } catch(DataAccessException ex) {
            return null;
        }
    }


    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setStatus(Status.valueOf(rs.getString("status")));
            return game;
        }
    }

}
