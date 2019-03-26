package test;

import model.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

import org.junit.Before;
import org.junit.Test;

/**
 * This is the class used to test fortificaiton class
 * @author anhen
 *
 */
public class FortificaitonTest {
	GameEngine game = new GameEngine();
	Country originalCountry = new Country();
	Country destCountry = new Country();
	Player curPlayer = new Player(1);
	int armyMoved;

	@Before
	public void setEnviorment() {
		game.setPlayerList(1);
	}
	@Test
	public void test() {
		String message = "Error: " + originalCountry.getName() + " is not your country, you can not change the number of army of this country!";;
		ArrayList <Country> countryList = new ArrayList<Country>();
		countryList.add(destCountry);
		curPlayer.setCountriesOwned(countryList);
		String string = game.checkMoveArmyTest(armyMoved, curPlayer, destCountry, originalCountry);
		assertEquals(message, string);
	}

}
