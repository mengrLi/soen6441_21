package test;

import GameModel.*;
import MapModel.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

/**
 * Test case for load game function
 * @author Qing Li
 *
 */
public class LoadGameTest {
	PlayerEngine playerEngine= new PlayerEngine();
	Map map= Map.getMapInstance();
	GameLoad testGame= new GameLoad();
	
	/**
	 * Set up, build a new game and save it with a correct format
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
		playerEngine.state= GameState.REINFORCE;
		playerEngine.currentPlayer=1;
		playerEngine.setInitialArmyNum(60);
		playerEngine.round++;
		playerEngine.round++;
		playerEngine.cardChangeFlage= true;
		playerEngine.gameSave("loadgametestcorrect.game");
	}
	
	/**
	 * To test if game is loaded successfully
	 * when it saves successfully, message will be empty, otherwise, message will display error message
	 */
	@Test
	public void correctLoadGameTest() {
		String message= testGame.Loading("loadgametestcorrect.game");
		assertEquals("",message);
	}
	
	/**
	 * To check the correctness of the game state after loading game
	 */
	@Test
	public void loadGameStateTest() {
		String message= testGame.Loading("loadgametestcorrect.game");
		assertEquals(GameState.REINFORCE, testGame.game.state);
	}
	
	/**
	 * To check the correctness of the current player after loading game
	 */
	@Test
	public void loadCurrentPlayerTest() {
		String message= testGame.Loading("loadgametestcorrect.game");
		assertEquals(1,testGame.game.currentPlayer);
	}
	
	/**
	 * To check the correctness of the initial army number after loading game
	 */
	@Test
	public void loadInitialArmyTest() {
		String message= testGame.Loading("loadgametestcorrect.game");
		assertEquals(60,testGame.game.initialArmyNum);
	}
	
	/**
	 * To check the correctness of the round number after loading game
	 */
	@Test
	public void loadRoundTest() {
		String message= testGame.Loading("loadgametestcorrect.game");
		assertEquals(12,testGame.game.round);
	}
	
	/**
	 * To check the correctness of the card exchange flag status after loading game
	 */
	@Test
	public void loadCardChangeFlagTest() {
		String message= testGame.Loading("loadgametestcorrect.game");
		assertEquals(true,testGame.game.cardChangeFlage);
	}
}
