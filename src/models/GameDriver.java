package models;

import java.util.*;

/**
 * This is the class of the game model acting like a game engine to implement all the functions of the game 
 * 
 */

public class GameDriver {
	
	private int playNum;
	private Map gameMap;
	private GameState gameState;
	private ArrayList<Player> playerList = new ArrayList<>();
	private int currentPlayer; //The ID of current player
	private int initialArmyNum;
	private static int round = 0; 
	
	
	/**
	 * The constructor of GameDriver
	 */
	public GameDriver(){}
	
	
	/**
	 * Set the initial number of players
	 * @param playerNum is the number chose by user to decide how mant players will play the game
	 */
	public void setPlayNum(int playerNum) {
		this.playNum = playerNum;
	}
	
	
	/**
	 * Get the number of players
	 * @return players number
	 */
	public int getPlayNum() {
		return this.playNum;
	}
	
	
	/**
	 * Set the initial army number of each player at the start up phase
	 * @param initArmyNum is the number of armies allocated for each player
	 */
	public void setInitialArmyNum(int initArmyNum) {
		this.initialArmyNum = initArmyNum;
	}
	
	
	/**
	 * Get the initial number of armies owned by each player
	 * @return number of initial armies
	 */
	public int getInitialArmyNum() {
		return this.initialArmyNum;
	}
	
	
	/**
	 * This method is used to select a saved map file and load it as a connected map
	 * @param nameOfMapFile is the filename of the saved map
	 */
	public void setGameMap(String nameOfMapFile) {
		this.gameMap.loadMap(nameOfMapFile);
		this.gameMap.checkMapValidation();
	}
	
	
	/**
	 * Get the game map
	 * @return game map
	 */
	public Map getGameMap() {
		return this.gameMap;
	}
	
	
	/**
	 * Get the list of all players
	 * @return array list of players
	 */
	public ArrayList<Player> getPlayerList(){
		return this.playerList;
	}
	
	
	/**
	 * This method is used to initial the number of players
	 * @param playerNum is the number of players chose by user
	 */
	public void setPlayerList() {
		int i;
		for(i=0;i<this.playNum;i++) {
			Player plyr= new Player(i); //plyr = player
			playerList.add(plyr);
		}
		System.out.println("Successfully set the players! Players are " + playerList);
	}
	
	
	/**
	 * This method is used to assign all countries to different players equally and randomly
	 */
	public void assignCountries() {
		ArrayList<Country> countryList = new ArrayList<>(gameMap.getCountriesMap().values());
		Collections.shuffle(countryList);
		for(int i=0,j=0;i<countryList.size();i++,j++) {
			if(j==playerList.size()) {
				j=0;
			}
			Player curplyr = playerList.get(j); //curplyr = current player
			Country curctn = countryList.get(i);
			curplyr.getCountriesOwned().add(curctn);
			curctn.setPlayer(curplyr);
		}
	}
	
	
	/**
	 * This method is used to determine the initial number of armies for each players
	 * Give all players the same number of armies 
	 */
	public void allocateArmies() {
		int totalArmy = 60;
		this.setInitialArmyNum(totalArmy/playerList.size());
		System.out.println("Each player allocates "  + this.getInitialArmyNum()+ " armies at the start");
		System.out.println("Now you can assign your armies whatever you like!");
	}
	
	
	/**
	 * Get the origin country that the current player want to move armies from
	 * @param player is the current player doing moving operation
	 * @return return origin country
	 */
	public Country getOriginCountry(Player player) {
		Country originctn =;
		if(player.getCountriesOwned().contains(originctn)) {
			return originctn;
		}
		else {
			System.out.println("Error: "+originctn.getName()+" is not your country, you car not change the number of army of this country!");
			return null;
		}
	}
	
	
	/**
	 * Get the target country that the current player want to move armies to 
	 * @param player is the current player doing moving operation
	 * @return return target country
	 */
	public Country getTargetCountry(Player player) {
		Country targetctn = ; //targetctn = target country
		if(player.getCountriesOwned().contains(targetctn)) {
			return targetctn;
		}
		else {
			System.out.println("Error: "+targetctn.getName()+" is not your country, you are not able to move army to this country!");
			return null;
		}
	}
	
	
	/**
	 * This method is to move a number of armies from one country to another country
	 * @param armyNum is the specific number of armies set by players
	 * @param targetCountry is the country where armies moved to 
	 * @param originCountry is the country where armies moved from
	 */
	public void assignArmyBetweenCountries(int armyNum, Country targetCountry, Country originCountry) {
		int i;
		if(targetCountry == null || originCountry == null) {
			System.out.println("Error: you can not doing this moving opearation, since selected country does not belong to you");
		}
		else {
			for(i=0;i<armyNum;i++) {
				targetCountry.AddArmy();
				originCountry.reduceArmy();
			}
		}
	}
	
	
	/**
	 * Place only one army to a country by one player each time
	 * @param targetCountry is the target country where one army moved to
	 */
	public void placeInitialArmy(Country targetCountry) {
		if(targetCountry != null) {
			targetCountry.AddArmy();
		}
		else {
			System.out.println("Error: you can not doing this moving opearation, since selected country does not belong to you");
		}
	}
	
	
	/**
	 * In round-robin fashion, the players place their given armies one by one on their own countries
	 */
	public void placeInitialArmy() {
		int i,j;
		Country targetctn;
		for(i=0,j=0;i<initialArmyNum*playerList.size();i++,j++) {
			if(j==playerList.size()) {
				j=0;
			}
			Player curplyr = playerList.get(j);
			targetctn = getTargetCountry(curplyr);
			placeInitialArmy(targetctn);
		}
	}
	
}
