package com.example.guessthenumber.service;

import com.example.guessthenumber.dao.GameDao;
import com.example.guessthenumber.dao.RoundDao;
import com.example.guessthenumber.dto.Game;
import com.example.guessthenumber.dto.Round;
import com.example.guessthenumber.dto.Status;
import com.example.guessthenumber.exception.InvalidGuessNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ServiceImpl implements ServiceInt  {

    @Autowired
    GameDao gameDao;
    @Autowired
    RoundDao roundDao;



    private String generateAnswer(){
        String s="";
        List<Integer> list=new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9));
        Random random=new Random();
        Integer r;

        for(int i=0;i<4;i++) {
            r=list.get(random.nextInt(list.size()));
            s +=r;
            list.remove(r);
        }
        return s;
    }

    @Override
    public Game createGame() {
       String answer= generateAnswer();
       Game game=new Game(answer, Status.PROGRESS);
       Game game1=gameDao.createGame(game);
       game1.setAnswer("0");
       return game1;
    }

    @Override
    public Round makeGuess(String guess,int gameId) throws InvalidGuessNumberException {

        isGuessNumberValid(guess);

        Game game=gameDao.getGameById(gameId);

        String answer=game.getAnswer();

        Map<Integer,Character> guessList=new HashMap<>();
        Map<Integer,Character> answerList=new HashMap<>();

        for(int i=1;i<=4;i++){
            guessList.put(i,guess.charAt(i-1));
            answerList.put(i,answer.charAt(i-1));
        }
        String[] result=getResult(guessList,answerList).split(":");
        Round round=new Round(guess,new Date(),gameId);
        round.setExactMatch(Integer.parseInt(result[1]));
        round.setPartialMatch(Integer.parseInt(result[3]));
        round.setGameID(gameId);

        Game currentGame=gameDao.getGameById(gameId);

        roundDao.addRound(round);

        if(round.getExactMatch()==4){
            currentGame.setStatus(Status.FINISHED);
            gameDao.updateGameStatus(gameId,Status.FINISHED);
        }

        return round;
    }

    public void isGuessNumberValid(String guess) throws InvalidGuessNumberException {
        Set<Character> set=new HashSet<>();

        for(int i=0;i<guess.length();i++){
            set.add(guess.charAt(i));
        }
        if(set.size()!=4){
            throw new InvalidGuessNumberException();
        }

    }

    private String getResult(Map<Integer, Character> guessList, Map<Integer, Character> answerList) {
        int e=0,p=0;
        int tmp=4;

        for(int i=1;i<=4;i++){
        if(guessList.get(i)==answerList.get(i)){
            e++;
            guessList.remove(i);
            answerList.remove(i);
        }}

        for(char x:guessList.values()){
           if(answerList.containsValue(x)){
               p++;
           }
        }
        return "e:"+e+":p:"+p;
    }

    @Override
    public List<Game> getFinishedGames() {
        List<Game> finishedGameList = gameDao.getAllGames();

        for(Game game:finishedGameList){
            game.setRounds(roundDao.getRoundsByGame(game.getId()));
        }

        Iterator<Game> iterator=finishedGameList.iterator();
        while (iterator.hasNext()){
            if(iterator.next().getStatus().equals(Status.PROGRESS)){
                iterator.remove();
            }
        }
        return finishedGameList;
    }

    @Override
    public Game getGameById(int id) {
        Game game=gameDao.getGameById(id);
        game.setRounds(roundDao.getRoundsByGame(game.getId()));
        return game;

    }

    @Override
    public List<Round> getRoundsForGame(int id) {
        return roundDao.getRoundsByGame(id);
    }
}
