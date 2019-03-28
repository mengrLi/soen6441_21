package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameController.*;
import MapController.*;

/**
 * This is the class to test the army class and methods in it
 * @author anhen
 *
 */
public class ArmyTest {
	private Player player;
	private Army army;
	private Country country;
	
	@Before
	public void setEnvironment() {
		player = new Player(02);
		army = new Army(player);
		country = new Country("Canada", 04);
	}
	@Test
	public void testArmy() {
		army.setPlayer(player);
		assertEquals(player, army.getPlayer());
	}

	@Test
	public void testGetPlayer() {
		assertEquals(this.player, army.getPlayer());
	}

	@Test
	public void testSetPlayer() {
		army.setPlayer(player);
		assertEquals(player, army.getPlayer());
	}

	@Test
	public void testGetCountry() {
		assertEquals(null, army.getCountry());
	}

	@Test
	public void testSetCountry() {
		army.setCountry(country);
		assertEquals(country, army.getCountry());
	}
}
