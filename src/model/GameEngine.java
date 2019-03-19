package model;


import java.awt.Color;
import java.text.NumberFormat;
import java.util.*;

import View.*;
import model.Country;




/**
 * This is the game engine which is a singleton class and
 * controls the game flow
 *
 */
public class GameEngine {
    public static LogWindow log;

    public static GameState state;//Game State
    private Map map = Map.getMapInstance();
    private static ArrayList<Player> playerList = new ArrayList<>();
    public int numberofplayers = 0;
    private static int currentPlayer = 0; //The ID of current player
    private boolean getCardFlag = false; // if player conquer at less a country, will get a card, flag is true
    private int initialArmyNum;
    public static int round = 0;
    Color playercolors[] = {Color.lightGray, Color.MAGENTA, Color.cyan, Color.GREEN, Color.yellow};


    /**
     * Game Engine Constructor, creates map and sets the game state to Edit mode
     */
    public GameEngine() {
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
            Player plyr = new Player(i); //plyr = player
            plyr.setColor(playercolors[i]);
            plyr.setName("player" + i);
            playerList.add(plyr);
        }
        System.out.println("Successfully set the players! Players are " + playerList);
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
    public void playerPlaceArmy(Country country) {

        int index = 0;
        Player curPlayer = playerList.get(currentPlayer);
        ArrayList<Army> curPlayerArmyList = playerList.get(currentPlayer).getArmyList();
        System.out.println("curPlayerArmyList " + curPlayerArmyList);

        while (curPlayerArmyList.get(index).getCountry() != null && index < curPlayerArmyList.size()) {
            index++;
        }
        if (index < curPlayerArmyList.size()) {
            placeInitialArmy(curPlayer, country);
            index++;
        }
        if (index == curPlayerArmyList.size()) {
            log.add("Player " + curPlayer.getName() + " has finished army placement");
            currentPlayer++;
            if (currentPlayer < playerList.size()) {
                log.add("Now It is Player " + getPlayNameWithColor(currentPlayer) + "'s turn to place army");
            } else {
                log.add("Finished army placement");
                //state = GameState.REINFORCE;
                currentPlayer = 0;
            }
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
        Player player = playerList.get(currentPlayer);
        int armyNum = player.getNumberOfArmy();
        player.setArmyList(armyNum);
        log.add(getPlayNameWithColor(currentPlayer) + " has " + armyNum + " army");

    }

    /**
     * click the country to add a army
     */
    public void reinforceArmy(Country country) {
        int index = 0;
        Player curPlayer = playerList.get(currentPlayer);
        ArrayList<Army> curPlayerArmyList = playerList.get(currentPlayer).getArmyList();
        System.out.println("curPlayerArmyList " + curPlayerArmyList);

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
     * @param curPlayer current player
     */
    public void diceOne(Country attackCounty, Country defendCountry, Player curPlayer) {

        Player attacker = attackCounty.getPlayer();
        int armyOfAttacker = attackCounty.getArmiesNum();
        int armyOfDefender = defendCountry.getArmiesNum();
        ArrayList<Integer> attackerDiceList = generateDiceNum(1);
        ArrayList<Integer> defenderDiceList = new ArrayList<Integer>();
        
        if(curPlayer.equals(attacker)) {
        	if (armyOfAttacker == 1) {
                log.add("You cannot attack, becasue you need at least 2 army to attack");
            } else if (armyOfAttacker == 2) {
                if (armyOfDefender == 1) {
                    defenderDiceList = generateDiceNum(1);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        defendCountry.setPlayer(attacker);
                    } else {
                        attackCounty.reduceArmy();
                    }
                } else {
                    defenderDiceList = generateDiceNum(2);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        attackCounty.reduceArmy();
                    } else {
                        attackCounty.reduceArmy();
                        attackCounty.reduceArmy();
                    }
                }
            } else if (armyOfAttacker >= 3) {
                if (armyOfDefender == 1) {
                    defenderDiceList = generateDiceNum(1);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        defendCountry.setPlayer(attacker);
                    } else {
                        attackCounty.reduceArmy();
                    }
                } else if (armyOfDefender >= 2) {
                    defenderDiceList = generateDiceNum(2);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        attackCounty.reduceArmy();
                    } else {
                        attackCounty.reduceArmy();
                        attackCounty.reduceArmy();
                    }
                }
            }
            checkAfterAtteacked(attackCounty, defendCountry);
        }
        else {
        	log.add("Error: it is not your turn!");
        }
    }


    /**
     * When attacker chooses 2 dices, compare the dice number
     *
     * @param attackCounty  it's the attack country
     * @param defendCountry it's the defend country
     * @param curPlayer current player
     */
    public void diceTwo(Country attackCounty, Country defendCountry, Player curPlayer) {
        Player attacker = attackCounty.getPlayer();
        int armyOfAttacker = attackCounty.getArmiesNum();
        int armyOfDefender = defendCountry.getArmiesNum();
        ArrayList<Integer> attackerDiceList = generateDiceNum(2);
        ArrayList<Integer> defenderDiceList = new ArrayList<Integer>();
        
        if(curPlayer.equals(attacker)) {
        	if (armyOfAttacker == 1) {
                log.add("You cannot attack, becasue you need at least 2 army to attack");
            } else if (armyOfAttacker == 2) {
                log.add("The number of army need at least one more than the number of dice");
            } else if (armyOfAttacker >= 3) {
                if (armyOfDefender == 1) {
                    defenderDiceList = generateDiceNum(1);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        defendCountry.setPlayer(attacker);
                    } else {
                        attackCounty.reduceArmy();
                    }
                } else if (armyOfDefender >= 2) {
                    defenderDiceList = generateDiceNum(2);
                    if (attackerDiceList.get(0) - defenderDiceList.get(0) > 0) {
                        defendCountry.reduceArmy();
                        if (attackerDiceList.get(1) - defenderDiceList.get(1) > 0) {
                            defendCountry.reduceArmy();
                        } else {
                            attackCounty.reduceArmy();
                        }
                    } else {
                        attackCounty.reduceArmy();
                        if (attackerDiceList.get(1) - defenderDiceList.get(1) > 0) {
                            defendCountry.reduceArmy();
                        } else {
                            attackCounty.reduceArmy();
                        }
                    }
                }
            }
            checkAfterAtteacked(attackCounty, defendCountry);
        }
        else {
        	log.add("Error: it is not your turn!");
        }
    }


    /**
     * When attacker chooses 3 dices, compare the dice number
     *
     * @param attackCtry attack country
     * @param defendCtry attacked country
     * @param curPlayer current player
     */
    public void diceThree(Country attackCtry, Country defendCtry, Player curPlayer) {
        Player attacker = attackCtry.getPlayer();
        int armyOfAttacker = attackCtry.getArmiesNum();
        int armyOfDefender = defendCtry.getArmiesNum();
        ArrayList<Integer> attackerDiceList = generateDiceNum(3);
        ArrayList<Integer> defenderDiceList = new ArrayList<>();
        
        if(curPlayer.equals(attacker)) {
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
        }
        else {
        	log.add("Error: it is not your turn!");
        }
        
    }
    
    
    /**
     * When attacker chooses all-in, compare the dice number
     * @param attackCtry attack country
     * @param defendCtry attacked country
     * @param curPlayer current player
     */
    public void diceAll(Country attackCtry, Country defendCtry, Player curPlayer) {
    	Player attacker = attackCtry.getPlayer();
        int armyOfAttacker = attackCtry.getArmiesNum();
        
        
        if(curPlayer.equals(attacker)) {
        	while(armyOfAttacker>=4) {
        		diceThree(attackCtry,defendCtry,curPlayer);
        	}
        	int timesLeft= armyOfAttacker%3;
        	if(timesLeft==0) {
        		diceTwo(attackCtry,defendCtry,curPlayer);
        	}
        	else if(timesLeft==2) {
        		diceOne(attackCtry,defendCtry,curPlayer);
        	}
        }
        else {
        	log.add("Error: it is not your turn!");
        }        
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
            log.add("Error:  There is only 1 army in this country(" + originctn.getName() + "), you can not move it!");
        } else if(destination.getArmiesNum()>=18) {
        	log.add("Error: There are too many armies in this country(" + destination.getName() + "), for equity, please select other countries");
        } else {
            for (i = 0; i < armyNum; i++) {
                originctn.reduceArmy();
                destination.AddArmy();
            }
        }
    }


    /**
     * next button function, turn to next player
     */
    public void turnToNextPlayer() {
        System.out.println("gameState :" + state);
        if (state == GameState.CHOOSECARD) {
            if (currentPlayer == playerList.size() - 1) {
                currentPlayer = 0;
                round++;
            } else {
                currentPlayer++;
            }
            //reset card flag
            getCardFlag = false;

        }
        log.add("Now current player is : " + getPlayNameWithColor(currentPlayer));
        System.out.println("2 next player : " + playerList.get(currentPlayer).getName());
    }


    /**
     * check when player conquer a country.
     */
    public void checkAfterAtteacked(Country attackerCtry, Country defenderCtry) {
        //if the attacker conquer the country
        if (defenderCtry.getArmiesNum() == 0) {
            //remove the country from defender's country list
            defenderCtry.getPlayer().getCountriesOwned().remove(defenderCtry);
            //change country's owner
            defenderCtry.setPlayer(attackerCtry.getPlayer());
            //add the country to attacker's country list
            attackerCtry.getPlayer().getCountriesOwned().add(defenderCtry);
            //check if the attacker owns all countries,if yes, then game finished.
            if (attackerCtry.getPlayer().getCountriesOwned().size() == map.getAllCountries().size()) {
                state = GameState.END;
                log.add(getCurPlayerNameWithColor() + " wined the game!");
                return;
            }else if (attackerCtry.getArmiesNum() > 1) {
                //attacker move a army to defender country
                moveArmyBetweenCountries(1, attackerCtry.getPlayer(), defenderCtry, attackerCtry);
            }
            log.add(getCurPlayerNameWithColor() + " conquered " + defenderCtry.getName());

            //when attacker conquers at less a country, will get a card
            if(!getCardFlag){
                getCardFlag = true;
                getCurPlayer().getNewCard();
                System.out.println(getCurPlayerNameWithColor() + " card list :" + getCurPlayer().getCardList());
            }
            //if the defender lose all his countries, attacker will get all his cards
            if(defenderCtry.getPlayer().getCountriesOwned().size() == 0){
                getCurPlayer().takeOverCards(defenderCtry.getPlayer());
            }
        }
        System.out.println("percentage of player:" + percentageOfmap(getCurPlayer()));
    }



    /**
     * get the percentage of the map controlled by player
     */
    public String percentageOfmap(Player player){
        String percnt = "";
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(2);

        int ownerCountryNum = player.getCountriesOwned().size();
        int totalCountriesNum = map.getCountryNum();
        percnt = numberFormat.format((float)ownerCountryNum/(float)totalCountriesNum*100);
        return percnt;
    }




    /**
     * restart the game
     */
    public void restart(){
        map.reset();
        playerList.clear();
        currentPlayer = 0;
        state = GameState.EDITMAP;
    }

}

