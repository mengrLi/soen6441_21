package test;

import model.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assert.*;
import org.junit.Before;


/**
 * The validation of attacker and defender
 * @author Qing Li
 *
 */
public class AttackerDefenderValidation {
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
	 * To test if the attack country is belonged to attacker/current player
	 */
	@Test
	public void attackerValidationTest() {
		Player thirdPlayer= new Player(2);
		attackCtry.setPlayer(thirdPlayer);
		assertFalse(thirdPlayer.equals(game.getCurPlayer()));
		//if the attack country is not belonged to attacker, then the attack operation is fail,
		//and there is nothing changes in the army number of both countries
		assertEquals(1,attackCtry.getArmiesNum());
		assertEquals(1,defendCtry.getArmiesNum());	
	}
	
	
	/**
	 * To test if the defend country is belonged to defender
	 */
	@Test
	public void defenderValidationTest() {
		Player forthPlayer= new Player(3);
		defendCtry.setPlayer(forthPlayer);
		assertFalse(forthPlayer.equals(game.getPlayerList().get(1)));
		//if the defend country is not belonged to defender, then the attack operation is fail,
		//and there is nothing changes in the army number of both countries
		assertEquals(1,attackCtry.getArmiesNum());
		assertEquals(1,defendCtry.getArmiesNum());	
	}
	
	
	/**
	 * To test if attacker has more than one army to attack
	 */
	@Test
	public void attackerArmyNumTest() {
		//If attacker has only one army,then the attack operation is rejected,
		//and there is nothing changes in the army number of both countries
		assertEquals(1,attackCtry.getArmiesNum());
		assertEquals(1,defendCtry.getArmiesNum());	
	}
	
}
