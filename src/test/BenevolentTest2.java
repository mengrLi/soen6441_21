package test;
import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameModel.StrategyPlayer.*;
import GameController.*;
import MapController.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Assert.*;
import org.junit.Before;

public class BenevolentTest2 {
	
	PlayerEngine game= new PlayerEngine();
	Player benevolent=new Player(1);
	Continent con=new Continent("1",1);
	Strategy str=new Benevolent();
	Country c1= new Country();
	Country c2= new Country();
	
	/**
	 * Set up the initial state
	 */
	@Before
	public void setUp() {
		ArrayList<Country> countriesOwned=new ArrayList<>();
		game.setPlayerList(1);
		benevolent= game.getPlayerList().get(0);
		con.setPlayer(benevolent);
		c1.setPlayer(benevolent);
		c2.setPlayer(benevolent);
		benevolent.setArmyList(8);
		for(int i=0;i<3;i++){
			c1.AddArmy();	
		}
		for(int i=0;i<5;i++){
			c2.AddArmy();
		}
		countriesOwned.add(c1);
		countriesOwned.add(c2);
		c1.setContinent(con);
		c2.setContinent(con);
		benevolent.setCountriesOwned(countriesOwned);
		con.setCountryList(countriesOwned);
		con.setValue(3);
	}
	/**
	 * To test the function of autoAttack
	 */
	@Test
	public void autoAttackTest() {
		benevolent.setStrategy(str);
		benevolent.autoAttack();
		// do not attack,so the number of armies are not change
		assertEquals(3,c1.getArmiesNum());
		assertEquals(5,c2.getArmiesNum());

	}

}
