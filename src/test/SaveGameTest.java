package test;

import GameModel.*;
import MapModel.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

/**
 * Test case for testing save game function 
 * @author Qing Li
 *
 */
public class SaveGameTest {
	PlayerEngine playerEngine= new PlayerEngine();
	Map map= Map.getMapInstance();
	
	/**
	 * Set up a new game
	 */
	@Before
	public void setUp() {
		playerEngine.setPlayerList(2);
		Player player1= playerEngine.getPlayerList().get(0);
		Player player2= playerEngine.getPlayerList().get(1);
		Country ctry1= new Country();
		Country ctry2= new Country();
		ctry1.setPlayer(player1);
		ctry2.setPlayer(player2);
		map.getCountriesMap().put("Country1", ctry1);
		map.getCountriesMap().put("Country2", ctry2);
	}
	
	/**
	 * To check the correctness of the game state after saving game
	 */
	@Test
	public void gameStateTest() {
		playerEngine.state= GameState.STARTUP;
		playerEngine.gameSave("gamesavetest1.game");
		GameLoad testGame= new GameLoad();
		testGame.Loading("gamesavetest1.game");
		assertEquals(GameState.STARTUP,testGame.game.state);
	}
	
	/**
	 * To check the correctness of the current player after saving game
	 */
	@Test
	public void gameCurrentPlayerTest() {
		playerEngine.currentPlayer++;
		playerEngine.gameSave("gamesavetest2.game");
		GameLoad testGame= new GameLoad();
		testGame.Loading("gamesavetest2.game");
		assertEquals(1,testGame.game.currentPlayer);
	}
	
	/**
	 * To check the correctness of the initial army number after saving game
	 */
	@Test
	public void gameInitialArmyTest() {
		playerEngine.setInitialArmyNum(60);
		playerEngine.gameSave("gamesavetest3.game");
		GameLoad testGame= new GameLoad();
		testGame.Loading("gamesavetest3.game");
		assertEquals(60,testGame.game.getInitialArmyNum());
	}
	
	/**
	 * To check the correctness of the current round number after saving game
	 */
	@Test
	public void gameRoundTest() {
		playerEngine.round++;
		playerEngine.round++;
		playerEngine.gameSave("gamesavetest4.game");
		GameLoad testGame= new GameLoad();
		testGame.Loading("gamesavetest4.game");
		assertEquals(2,testGame.game.round);
	}
	
	/**
	 * To check the correctness of the card exchange flag status after saving game
	 */
	@Test
	public void gameCardChangeFlagTest() {
		playerEngine.cardChangeFlage= true;
		playerEngine.gameSave("gamesavetest5.game");
		GameLoad testGame= new GameLoad();
		testGame.Loading("gamesavetest5.game");
		assertEquals(true,testGame.game.cardChangeFlage);
	}
	
	/**
	 * To check the correctness of the reinforce flag status after saving game
	 */
	@Test
	public void gameReinforceFlagTest() {
		playerEngine.gameSave("gamesavetest6.game");
		GameLoad testGame= new GameLoad();
		testGame.Loading("gamesavetest6.game");
		assertEquals(false,testGame.game.reinforceFlag);
	}
	
	/**
	 * To check the correctness of the get card flag status after saving game
	 */
	@Test
	public void gameGetCardFlagTest() {
		playerEngine.getCardFlag= true;
		playerEngine.gameSave("gamesavetest7.game");
		GameLoad testGame= new GameLoad();
		testGame.Loading("gamesavetest7.game");
		assertEquals(true,testGame.game.getCardFlag);
	}
}
