package test;

import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameController.*;
import MapController.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * This is the class used to test fortification class
 * @author anhen
 */
public class FortificaitonTest {
	PlayerEngine game = new PlayerEngine();
	Country originalCountry = new Country();
	Country destCountry = new Country();
	Player curPlayer = new Player(1);
	int armyMoved;

	/**
	 * set the initial environment;
	 */
	@Before
	public void setEnviorment() {
		game.setPlayerList(1);
	}
	/**
	 * test whether the original country is your country
	 */
	@Test
	public void errorOne() {
		originalCountry.setName("original country");
		destCountry.setName("destination country");
		String message = "Error: original country is not your country, you can not change the number of army of this country!";
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList.add(destCountry);
		curPlayer.setCountriesOwned(countryList);
		String string = game.checkMoveArmyTest(armyMoved, curPlayer, destCountry, originalCountry);
		assertEquals(message, string);
	}

	/**
	 * test whether the destination country is your country
	 */
	@Test
	public void errorTwo() {
		originalCountry.setName("original country");
		destCountry.setName("destination country");
		String message = "Error: destination country is not your country, you are not able to move army to this country!";
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList.add(originalCountry);
		curPlayer.setCountriesOwned(countryList);
		String string = game.checkMoveArmyTest(armyMoved, curPlayer, destCountry, originalCountry);
		assertEquals(message, string);
	}

	/**
	 * test whether the army of original country equal 1
	 */
	@Test
	public void errorThree() {
		originalCountry.setPlayer(curPlayer);
		destCountry.setPlayer(curPlayer);
		originalCountry.increaseArmy();
		destCountry.increaseArmy();
		originalCountry.setName("original country");
		destCountry.setName("destination country");
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList.add(originalCountry);
		countryList.add(destCountry);
		curPlayer.setCountriesOwned(countryList);
		String message = "Error:  There is only 1 army in this country(" + originalCountry.getName()
				+ "), you can not move it!";
		String string = game.checkMoveArmyTest(armyMoved, curPlayer, destCountry, originalCountry);
		assertEquals(message, string);
	}

	/**
	 * test whether the army of destination country more than 18
	 */
	@Test
	public void errorFour() {
		originalCountry.setPlayer(curPlayer);
		destCountry.setPlayer(curPlayer);
		originalCountry.increaseArmy();
		originalCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		destCountry.increaseArmy();
		originalCountry.setName("original country");
		destCountry.setName("destination country");
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList.add(originalCountry);
		countryList.add(destCountry);
		curPlayer.setCountriesOwned(countryList);
		String message = "Error: There are too many armies in this country(" + destCountry.getName() + "),";
		String string = game.checkMoveArmyTest(armyMoved, curPlayer, destCountry, originalCountry);
		assertEquals(message, string);
	}

	/**
	 * test the successful situation,and move army
	 */
	@Test
	public void pass() {
		armyMoved = 1;
		originalCountry.setPlayer(curPlayer);
		destCountry.setPlayer(curPlayer);
		originalCountry.increaseArmy();
		originalCountry.increaseArmy();
		destCountry.increaseArmy();
		originalCountry.setName("original country");
		destCountry.setName("destination country");
		ArrayList<Country> countryList = new ArrayList<Country>();
		countryList.add(originalCountry);
		countryList.add(destCountry);
		curPlayer.setCountriesOwned(countryList);
		String message = "you move" + armyMoved + "to your destination country";
		String string = game.checkMoveArmyTest(armyMoved, curPlayer, destCountry, originalCountry);
		assertEquals(message, string);
	}
}
