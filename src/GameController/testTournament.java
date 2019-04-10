package GameController;

import GameModel.*;
import MapModel.Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by liaoxiaoyun on 2019-04-01.
 */
public class testTournament
{


    public static GameState state;//Game State
    private static MapModel.Map map = Map.getMapInstance();
    private static ArrayList<Player> playerList = new ArrayList<>();
    public static ArrayList<String> mapList = new ArrayList<>();
    public static int numberofmaps = 0;
    public static int numberofplayers = 0;
    public static int currentPlayer = 0; //The ID of current player
    public static boolean getCardFlag = false; // if player conquer at less a country, will get a card, flag is true
    public int initialArmyNum;
    public static int round = 0;
    static Color playercolors[] = {Color.lightGray, Color.MAGENTA, Color.cyan, Color.GREEN, Color.yellow};
    public static boolean cardChangeFlage = false; //check if need to change card
    public static boolean reinforceFlag = false;
    public static int gameNum = 0; // for tournament
    public static int maxRoundNum = 0;// for tournament
    public static int defaultMaxRound = 20; // for single game
    public static boolean stopflag = false;
    public static int curGame = 0;
    public static String curMap = "";
    public static ArrayList<String> tournamentResult = new ArrayList<>();

    public testTournament(GameState state,ArrayList<Player> playerList,int gameNum,int maxRoundNum,ArrayList<String> mapList){
        this.state = state;
        this.playerList = playerList;
        this.gameNum = gameNum;
        this.maxRoundNum = maxRoundNum;
        this.mapList = mapList;
    }
    public void runTournament(){

    }

    public void autoPlay() {

        curMap = "";
        for (String mapName : mapList) {
            curMap = mapName;
            map.reset();
            map.loadMap(mapName);
            //single map
            curGame = 0;
            while (curGame < gameNum) {
                //reset
                resetForNextGame();
                //start up

                AssignPlayers();// start up

                autoOneTurn(curGame);
                while (round < maxRoundNum && state != GameState.END) {
                    System.out.println("maxRoundNum:" + maxRoundNum);
                    System.out.println("round:" + round);
                    turnToNextPlayerT(curGame);
                }
                if (round == maxRoundNum && state != GameState.END) {
                    int curMap = PlayerEngine.mapList.indexOf(PlayerEngine.curMap);
                    String result = "\n " + "Map"+ curMap + "-Game"+curGame + " : Draw";
                    tournamentResult.add(result);
                }

                curGame++;
            }
        }
        String totalResult = "\n    Tournament Result";
        for(String result : tournamentResult){
            totalResult = totalResult + result;
        }
    }


    public void resetForNextGame() {
        map.resetCountries();
        resetPlayers();
        state = GameState.REINFORCE;
        currentPlayer = 0;
        round = 0;
        cardChangeFlage = false;
        reinforceFlag = false;
        getCardFlag = false;
    }

    public void resetPlayers() {
        for (Player player : playerList) {
            player.resetPlayer();
        }
    }
    public void AssignPlayers() {

        ArrayList<Country> countryList = new ArrayList<>(map.getCountriesMap().values());
        Collections.shuffle(countryList);
        for (int i = 0, j = 0; i < countryList.size(); i++, j++) {
            if (j == playerList.size()) {
                j = 0;
            }
            Player curplyr = playerList.get(j); //curplyr = current player
            Country curctn = countryList.get(i);
            curplyr.getCountriesOwned().add(curctn);
            curctn.setPlayer(curplyr);
        }
        //give the initial number of armies for each players
        allocateArmies();

        currentPlayer = 0;

        //check if the first player is computer
        if (getCurPlayer().getStrategy() != null) {
            for (Country country : getCurPlayer().getCountriesOwned()) {
                if (!playerPlaceArmy(country)) {
                    break;
                }
            }
        }
    }

    public void autoOneTurn(int curGame) {
        getCurPlayer().autoReinforce();//need to add auto exchange card

        if (getCurPlayer().autoAttack()) {
            String winner = getCurPlayerNameWithColor();

            int curMap = PlayerEngine.mapList.indexOf(PlayerEngine.curMap);
            String result = "\n " + "Map"+ curMap + "-Game"+curGame + " winner is: " + getCurPlayerNameWithColor();
            tournamentResult.add(result);
            curGame++;
            return;
        }

        getCurPlayer().autoFortify();
        state = GameState.CHOOSECARD;
    }


