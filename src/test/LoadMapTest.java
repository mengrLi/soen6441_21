package test;

import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameController.*;
import MapController.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Assert.*;


/**
 * This test case is used to test the function of loading map
 * @author QingLi
 *
 */
public class LoadMapTest {
	Map gameMap=Map.getMapInstance();
	
	/**
	 * To test error case1: the format of map file is invalid
	 */
	@Test
	public void loadMapTestCase1() {
		
		String message = "Error in 1row";
		assertEquals(message,gameMap.loadMap("format-error.map"));	
	}
	
	/**
	 * To test error case2: the information of countries or continents or connection is incomplete
	 */
	@Test
	public void loadMapTestCase2() {
		
		String message = "Loading map error:  countries number is not correct";
		assertEquals(message,gameMap.loadMap("incomplete.map"));
	}
	
	
	/**
	 * To test correct case: load valid map successfully
	 */
	@Test
	public void loadMapTestCase3() {
		
		String message = "Map is successfully connected!";
		String result = gameMap.loadMap("correct.map");
		assertEquals(message,result);
	}
	
}
