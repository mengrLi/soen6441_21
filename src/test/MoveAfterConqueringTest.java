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
import org.junit.Assert.*;
import org.junit.Before;
import java.util.*;


/**
 * The validation of moving after conquering
 * @author Qing Li
 */
public class MoveAfterConqueringTest {
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
	 * To test when defender is not conquered by attacker
	 */
//	@Test
//	public void notConqueredTest() {
//		defendCtry.increaseArmy();
//		game.checkAfterAtteacked(attackCtry, defendCtry);
//		String percentOfAttacker= "33.33";
//		String percentOfDefender= "33.33";
//		assertEquals(percentOfAttacker, game.percentageOfmap(game.getPlayerList().get(0)));
//		assertEquals(percentOfDefender, game.percentageOfmap(game.getPlayerList().get(1)));
//	}
	
	
	/**
	 * To test if player of conquered country changed after conquering
	 */
	@Test
	public void changePlayerAfterConqueredTest() {
		Country thirdCtry= new Country();
		map.getCountriesMap().put("third", thirdCtry);
		game.getPlayerList().get(1).getCountriesOwned().remove(defendCtry);
        defendCtry.setPlayer(attackCtry.getPlayer());
        game.getPlayerList().get(0).getCountriesOwned().add(defendCtry);
        //To test if defender lose its control on the defend country
		assertEquals(0,game.getPlayerList().get(1).getCountriesOwned().size());
		//To test if conquered country has changed the owner
		assertEquals(game.getPlayerList().get(0),defendCtry.getPlayer());
		//To test if attacker has one more conquered country now
		assertEquals(2, game.getPlayerList().get(0).getCountriesOwned().size());
	}
	
	
	/**
	 * To test when attacker could move one army to the conquered country successfully
	 */
	@Test
	public void moveAfterConqueredTest1() {
		Country thirdCtry= new Country();
		map.getCountriesMap().put("third", thirdCtry);
		game.getPlayerList().get(1).getCountriesOwned().remove(defendCtry);
        defendCtry.setPlayer(attackCtry.getPlayer());
        game.getPlayerList().get(0).getCountriesOwned().add(defendCtry);
		game.moveArmyBetweenCountries(1, game.getPlayerList().get(0), defendCtry, attackCtry);
		assertEquals(1,defendCtry.getArmiesNum());
		assertEquals(1,attackCtry.getArmiesNum());
	}
	
	
	/**
	 * To test the failure case when attacker moves one army to the conquered country
	 */
	@Test
	public void moveAfterConqueredTest2() {
		Country thirdCtry= new Country();
		map.getCountriesMap().put("third", thirdCtry);
		game.getPlayerList().get(1).getCountriesOwned().remove(defendCtry);
        defendCtry.setPlayer(attackCtry.getPlayer());
        game.getPlayerList().get(0).getCountriesOwned().add(defendCtry);
		game.moveArmyBetweenCountries(0, game.getPlayerList().get(0), defendCtry, attackCtry);
		//If attack only has one army, then the movement after conquering will be rejected,
		//and the conquered country will have 0 army
		assertEquals(0,defendCtry.getArmiesNum());
	}
}
