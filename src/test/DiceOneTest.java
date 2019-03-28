package test;

import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameController.*;
import MapController.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assert.*;
import org.junit.Before;


/**
 * This is the test case for battle results when throw one dice
 * @author Qing Li
 *
 */
public class DiceOneTest {
	PlayerEngine game= new PlayerEngine();
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
		attackCtry.setPlayer(attacker);
		defendCtry.setPlayer(defender);
		attackCtry.increaseArmy();
		defendCtry.increaseArmy();
	}
	
	
	/**
	 * To test error case1: if it is not current player attacking, nothing will be happened
	 */
	@Test
	public void errorCaseTest1() {
		Player thirdPlayer= new Player(2);
		attackCtry.setPlayer(thirdPlayer);
		assertFalse(thirdPlayer.equals(game.getCurPlayer()));
		assertEquals(1,attackCtry.getArmiesNum());
		assertEquals(1,defendCtry.getArmiesNum());	
	}
	
	
	/**
	 * To test error case2: when army number of attacker is less than two,
	 * attacker could not throw one dice
	 */
	public void errorCaseTest2() {
		assertEquals(1,attackCtry.getArmiesNum());
		assertEquals(1,defendCtry.getArmiesNum());	
	}
	
	
	/**
	 * To test when attacker has one dice and defender has one dice
	 */
	public void defenderOneDiceTest() {
		attackCtry.AddArmy();
		game.diceOne(attackCtry, defendCtry);
		assertEquals(attackCtry.getArmiesNum()+defendCtry.getArmiesNum(), 2);
	}
	
	
	/**
	 * To test when attacker has one dice and defender has two dices
	 */
	public void  defenderTwoDiceTest() {
		attackCtry.AddArmy();
		defendCtry.AddArmy();
		game.diceOne(attackCtry, defendCtry);
		assertEquals(defendCtry.getArmiesNum(),2);
	}
	
	
	
	
	
	
	
	
}
