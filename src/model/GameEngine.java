  package model;


import java.awt.Color;
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
        for(Player player : playerList){
            for(Country country : player.getCountriesOwned()){
                placeInitialArmy(player,country);
            }
        }

        log.add("Now you can assign your armies whatever you like!");
    }

    /**
     * start up,
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
                state = GameState.REINFORCE;
                currentPlayer = 0;
            }
        }
    }

/**
 * This is the method to get the name of color
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
 * @param index
 * @return the player's name with color
 */
    public String getPlayNameWithColor(int index){
        String name = "";
       name = playerList.get(index).getName() + "("+getColorName(playercolors[index])+")";
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
 * @return current player
 */
    public Player getCurPlayer(){
        return playerList.get(currentPlayer);
    }

    public String getCurPlayerNameWithColor(){
        return getPlayNameWithColor(currentPlayer);
    }
    /**
     * Get the player list
     * @return playerList
     */
    public ArrayList<Player> getPlayerList(){
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
            state = GameState.ATTACK;
        }
    }
    
    
    /**
     * Generate the random dice list of one player, can be either attacker or defender
     * And the dice list would be sorted in ascending order
     * @param diceNum is the number of dices they choose
     * @return ArrayList<Integer>  a list of dices
     */
    public ArrayList<Integer> generateDiceNum(int diceNum) {
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
     * When attacker chooses 3 dices, compare the dice number
     * @param attackCtry attack country
     * @param defendCtry attacked country
     */
    public void diceThree(Country attackCtry, Country defendCtry) {
    	Player attacker= attackCtry.getPlayer();
    	int armyOfAttacker= attackCtry.getArmiesNum();
    	int armyOfDefender= defendCtry.getArmiesNum();
    	ArrayList<Integer> attackerDiceList= generateDiceNum(3);
    	ArrayList<Integer> defenderDiceList= new ArrayList<>();
    	if(armyOfAttacker>=3) {
    		if(armyOfDefender==0) {
        		defenderDiceList= generateDiceNum(1);
        		if(attackerDiceList.get(2)-defenderDiceList.get(0)>0) {
        			defendCtry.setPlayer(attacker);
        		}
        		else {
        			attackCtry.reduceArmy();
        		}
        	}
        	else if(armyOfDefender==1){
        		defenderDiceList= generateDiceNum(1);
        		if(attackerDiceList.get(2)-defenderDiceList.get(0)>0) {
        			defendCtry.reduceArmy();
        		}
        		else {
        			attackCtry.reduceArmy();
        		}
        	}
        	else {
        		defenderDiceList= generateDiceNum(2);
        		if(attackerDiceList.get(2)-defenderDiceList.get(1)>0) {
        			defendCtry.reduceArmy();
        			if(attackerDiceList.get(1)-defenderDiceList.get(0)>0) {
        				defendCtry.reduceArmy();
        			}
        			else {
        				attackCtry.reduceArmy();
        			}
        		}
        		else {
        			attackCtry.reduceArmy();
        			if(attackerDiceList.get(1)-defenderDiceList.get(0)>0) {
        				defendCtry.reduceArmy();
        			}
        			else {
        				attackCtry.reduceArmy();
        			}
        		}
        	}
    	} 
    	else {
    		log.add("you can choose 3 dices to roll, cause you don't have at least 3 armies");
    	}
    }
    
    
    public String attack(Country attacker, Country defender,int attackerDiceNumber) {
    	
    	if(attackerDiceNumber==1) {
    		//TODO:diceOne
    	}
    	else if(attackerDiceNumber==2) {
    		//TODO:diceTwo
    	}
    	else if(attackerDiceNumber==3) {
    		diceThree(attacker,defender);
    	}
    	else {
    		//TODO:all-in
    	}
    	
    	
    	return "attack success";
    }
    
    
   /**
	 * This method is to move a number of armies from one country to another country
	 * @param armyNum is the specific number of armies set by players
	 * @param curplayer is the current player
	 * @param destination is the country where armies moved to
	 * @param originctn is the country where armies moved from
	 */
	public void moveArmyBetweenCountries(int armyNum, Player curplayer, Country destination, Country originctn) {
	    System.out.println("moveArmyBetweenCountries --- ");
		int i;
		if(!curplayer.getCountriesOwned().contains(originctn)) {
			log.add("Error: "+originctn.getName()+" is not your country, you car not change the number of army of this country!");
		}
		else if(!curplayer.getCountriesOwned().contains(destination)) {
			log.add("Error: "+destination.getName()+" is not your country, you are not able to move army to this country!");
		}
		else  if(originctn.getArmiesNum() == 1) {
            log.add("Error:  There is only 1 army in this country(" +originctn.getName()+"), you can not move it!");
        }
		else {
			for(i=0;i<armyNum;i++) {
				originctn.reduceArmy();
				destination.AddArmy();
			}
		}
	}


    /**
     * next button function, turn to next player
     */
    public void turnToNextPlayer(){
	    System.out.println("gameState :" + state);
        if(state == GameState.REINFORCE){
            if(currentPlayer == playerList.size()-1){
                currentPlayer = 0;
                round ++;
            } else {
                currentPlayer ++;
            }
        }
        log.add("Now current player is : " + getPlayNameWithColor(currentPlayer));
        System.out.println("2 next player : "+ playerList.get(currentPlayer).getName());
    }


    /**
     * check when player conquer a country.
     */
    public int checkAfterAtteacked(Country attacker, Country defender){
        int rtn = 0; // 0-not conquer, 1-conquer, 2-game finished,
        //check if the attacker owns all countries,if yes, then game finish.
       if(attacker.getPlayer().getCountriesOwned().size() == map.getAllCountries().size()){
           state = GameState.END;
           rtn = 2;
       }else{
           //if the attacker conquer the country
           if(defender.getArmiesNum() == 0){
               rtn = 1;
               //change country's owner
               defender.setPlayer(attacker.getPlayer());
               //remove the country from defender's country list
               defender.getPlayer().getCountriesOwned().remove(defender);
               //add the country to attacker's country list
               attacker.getPlayer().getCountriesOwned().add(defender);
               //attacker move a army to defender country
               if(attacker.getArmiesNum()>1){
                   moveArmyBetweenCountries(1,attacker.getPlayer(),defender,attacker);
               }//如果attacker只剩一个army，那要想其他办法移动。
           }
       }
       return rtn;
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

