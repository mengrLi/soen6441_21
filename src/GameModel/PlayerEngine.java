package GameModel;


import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.*;

import GameModel.StrategyPlayer.Aggressive;
import GameModel.StrategyPlayer.Benevolent;
import GameModel.StrategyPlayer.Cheater;
import GameModel.StrategyPlayer.Strategy;
import GameModel.StrategyPlayer.RandomP;
import MapModel.Map;
import MapView.LogWindow;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * This is the game engine which is a singleton class and
 * controls the game flow based on the game rules.
 */
public class PlayerEngine {
    public static LogWindow log;

    public static GameState state;//Game State
    private static MapModel.Map map = Map.getMapInstance();
    private static ArrayList<Player> playerList = new ArrayList<>();
    private static ArrayList<String> mapList = new ArrayList<>();
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


    /**
     * Game Engine Constructor, creates map and sets the game state to Edit mode
     */
    public PlayerEngine() {
        state = GameState.EDITMAP;
    }

    public GameState getCurrentState() {
        return state;
    }


    /**
     * This method is used to initial the number of players
     *
     * @param playerNum is the number of players chose by user
     */
    public void setPlayerList(int playerNum) {
        int i;
        this.setNumberofplayers(playerNum);
        for (i = 0; i < this.numberofplayers; i++) {
            //Player plyr = new Player(i); //plyr = player
            //Cheater test
            Player plyr;
            plyr = new Player(i);
            plyr.setColor(playercolors[i]);
            plyr.setName("player" + i);
            playerList.add(plyr);
        }
        System.out.println("Successfully set the players! Players are " + playerList);
    }


    //for strategy setting
    public void getNextPlayer() {
        if (currentPlayer == playerList.size() - 1) {
            currentPlayer = 0;
        } else {
            currentPlayer++;
        }
        System.out.println("currentPlayer:" + currentPlayer);
    }


    public void setPlayerStrategy(int i, String strategyName) {
        Strategy strategy;
        switch (strategyName) {
            case "Cheater":
                strategy = new Cheater();
                playerList.get(i).setStrategy(strategy);
                break;
            case "Aggressive":
                strategy = new Aggressive();
                playerList.get(i).setStrategy(strategy);
                break;
            case "Benevolent":
                strategy = new Benevolent();
                playerList.get(i).setStrategy(strategy);
                break;
            case "RandomP":
                strategy = new RandomP();
                playerList.get(i).setStrategy(strategy);
                break;
        }
        String name = playerList.get(i).getStrategy() != null ? playerList.get(i).getStrategy().getClass().getSimpleName() : "Human";
        playerList.get(i).setName(name + i);
    }

    /**
     * Set the initial number of players
     *
     * @param playerNum is the number chose by user to decide how mant players will play the game
     */
    public void setNumberofplayers(int playerNum) {
        this.numberofplayers = playerNum;
    }


    /**
     * This method is used to assign all countries to different players equally and randomly
     */

    public void AssignPlayers() {
        log.add("Assigning Players");

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
        log.add(playerList.get(currentPlayer).getName() + "(" + getColorName(playercolors[currentPlayer]) + "), you play first!");
        //check if the first player is computer
        if (getCurPlayer().getStrategy() != null) {
            for (Country country : getCurPlayer().getCountriesOwned()) {
                if (!playerPlaceArmy(country)) {
                    break;
                }
            }
        }
    }

    /**
     * This method is used to determine the initial number of armies for each players
     * Give all players the same number of armies
     */
    public void allocateArmies() {
        //int totalArmy = 30;
        int totalArmy = map.getCountryNum() * 2;
        System.out.println("totalArmy :" + totalArmy);
        this.setInitialArmyNum(totalArmy / playerList.size());
        log.add("Each player allocates " + this.getInitialArmyNum() + " armies at the start");
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

        log.add("Now you can assign your armies whatever you like!");
    }

