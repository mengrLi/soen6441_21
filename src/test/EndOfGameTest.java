package test;

import GameModel.*;
import MapModel.*;
import GameView.*;
import MapModel.Map;
import MapView.*;
import GameController.*;
import MapController.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Assert.*;
import org.junit.Before;
import java.util.*;


/**
 * The test of end of game
 * @author Qing Li
 */
public class EndOfGameTest {
	PlayerEngine game= new PlayerEngine();

	Map map= Map.getMapInstance();
	LogWindow log= new LogWindow() ;
	Country attackCtry= new Country();
	Country defendCtry= new Country();
	
	
	/**
	 * Set up the initial state
	 */
	@Before
	public void setUp() {
		
		game.setPlayerList(2);
		Player attacker= game.getPlayerList().get(0);
		Player defender= game.getPlayerList().get(1);
		map.getCountriesMap().put("AttackCtry", attackCtry);
		map.getCountriesMap().put("DefendCtry", defendCtry);
		attackCtry.setPlayer(attacker);
		defendCtry.setPlayer(defender);
		ArrayList<Country> attackerCountryList= new ArrayList<>();
		ArrayList<Country> defenderCountryList= new ArrayList<>();
		attackerCountryList.add(attackCtry);
		defenderCountryList.add(defendCtry);
		attacker.setCountriesOwned(attackerCountryList);
		defender.setCountriesOwned(defenderCountryList);
		attackCtry.increaseArmy();
		attackCtry.increaseArmy();
	}
	
	
	/**
	 * To test case: game is end
	 */
	@Test
	public void endOfGameTest() {
		game.getPlayerList().get(1).getCountriesOwned().remove(defendCtry);
        defendCtry.setPlayer(attackCtry.getPlayer());
        game.getPlayerList().get(0).getCountriesOwned().add(defendCtry);
        game.state = GameState.END;
        //If game is end, the number of countries of winner is equal to that of the whole map
        assertEquals(2,game.getPlayerList().get(0).getCountriesOwned().size());
        //If game is end, the game state will be changed to END
        assertEquals(GameState.END,game.getCurrentState()); 
        String percentOfWinner= "33.33";
        //If game is end, the percentage of winner will be 100
        assertEquals(percentOfWinner, game.percentageOfmap(game.getPlayerList().get(0)));
	}
	
	
	
	@Test
	public void notEndOfGameTest() {
		//If game is end, the number of countries of winner is not equal to that of the whole map
		assertFalse(game.getPlayerList().get(0).getCountriesOwned().equals(map.getAllCountries()));
		//If game is end, the game state will not be END
		GameState state= GameState.END;
        assertFalse(state.equals(game.getCurrentState())); 
        //If game is end, the percentage of winner will not be 100
        String percentOfWinner= "33.33";
        assertFalse(percentOfWinner.equals(game.percentageOfmap(game.getPlayerList().get(0))));
	}
}

