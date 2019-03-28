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
 * This is the test case for battle results when all-in
 * @author Qing Li
 *
 */
public class DiceAllTest {
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
		attackCtry.increaseArmy();
		attackCtry.increaseArmy();
		attackCtry.increaseArmy();
		attackCtry.increaseArmy();
	}
	
	
	/**
	 * To test error case1: if it is not current player attacking, nothing will be happened
	 */
	@Test
	public void errorCaseTest1() {
		Player thirdPlayer= new Player(2);
		attackCtry.setPlayer(thirdPlayer);
		assertFalse(thirdPlayer.equals(game.getCurPlayer()));
		assertEquals(5,attackCtry.getArmiesNum());
		assertEquals(0,defendCtry.getArmiesNum());	
	}
	
	
	/**
	 * To test when defender has no army and it will be conquered by attacker automatically
	 */
	public void defenderZeroArmyTest() {
		Player attacker= game.getPlayerList().get(0);
		game.diceAll(attackCtry, defendCtry);
		assertEquals(attacker.getID(), defendCtry.getPlayer().getID());		
	}
	
	
	/**
	 * To test when attacker has 5 armies and defender has 1 army
	 */
	public void attackerFiveArmiesTest1() {
		defendCtry.AddArmy();
		game.diceAll(attackCtry, defendCtry);
		assertEquals(attackCtry.getArmiesNum()+defendCtry.getArmiesNum(), 5);
	}
	
	
	/**
	 * To test when attacker has 5 armies and defender has 2 armies
	 */
	public void  attackerFiveArmiesTest2() {
		defendCtry.AddArmy();
		defendCtry.AddArmy();
		game.diceAll(attackCtry, defendCtry);
		assertEquals(attackCtry.getArmiesNum()+defendCtry.getArmiesNum(),5);
	}
	
	
	/**
	 * To test when attacker has 6 armies and defender has 1 army
	 */
	public void attackerSixArmiesTest() {
		attackCtry.AddArmy();
		defendCtry.AddArmy();
		game.diceAll(attackCtry, defendCtry);
		assertEquals(attackCtry.getArmiesNum()+defendCtry.getArmiesNum(), 5);
	}
	
	
	/**
	 * To test when attacker has 7 armies and defender has 2 armies
	 */
	public void attackerSevenArmiesTest() {
		attackCtry.AddArmy();
		attackCtry.AddArmy();
		defendCtry.AddArmy();
		defendCtry.AddArmy();
		game.diceAll(attackCtry, defendCtry);
		assertEquals(attackCtry.getArmiesNum()+defendCtry.getArmiesNum(), 5);
	}
}