    /**
     * start up,
     *
     * @param country
     */
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
        System.out.println("index: "+ index);
        if (index == curPlayerArmyList.size()) {
            log.add("Player " + curPlayer.getName() + " has finished army placement");
            currentPlayer++;
            if (currentPlayer < playerList.size()) {
                log.add("Now It is Player " + getPlayNameWithColor(currentPlayer) + "'s turn to place army");
                if (getCurPlayer().getStrategy() != null) {
                    Country placeArmy = null;
                    Collection<Country> countries = getCurPlayer().getCountriesOwned();
                    Iterator<Country> iterator = countries.iterator();
                    if(iterator.hasNext()){
                        placeArmy =  iterator.next();
                    }
                    while (playerPlaceArmy(placeArmy)){
                        if(iterator.hasNext()){
                            placeArmy =  iterator.next();
                        }else {
                            iterator = countries.iterator();
                            placeArmy = iterator.next();
                        }
                    }
                }
            } else {
                log.add("Finished army placement");
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

    /**
     * This is the method to get the name of color
     *
     * @param color
     * @return colorName
     */

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

    /**
     * This is the method to get name with color
     *
     * @param index
     * @return the player's name with color
     */
    public String getPlayNameWithColor(int index) {
        String name = "";
        name = playerList.get(index).getName() + "(" + getColorName(playercolors[index]) + ")";
        return name;
    }

    /**
     * Place only one army to a country by one player each time
     *
     * @param destination is the target country where one army moved to
     */
    public void placeInitialArmy(Player curplayer, Country destination) {
        if (curplayer.getCountriesOwned().contains(destination)) {
            destination.AddArmy();
        } else {
            log.add("Error: you can not doing this moving opearation");
            log.add("since selected country " + destination.getName() + " does not belong to you");
        }
    }

    /**
     * Set the initial army number of each player at the start up phase
     *
     * @param initArmyNum is the number of armies allocated for each player
     */
    public void setInitialArmyNum(int initArmyNum) {
        this.initialArmyNum = initArmyNum;
    }


    /**
     * Get the initial number of armies owned by each player
     *
     * @return number of initial armies
     */
    public int getInitialArmyNum() {
        return this.initialArmyNum;
    }


    /**
     * This is the method used to get current player
     *
     * @return current player
     */
    public Player getCurPlayer() {
        return playerList.get(currentPlayer);
    }

    public String getCurPlayerNameWithColor() {
        return getPlayNameWithColor(currentPlayer);
    }

    /**
     * Get the player list
     *
     * @return playerList
     */
    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    /**
     * Set the initial number of players
     *
     * @param playerNum is the number chose by user to decide how mant players will play the game
     */
    public void setPlayNum(int playerNum) {
        this.numberofplayers = playerNum;
    }


    /**
     * reinforce button function, calculate the number of army player can get .
     */
    public void reinforce() {
        if (reinforceFlag == true) {
            log.add(getCurPlayerNameWithColor() + " has reinforced, can not reinforce again!");
        } else {
            if (getCurPlayer().getCardList().size() >= 5 && cardChangeFlage == false) {
                log.add(getCurPlayerNameWithColor() + " has " + getCurPlayer().getCardList().size() + " cards, you need to exchange the card");
                return;
            } else {
                Player player = playerList.get(currentPlayer);
                int armyNum = player.getNumberOfArmy();
                player.setArmyList(armyNum);
                reinforceFlag = true;
                log.add(getPlayNameWithColor(currentPlayer) + " has " + armyNum + " army");
            }
        }
    }

    /**
     * click the country to add a army
     */
    public void reinforceArmy(Country country) {
        int index = 0;
        Player curPlayer = playerList.get(currentPlayer);
        ArrayList<Army> curPlayerArmyList = playerList.get(currentPlayer).getArmyList();

        while (curPlayerArmyList.get(index).getCountry() != null && index < curPlayerArmyList.size()) {
            index++;
        }
        if (index < curPlayerArmyList.size()) {
            placeInitialArmy(curPlayer, country);
            index++;
        }
        if (index == curPlayerArmyList.size()) {
            log.add("Player " + getPlayNameWithColor(currentPlayer) + " has finished army placement");
            //state = GameState.ATTACK;
        }
    }


    /**
     * Generate the random dice list of one player, can be either attacker or defender
     * And the dice list would be sorted in ascending order
     *
     * @param diceNum is the number of dices they choose
     * @return ArrayList<Integer>  a list of dices
     */
    public static ArrayList<Integer> generateDiceNum(int diceNum) {
        ArrayList<Integer> diceList = new ArrayList<>();
        int randomNumber;
        //generate a random number for each dice in the dice list
        for (int i = 0; i < diceNum; i++) {
            randomNumber = new Random().nextInt(6) + 1;
            diceList.add(randomNumber);
        }
        //making it be in a ascending order
        Collections.sort(diceList);
        return diceList;
    }


    /**
     * When attacker chooses 1 dices, compare the dice number
     *
     * @param attackCounty  it's the attack country
     * @param defendCountry it's the defend country
     */
    public void diceOne(Country attackCounty, Country defendCountry) {
        Player curPlayer = getCurPlayer();

        Player attacker = attackCounty.getPlayer();
        int armyOfAttacker = attackCounty.getArmiesNum();
        int armyOfDefender = defendCountry.getArmiesNum();
        ArrayList<Integer> attackerDiceList = generateDiceNum(1);
        ArrayList<Integer> defenderDiceList = new ArrayList<Integer>();

        if (curPlayer.equals(attacker)) {
            if (armyOfAttacker == 1) {
                log.add("You cannot attack, becasue you need at least 2 army to attack");
            } else if (armyOfAttacker == 2) {
                if (armyOfDefender == 1) {
                    defenderDiceList = generateDiceNum(1);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        defendCountry.reduceArmy();
                    } else {
                        attackCounty.reduceArmy();
                    }
                    checkAfterAtteacked(attackCounty, defendCountry);
                } else {
                    defenderDiceList = generateDiceNum(2);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        attackCounty.reduceArmy();
                    } else {
                        attackCounty.reduceArmy();
                        attackCounty.reduceArmy();
                    }
                    checkAfterAtteacked(attackCounty, defendCountry);
                }
            } else if (armyOfAttacker >= 3) {
                if (armyOfDefender == 1) {
                    defenderDiceList = generateDiceNum(1);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        defendCountry.reduceArmy();
                    } else {
                        attackCounty.reduceArmy();
                    }
                    checkAfterAtteacked(attackCounty, defendCountry);
                } else if (armyOfDefender >= 2) {
                    defenderDiceList = generateDiceNum(2);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        attackCounty.reduceArmy();
                    } else {
                        attackCounty.reduceArmy();
                        attackCounty.reduceArmy();
                    }
                    checkAfterAtteacked(attackCounty, defendCountry);
                }
            }
        } else {
            log.add("Error: it is not your turn!");
        }
    }

    /**
     * When attacker chooses 2 dices, compare the dice number
     *
     * @param attackCounty  it's the attack country
     * @param defendCountry it's the defend country
     */
    public void diceTwo(Country attackCounty, Country defendCountry) {
        Player curPlayer = getCurPlayer();
        Player attacker = attackCounty.getPlayer();
        int armyOfAttacker = attackCounty.getArmiesNum();
        int armyOfDefender = defendCountry.getArmiesNum();
        ArrayList<Integer> attackerDiceList = generateDiceNum(2);
        ArrayList<Integer> defenderDiceList = new ArrayList<Integer>();

        if (curPlayer.equals(attacker)) {
            if (armyOfAttacker == 1) {
                log.add("You cannot attack, becasue you need at least 2 army to attack");
            } else if (armyOfAttacker == 2) {
                log.add("The number of army need at least one more than the number of dice");
            } else if (armyOfAttacker >= 3) {
                if (armyOfDefender == 1) {
                    defenderDiceList = generateDiceNum(1);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        defendCountry.reduceArmy();
                    } else {
                        defendCountry.reduceArmy();
                        attackCounty.reduceArmy();
                    }
                    checkAfterAtteacked(attackCounty, defendCountry);
                } else if (armyOfDefender >= 2) {
                    defenderDiceList = generateDiceNum(2);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        defendCountry.reduceArmy();
                        if (attackerDiceList.get(1) - defenderDiceList.get(1) > 0) {
                            defendCountry.reduceArmy();
                        } else {
                            attackCounty.reduceArmy();
                        }
                        checkAfterAtteacked(attackCounty, defendCountry);
                    } else {
                        attackCounty.reduceArmy();
                        if (attackerDiceList.get(1) - defenderDiceList.get(1) > 0) {
                            defendCountry.reduceArmy();
                        } else {
                            attackCounty.reduceArmy();
                        }
                        checkAfterAtteacked(attackCounty, defendCountry);
                    }
                }
            }
            checkAfterAtteacked(attackCounty, defendCountry);
        } else {
            log.add("Error: it is not your turn!");
        }
    }


    /**
     * When attacker chooses 3 dices, compare the dice number
     *
     * @param attackCtry attack country
     * @param defendCtry attacked country
     */
    public void diceThree(Country attackCtry, Country defendCtry) {
        Player curPlayer = getCurPlayer();
        Player attacker = attackCtry.getPlayer();
        int armyOfAttacker = attackCtry.getArmiesNum();
        int armyOfDefender = defendCtry.getArmiesNum();
        ArrayList<Integer> attackerDiceList = generateDiceNum(3);
        ArrayList<Integer> defenderDiceList = new ArrayList<>();

        if (curPlayer.equals(attacker)) {
            if (armyOfAttacker >= 4) {
                if (armyOfDefender == 0) {
                    defendCtry.setPlayer(attacker);
                } else if (armyOfDefender == 1) {
                    defenderDiceList = generateDiceNum(1);
                    if (attackerDiceList.get(2) - defenderDiceList.get(0) > 0) {
                        defendCtry.reduceArmy();
                    } else {
                        attackCtry.reduceArmy();
                    }
                } else {
                    defenderDiceList = generateDiceNum(2);
                    if (attackerDiceList.get(2) - defenderDiceList.get(1) > 0) {
                        defendCtry.reduceArmy();
                        if (attackerDiceList.get(1) - defenderDiceList.get(0) > 0) {
                            defendCtry.reduceArmy();
                        } else {
                            attackCtry.reduceArmy();
                        }
                    } else {
                        attackCtry.reduceArmy();
                        if (attackerDiceList.get(1) - defenderDiceList.get(0) > 0) {
                            defendCtry.reduceArmy();
                        } else {
                            attackCtry.reduceArmy();
                        }
                    }
                }
            } else {
                log.add("you can choose 3 dices to roll, cause you don't have at least 4 armies");
            }
            checkAfterAtteacked(attackCtry, defendCtry);
        } else {
            log.add("Error: it is not your turn!");
        }

    }


    /**
     * When attacker chooses all-in, compare the dice number
     *
     * @param attackCtry attack country
     * @param defendCtry attacked country
     */
    public void diceAll(Country attackCtry, Country defendCtry) {
        Player curPlayer = getCurPlayer();
        Player attacker = attackCtry.getPlayer();
        int armyOfAttacker = attackCtry.getArmiesNum();


        if (curPlayer.equals(attacker)) {
            int timesOfThree = Math.floorDiv(armyOfAttacker, 4);
            int timesLeft = armyOfAttacker % 4;

            for (int i = 0; i < timesOfThree; i++) {
                diceThree(attackCtry, defendCtry);
            }
            if (timesLeft == 2) {
                diceOne(attackCtry, defendCtry);
            } else if (timesLeft == 3) {
                diceTwo(attackCtry, defendCtry);
            }
        } else {
            log.add("Error: it is not your turn!");

        }
    }


    /**
     * Attack operation for each player
     *
     * @param attackCtry country of attacker
     * @param defendCtry country of defender
     * @param diceNum    number of dice chose by current player
     */
    public void attack(Country attackCtry, Country defendCtry, int diceNum) {
        switch (diceNum) {
            case 1:
                log.add("you choose one dice to attack");
                diceOne(attackCtry, defendCtry);
                break;
            case 2:
                log.add("you choose two dices to attack");
                diceTwo(attackCtry, defendCtry);
                break;
            case 3:
                log.add("you choose three dices to attack");
                diceThree(attackCtry, defendCtry);
                break;
            case 4:
                log.add("you choose all-in to attack");
                diceAll(attackCtry, defendCtry);
                break;
        }
        log.add("Attack to" + defendCtry.getName() + "successfully finished");
    }


    /**
     * This method is to move a number of armies from one country to another country
     *
     * @param armyNum     is the specific number of armies set by players
     * @param curplayer   is the current player
     * @param destination is the country where armies moved to
     * @param originctn   is the country where armies moved from
     */
    public void moveArmyBetweenCountries(int armyNum, Player curplayer, Country destination, Country originctn) {
        System.out.println("moveArmyBetweenCountries --- ");
        int i;
        if (!curplayer.getCountriesOwned().contains(originctn)) {
            log.add("Error: " + originctn.getName() + " is not your country, you car not change the number of army of this country!");
        } else if (!curplayer.getCountriesOwned().contains(destination)) {
            log.add("Error: " + destination.getName() + " is not your country, you are not able to move army to this country!");
        } else if (originctn.getArmiesNum() == 1) {
            //log.add("Error:  There is only 1 army in this country(" + originctn.getName() + "), you can not move it!");
        } else {
            for (i = 0; i < armyNum; i++) {
                originctn.moveOutOneArmy(destination);
                //originctn.moveOutOneArmy();
                //destination.AddArmy();
            }
        }
    }


    /**
     * check when player conquer a country.
     */
    public boolean checkAfterAtteacked(Country attackerCtry, Country defenderCtry) {
        boolean ifEnded = false;
        Player attacked = attackerCtry.getPlayer();
        Player defender = defenderCtry.getPlayer();

        //if the attacker conquer the country
        if (defenderCtry.getArmiesNum() == 0) {
            //remove the country from defender's country list
            defender.getCountriesOwned().remove(defenderCtry);
            //change country's owner
            defenderCtry.setPlayer(attackerCtry.getPlayer());
            //add the country to attacker's country list
            attacked.getCountriesOwned().add(defenderCtry);
            //check if the attacker owns all countries,if yes, then game finished.

            if (attacked.getCountriesOwned().size() == map.getAllCountries().size()) {
                state = GameState.END;
                log.add("=====  check after attacking" + getCurPlayerNameWithColor() + " wined the game! =====");
                ifEnded = true;
                return ifEnded;
            } else if (attackerCtry.getArmiesNum() > 1) {
                //attacker move a army to defender country
                moveArmyBetweenCountries(1, attacked, defenderCtry, attackerCtry);
            }
            log.add(getCurPlayerNameWithColor() + " conquered " + defenderCtry.getName());

            //when attacker conquers at less a country, will get a card
            if (getCardFlag == false) {
                getCardFlag = true;
                getCurPlayer().getNewCard();
            }
            //if the defender lose all his countries, attacker will get all his cards
            if (defender.getCountriesOwned().size() == 0) {
                getCurPlayer().takeOverCards(defender);

                //playerList.remove(defender);
                defender.isAlive = false;
                currentPlayer = playerList.indexOf(attacked);
                System.out.println("2 playerList :" + playerList);
            }
        }
        //System.out.println("percentage of player:" + percentageOfmap(getCurPlayer()));
        return ifEnded;
    }


    /**
     * get the percentage of the map controlled by player
     */
    public String percentageOfmap(Player player) {
        String percnt = "";
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        int ownerCountryNum = player.getCountriesOwned().size();
        int totalCountriesNum = map.getCountryNum();
        percnt = numberFormat.format((float) ownerCountryNum / (float) totalCountriesNum * 100);
        return percnt;
    }


    /**
     * next button function, turn to next player
     */
    public void turnToNextPlayer() {
        if(state == GameState.END){
            return;
        }

        if(round == defaultMaxRound){
            log.add("=====  Game Stop =====");
            log.add("=====  Drawn =====");
            return;
        }
        System.out.println("gameState :" + state);
        System.out.println("currentPlayer :" + getCurPlayerNameWithColor());
        //reset card flag
        getCardFlag = false;
        reinforceFlag = false;
        cardChangeFlage = false;
        state = GameState.CHOOSECARD;
        if (state == GameState.CHOOSECARD) {
            if (currentPlayer == playerList.size() - 1) {
                currentPlayer = 0;
                log.add("Now current player is : " + getPlayNameWithColor(currentPlayer));
                round++;
                //if player is computer, then auto play
                if (!getCurPlayer().isAlive) {
                    turnToNextPlayer();
                } else {
                    if (getCurPlayer().getStrategy() != null) {
                        autoOneTurn();
                        sleep(1500);
                        turnToNextPlayer();
                    }
                }
            } else {
                currentPlayer++;
                if (!getCurPlayer().isAlive) {
                    turnToNextPlayer();
                } else {
                    log.add("Now current player is : " + getPlayNameWithColor(currentPlayer));
                    //if player is computer, then auto play
                    if (getCurPlayer().getStrategy() != null) {
                        autoOneTurn();
                        sleep(1000);
                        turnToNextPlayer();
                    }
                }
            }
        }
    }

    /**
     * for auto play, turn to next player
     */
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
                    log.add("Now current player is : " + getPlayNameWithColor(currentPlayer));
                    round++;
                    autoOneTurn(curGame);
                }
            } else {
                currentPlayer++;
                if (!getCurPlayer().isAlive) {
                    turnToNextPlayerT(curGame);
                } else {
                    log.add("Now current player is : " + getPlayNameWithColor(currentPlayer));
                    autoOneTurn(curGame);
                }
            }
        }
    }

    //before autoplay, must loading map, choosing players, setting maximum round number, setting game number
    public void autoPlay() {
        sleep(1000);
        for (String mapName : mapList) {
            log.add("Map : " + mapName);
            map.reset();
            map.loadMap(mapName);
            //single map
            int curGame = 0;
            while (curGame < gameNum) {
                log.add("===== Game " + curGame + ": Start! =====");
                //reset
                resetForNextGame();
                //start up
                sleep(1000);
                AssignPlayers();// start up
                sleep(1000);
                autoOneTurn(curGame);
                while (round < maxRoundNum && state != GameState.END) {
                    System.out.println("maxRoundNum:" + maxRoundNum);
                    System.out.println("round:" + round);
                    turnToNextPlayerT(curGame);
                }
                if (round == maxRoundNum && state != GameState.END) {
                    log.add("=====  Game " + curGame + ": drawn =====");
                }

                sleep(3000);
                log.add("\n");
                log.add("\n");
                log.add("\n");
                curGame++;
            }
        }
    }

    //for T
    public void autoOneTurn(int curGame) {
        log.add(getCurPlayerNameWithColor() + "Start to Reinforce");
        getCurPlayer().autoReinforce();//need to add auto exchange card
        log.add(getCurPlayerNameWithColor() + "Start to Attack");
        if (getCurPlayer().autoAttack()) {
            String winner = getCurPlayerNameWithColor();
            log.add("=====autoOneTurn  Game " + curGame + ":" + winner + "winned =====");
            curGame++;
            return;
        }
        sleep(2000);
        log.add(getCurPlayerNameWithColor() + "Start to Fortify");
        getCurPlayer().autoFortify();
        state = GameState.CHOOSECARD;
    }

    //for single game
    public void autoOneTurn() {
        log.add(getCurPlayerNameWithColor() + "Start to Reinforce");
        if(state == GameState.CHOOSECARD && stopflag == false){
            getCurPlayer().autoReinforce();
            state = GameState.ATTACK;
        }

        if(state == GameState.ATTACK && stopflag == false){
            log.add(getCurPlayerNameWithColor() + "Start to Attack");
            if (getCurPlayer().autoAttack()) {
                String winner = getCurPlayerNameWithColor();
                log.add("===== "+winner + " : winned ======");
                return;
            }
            state = GameState.FORTIFY;
        }

        if(state == GameState.FORTIFY && stopflag == false){
            log.add(getCurPlayerNameWithColor() + "Start to Fortify");
            getCurPlayer().autoFortify();
            state = GameState.CHOOSECARD;
        }
    }


    public void gameStart() {
        if (getCurPlayer().getStrategy() != null) {
            autoOneTurn();
            turnToNextPlayer();
        } else {
            return;
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

    public void continueToplay(){
        if(getCurPlayer().getStrategy() == null){
            return;
        }else {
            autoOneTurn();
            turnToNextPlayer();
        }
    }

    /**
     * restart the game
     */
    public void restart() {
        map.reset();
        playerList.clear();
        currentPlayer = 0;
        state = GameState.EDITMAP;
        numberofplayers = 0;
        getCardFlag = false;
        cardChangeFlage = false;
        reinforceFlag = false;
        initialArmyNum = 0;
        gameNum = 0;
        maxRoundNum = 0;
        round = 0;
    }

    /**
     * this method used to test moved army
     *
     * @param armyNum     the number of army moved
     * @param curplayer   the current player
     * @param destination the destination country to move
     * @param originctn   the original country to move
     * @return the message for equaling
     */
    public String checkMoveArmyTest(int armyNum, Player curplayer, Country destination, Country originctn) {
        int i;
        String message = null;
        if (!curplayer.getCountriesOwned().contains(originctn)) {
            message = "Error: " + originctn.getName() + " is not your country, you can not change the number of army of this country!";
        } else if (!curplayer.getCountriesOwned().contains(destination)) {
            message = "Error: " + destination.getName() + " is not your country, you are not able to move army to this country!";
        } else if (originctn.getArmiesNum() == 1) {
            message = "Error:  There is only 1 army in this country(" + originctn.getName() + "), you can not move it!";
        } else if (destination.getArmiesNum() >= 18) {
            message = "Error: There are too many armies in this country(" + destination.getName() + "),";
            //log.add("please select another country");
        } else {
            for (i = 0; i < armyNum; i++) {
                originctn.moveOutOneArmy(destination);
                message = "you move" + armyNum + "to your destination country";
            }
        }
        return message;
    }

    public void gameSave(String filename) {
        //save game should stop auto player playing
        stopflag = true;
        String gameInfo = "";
        String mapInfo = map.mapInfo();

        String playersInfo = "";
        for (Player player : playerList) {
            playersInfo = playersInfo + player.playerSave() + "\n";
        }

        String countrysInfo = "";
        for (Country country : map.getAllCountries()) {
            countrysInfo = countrysInfo + country.countrySave() + "\n";
        }

        String gameStateInfo = "gameState = " + state.name();
        String currentPlayerInfo = "currentPlayer = " + currentPlayer;
        String initialArmyNumInfo = "initialArmyNum = " + initialArmyNum;
        String roundInfo = "round = " + round;
        String cardChangeFlageInfo = "cardChangeFlage = " + cardChangeFlage;
        String reinforceFlagInfo = "reinforceFlag = " + reinforceFlag;
        String getCardFlagInfo = "getCardFlag = " + getCardFlag;

        gameInfo = mapInfo + "\n"
                + "[Players]\n"
                + playersInfo + "\n"
//               +"[Countries]\n"
//               + countrysInfo + "\n"
                + "[GameSate]\n"
                + gameStateInfo + "\n"
                + currentPlayerInfo + "\n"
                + initialArmyNumInfo + "\n"
                + roundInfo + "\n"
                + cardChangeFlageInfo + "\n"
                + reinforceFlagInfo + "\n"
                + getCardFlagInfo + "\n";

        System.out.println(gameInfo);

        File outFile = new File(filename);
        if (outFile.exists()) {
            outFile.delete();
        }

        try {
            outFile.createNewFile();
            FileOutputStream outStream = new FileOutputStream(filename, true);
            outStream.write(gameInfo.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String save() {
        // TODO Auto-generated method stub
        String rtnMessage = "";
        String fileName;
        PrintWriter outputStream = null;
        JFrame frm = new JFrame("Save As");
        frm.setAlwaysOnTop(true);
        JPanel p = new JPanel();
        JFileChooser fc = new JFileChooser();
        Container c = frm.getContentPane();

        File f;
        int flag = 0;
        c.setLayout(new FlowLayout());
        c.add(p);
        fc.setDialogTitle("Save Game");

        try {
            File filedir = new File("/Users/mengranli/Desktop/6441-build3_2/");
            fc.setCurrentDirectory(filedir);
            flag = fc.showSaveDialog(frm);
        } catch (HeadlessException he) {
            System.out.println("Save File Dialog ERROR!");
            rtnMessage = "Save File Dialog ERROR!";
        }
        if (flag == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            fileName = f.getAbsolutePath();
            System.out.println(fileName);
            gameSave(fileName);
        }

        return rtnMessage;
    }

    public String TourMapLoad() {
        String fileName = "";
        // TODO Auto-generated method stub
        String rtnMessage = " Load map successfully";
        JFrame frm = new JFrame("Load Map");
        frm.setAlwaysOnTop(true);
        JPanel p = new JPanel();
        JFileChooser fc = new JFileChooser();
        Container c = frm.getContentPane();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("map file", "map");
        fc.setFileFilter(filter);
        File f;
        int flag = 0;

        c.setLayout(new FlowLayout());
        c.add(p);
        fc.setDialogTitle("Open File");

        try {
            File filedir = new File("/Users/mengranli/eclipse-workspace/mapView/");
            fc.setCurrentDirectory(filedir);
            flag = fc.showOpenDialog(frm);
        } catch (HeadlessException head) {
            System.out.println("Open File Dialog ERROR!");
        }

        if (flag == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            fileName = fc.getCurrentDirectory() + "/" + fc.getName(f);
            mapList.add(fileName);
            System.out.println(fileName);
        }
        System.out.println(mapList);
        return fileName;
    }

    public void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



