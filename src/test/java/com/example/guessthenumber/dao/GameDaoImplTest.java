package com.example.guessthenumber.dao;

import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Round;
import com.example.guessthenumber.dto.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameDaoImplTest {

    private GameDao gameDao;
    private RoundDao roundDao;

    @BeforeEach
    void setUp() {
        ApplicationContext ctx=new ClassPathXmlApplicationContext("ApplicationContext.xml");
        gameDao=ctx.getBean("gameDao",GameDaoImpl.class);
        roundDao=ctx.getBean("roundDao",RoundDaoImp.class);
        List<Game> gameList= gameDao.getAllGames();

        for(Game game:gameList){
            List<Round> roundList=roundDao.getRoundsByGame(game.getId());
            for(Round r:roundList){
                roundDao.removeRound(r.getId());
            }
            gameDao.deleteGameById(game.getId());
        }

    }


    @Test
    void createGame() {
        Game game=new Game("1234", Status.PROGRESS);
        gameDao.createGame(game);
        game.setRounds(roundDao.getRoundsByGame(game.getId()));

        Game createdGame=gameDao.getGameById(game.getId());
        createdGame.setRounds(roundDao.getRoundsByGame(createdGame.getId()));

        Assertions.assertEquals(game,createdGame);
    }

    @Test
    void updateGameStatus() {

        Game game=new Game("1234", Status.PROGRESS);
        gameDao.createGame(game);
        gameDao.updateGameStatus(game.getId(),Status.FINISHED);
        Game createdGame=gameDao.getGameById(game.getId());
        Assertions.assertEquals(createdGame.getStatus(),Status.FINISHED);

    }

    @Test
    void deleteGameById() {
        Game game=new Game("1234", Status.PROGRESS);
        gameDao.createGame(game);
        assertNotNull(gameDao.getGameById(game.getId()));
        gameDao.deleteGameById(game.getId());
        assertNull(gameDao.getGameById(game.getId()));
    }

    @Test
    void getAllGames() {

        Game game=new Game("1234", Status.PROGRESS);
        gameDao.createGame(game);
        game.setRounds(roundDao.getRoundsByGame(game.getId()));

        Game game2=new Game("4321", Status.PROGRESS);
        gameDao.createGame(game2);
        game2.setRounds(roundDao.getRoundsByGame(game.getId()));

        assertEquals(2,gameDao.getAllGames().size());

        List<Game> gameList=gameDao.getAllGames();
        for(Game currentGame:gameList){
           currentGame.setRounds(roundDao.getRoundsByGame(currentGame.getId()));
        }
        assertTrue(gameList.contains(game));
        assertTrue(gameList.contains(game2));

    }

    @Test
    void getGameById() {
        Game game=new Game("1234", Status.PROGRESS);
        game.setRounds(roundDao.getRoundsByGame(game.getId()));
        gameDao.createGame(game);
        Game createdGame=gameDao.getGameById(game.getId());
        createdGame.setRounds(roundDao.getRoundsByGame(game.getId()));
        assertEquals(game,createdGame);
    }
}