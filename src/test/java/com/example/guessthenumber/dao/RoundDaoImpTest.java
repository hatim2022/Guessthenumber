package com.example.guessthenumber.dao;

import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Round;
import com.example.guessthenumber.dto.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoundDaoImpTest {

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
    void addRound() {
        Game game=new Game("1234", Status.PROGRESS);
        gameDao.createGame(game);
        Round round=new Round("1543",new Date(),game.getId());
        roundDao.addRound(round);
        Round createdRound=roundDao.getRoundById(round.getId());
        assertEquals(round,createdRound);

    }

    @Test
    void getRoundsByGame() {
        Game game=new Game("1234", Status.PROGRESS);
        gameDao.createGame(game);

        Round round1=new Round("1543",new Date(),game.getId());
        roundDao.addRound(round1);
        Round round2=new Round("1253",new Date(),game.getId());
        roundDao.addRound(round2);

        List<Round> list=new ArrayList<>();
        list.add(round1);
        list.add(round2);

        assertEquals(roundDao.getRoundsByGame(game.getId()),list);

    }

    @Test
    void removeRound() {

        Game game=new Game("1234", Status.PROGRESS);
        gameDao.createGame(game);

        Round round1=new Round("1543",new Date(),game.getId());
        roundDao.addRound(round1);

        roundDao.removeRound(round1.getId());
        Assertions.assertNull(roundDao.getRoundById(round1.getId()));

    }
}