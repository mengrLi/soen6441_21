package test;

import GameModel.*;
import MapModel.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;

/**
 * Test case for load game format
 * @author Qing Li
 *
 */
public class LoadGameFormatTest {
	PlayerEngine playerEngine= new PlayerEngine();
	Map map= Map.getMapInstance();
	GameLoad testGame= new GameLoad();
	
	/**
	 * Set up, build a new game and save it with an error format
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
	 * To test the error format of .game file
	 * Here the loadgametesterror.game have wrong format in the [WRONG GAME FORMAT](row 1)
	 */
	@Test
	public void loadGameErrorTest() {
		String message= testGame.LoadGame();
		assertEquals("Error in 1row",message);
	}
	
}
