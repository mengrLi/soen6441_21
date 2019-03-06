package models;

import java.util.*;

/**
 * This is the class of the game model acting like a game engine to implement all the functions of the game 
 * 
 */

public class GameDriver extends Observable{
	
	private int playNum;
	private Map gameMap;
	private GameState gameState;
	private ArrayList<Player> playerList = new ArrayList<>();
	private int currentPlayer; //The ID of current player
	private int initialArmyNum;
	private static int round = 0; 
	public static GameState state;
	
	/**
	 * The constructor of GameDriver
	 */
	public GameDriver(){
	state = GameState.EDITMAP;
	}
	
	
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
		setChanged();
		notifyObservers();
		
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
	public void setPlayerList(int playerNum) {
		int i;
		this.setPlayNum(playerNum);
		for(i=0;i<this.playNum;i++) {
			Player plyr= new Player(i); //plyr = player
			playerList.add(plyr);
		}
		System.out.println("Successfully set the players! Players are " + playerList);
		setChanged();
		notifyObservers();
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
		setChanged();
		notifyObservers();
	}
	
	
	/**
	 * This method is used to determine the initial number of armies for each players
	 * Give all players the same number of armies 
	 */
	public void allocateArmies() {
		int totalArmy = 60;
		this.setInitialArmyNum(totalArmy/playerList.size());
		System.out.println("Each player allocates "  + this.getInitialArmyNum()+ " armies at the start");
		for(int i=0;i<playerList.size();i++) {
			Player curplayer = playerList.get(i);
			curplayer.setArmyList(initialArmyNum);
		}
		System.out.println("Now you can assign your armies whatever you like!");
		setChanged();
		notifyObservers();
	}
	
	
	/**
	 * This method is to move a number of armies from one country to another country
	 * @param armyNum is the specific number of armies set by players
	 * @param curplayer is the current player
	 * @param desination is the country where armies moved to 
	 * @param originctn is the country where armies moved from
	 */
	public void moveArmyBetweenCountries(int armyNum, Player curplayer, Country destination, Country originctn) {
		int i;
		if(!curplayer.getCountriesOwned().contains(originctn)) {
			System.out.println("Error: "+originctn.getName()+" is not your country, you car not change the number of army of this country!");
		}
		else if(!curplayer.getCountriesOwned().contains(destination)) {
			System.out.println("Error: "+destination.getName()+" is not your country, you are not able to move army to this country!");
		}
		else {
			for(i=0;i<armyNum;i++) {
				originctn.reduceArmy();
				destination.AddArmy();
			}
		}
		setChanged();
		notifyObservers();
	}
	
	
	/**
	 * Place only one army to a country by one player each time
	 * @param destination is the target country where one army moved to
	 */
	public void placeInitialArmy(Player curplayer, Country destination) {
		if(curplayer.getCountriesOwned().contains(destination)) {
			destination.AddArmy();
		}
		else {
			System.out.println("Error: you can not doing this moving opearation, since selected country "+destination.getName()+ " does not belong to you");
		}
		setChanged();
		notifyObservers();
	}
	
	
}
