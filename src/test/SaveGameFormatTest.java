package test;

import GameModel.*;
import MapModel.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

/**
 * Test case for testing save game format 
 * @author Qing Li
 * 
 */
public class SaveGameFormatTest {
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
	 * The testing of correct format of saving game
	 * when it saves successfully, message will be empty, otherwise, message will display error message
	 */
	@Test
	public void correctSaveTest() {
		playerEngine.state= GameState.REINFORCE;
		playerEngine.currentPlayer++;
		playerEngine.setInitialArmyNum(60);
		playerEngine.round++;
		playerEngine.round++;
		playerEngine.cardChangeFlage= true;
		String message= playerEngine.save();
		assertEquals("",message);
	}

}