    public void allocateArmies() {
        //int totalArmy = 30;
        int totalArmy = map.getCountryNum() * 2;
        System.out.println("totalArmy :" + totalArmy);
        this.setInitialArmyNum(totalArmy / playerList.size());

        for (int i = 0; i < playerList.size(); i++) {
            Player curplayer = playerList.get(i);
            curplayer.setArmyList(initialArmyNum);
        }

        //give each country a army first
        for (Player player : playerList) {
            for (Country country : player.getCountriesOwned()) {
                placeInitialArmy(player, country);
            }
        }
    }
    public void setInitialArmyNum(int initArmyNum) {
        this.initialArmyNum = initArmyNum;
    }

    public void placeInitialArmy(Player curplayer, Country destination) {
        if (curplayer.getCountriesOwned().contains(destination)) {
            destination.AddArmy();
        } else {
        }
    }

    public Player getCurPlayer() {
        return playerList.get(currentPlayer);
    }

    public String getCurPlayerNameWithColor() {
        return getPlayNameWithColor(currentPlayer);
    }

    public String getPlayNameWithColor(int index) {
        String name = "";
        name = playerList.get(index).getName() + "(" + getColorName(playercolors[index]) + ")";
        return name;
    }

    public String getColorName(Color color) {
        String colorName = "";

        if (color.equals(Color.lightGray)) {
            colorName = "Gray";
        } else if (color.equals(Color.MAGENTA)) {
            colorName = "Red";
        } else if (color.equals(Color.cyan)) {
            colorName = "Blue";
        } else if (color.equals(Color.GREEN)) {
            colorName = "GREEN";
        } else if (color.equals(Color.yellow)) {
            colorName = "Yellow";
        }
        return colorName;
    }


    public boolean playerPlaceArmy(Country country) {

        int index = 0;
        Player curPlayer = playerList.get(currentPlayer);
        ArrayList<Army> curPlayerArmyList = playerList.get(currentPlayer).getArmyList();
        //System.out.println("curPlayerArmyList " + curPlayerArmyList);

        while (curPlayerArmyList.get(index).getCountry() != null && index < curPlayerArmyList.size()) {
            index++;
        }
        if (index < curPlayerArmyList.size()) {
            placeInitialArmy(curPlayer, country);
            index++;
        }
        System.out.println("index: " + index);
        if (index == curPlayerArmyList.size()) {

            currentPlayer++;
            if (currentPlayer < playerList.size()) {

                if (getCurPlayer().getStrategy() != null) {
                    Country placeArmy = null;
                    Collection<Country> countries = getCurPlayer().getCountriesOwned();
                    Iterator<Country> iterator = countries.iterator();
                    if (iterator.hasNext()) {
                        placeArmy = iterator.next();
                    }
                    while (playerPlaceArmy(placeArmy)) {
                        if (iterator.hasNext()) {
                            placeArmy = iterator.next();
                        } else {
                            iterator = countries.iterator();
                            placeArmy = iterator.next();
                        }
                    }
                }
            } else {

                //state = GameState.REINFORCE;
                currentPlayer = 0;
            }
            //this player finished the army placement and next player is a human, stop auto place army
            //System.out.println("continue to place army");
            return false;
        } else {
            // continue to place army
            //System.out.println("continue to place army");
            return true;
        }
    }


    public void turnToNextPlayerT(int curGame) {
        System.out.println("gameState :" + state);
        System.out.println("currentPlayer :" + getCurPlayerNameWithColor());
        //reset card flag
        getCardFlag = false;
        reinforceFlag = false;
        cardChangeFlage = false;
        if (state == GameState.CHOOSECARD) {
            if (currentPlayer == playerList.size() - 1) {
                currentPlayer = 0;
                if (!getCurPlayer().isAlive) {
                    turnToNextPlayerT(curGame);
                } else {

                    round++;
                    autoOneTurn(curGame);
                }
            } else {
                currentPlayer++;
                if (!getCurPlayer().isAlive) {
                    turnToNextPlayerT(curGame);
                } else {

                    autoOneTurn(curGame);
                }
            }
        }
    }
}
