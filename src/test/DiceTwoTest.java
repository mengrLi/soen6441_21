package test;

import model.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assert.*;
import org.junit.Before;


/**
 * This is the test case for battle results when throw two dices
 * @author Qing Li
 *
 */
public class DiceTwoTest {
	GameEngine game= new GameEngine();
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
	 * To test error case2: when army number of attacker is less than three,
	 * attacker could not throw two dices
	 */
	public void errorCaseTest2() {
		assertEquals(1,attackCtry.getArmiesNum());
		assertEquals(1,defendCtry.getArmiesNum());	
	}
	
	
	/**
	 * To test when attacker has two dices and defender has one dice
	 */
	public void defenderOneDiceTest() {
		attackCtry.AddArmy();
		attackCtry.AddArmy();
		game.diceTwo(attackCtry, defendCtry);
		assertEquals(defendCtry.getArmiesNum(), 0);
	}
	
	
	/**
	 * To test when attacker has two dices and defender has two dices
	 */
	public void  defenderTwoDiceTest() {
		attackCtry.AddArmy();
		attackCtry.AddArmy();
		defendCtry.AddArmy();
		game.diceTwo(attackCtry, defendCtry);
		assertEquals(attackCtry.getArmiesNum()+defendCtry.getArmiesNum(),3);
	}
}
