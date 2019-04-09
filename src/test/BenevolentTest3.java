package test;
import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameModel.StrategyPlayer.*;
import GameController.*;
import MapController.*;
import org.junit.Test;
import java.util.Collection;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Assert.*;
import org.junit.Before;

public class BenevolentTest3 {
	PlayerEngine game= new PlayerEngine();
	Player benevolent=new Player(1);
	Continent con=new Continent("1",1);
	Strategy str=new Benevolent();
	Strategy str1=new Benevolent();
	Country c1= new Country();
	Country c2= new Country();
	Country c3=new Country();
	Map map=Map.getMapInstance();
	Collection<String> connectionmap=new ArrayList <String>();
	Collection<String> connectionmap1=new ArrayList <String>();
	Collection<String> connectionmap2=new ArrayList <String>();
	
	/**
	 * Set up the initial state
	 */
	@Before
	public void setUp() {
		ArrayList<Country> countriesOwned=new ArrayList<>();
		game.setPlayerList(1);
		benevolent= game.getPlayerList().get(0);
		benevolent.setName("Player");
		con.setPlayer(benevolent);
		c1.setPlayer(benevolent);
		c2.setPlayer(benevolent);
		c3.setPlayer(benevolent);
		c1.setName("C1");
		c2.setName("C2");
		c3.setName("C3");
		benevolent.setArmyList(10);
		// c1 has 3 armies
		//c2 has 5 armies
		//c3 has 2 armies
		for(int i=0;i<3;i++){
			c1.AddArmy();	
		}
		for(int i=0;i<5;i++){
			c2.AddArmy();
		}
		for(int i=0;i<2;i++){
			c3.AddArmy();
		}
		countriesOwned.add(c1);
		countriesOwned.add(c2);
		countriesOwned.add(c3);
		c1.setContinent(con);
		c2.setContinent(con);
		c3.setContinent(con);
		benevolent.setCountriesOwned(countriesOwned);
		con.setCountryList(countriesOwned);
		con.setValue(1);
		con.setName("Cont");
		connectionmap.add("C2");
		connectionmap.add("C3");
		map.getConnectionMap().put("C1", connectionmap);
		connectionmap1.add("C1");
		connectionmap1.add("C3");
		map.getConnectionMap().put("C2", connectionmap1);
		connectionmap2.add("C1");
		connectionmap2.add("C2");
		map.getConnectionMap().put("C3", connectionmap2);
		map.getContinentMap().put("Cont", con);
		map.getCountriesMap().put("C1", c1);
		map.getCountriesMap().put("C2", c2);
		map.getCountriesMap().put("C3", c3);
	}
	/**
	 * To test if the attack country is belonged to attacker/current player
	 */
	@Test
	public void autoReinforceTest() {
		
		//autoReinforce
		benevolent.setStrategy(str);
		benevolent.autoReinforce();
		//c3 is the weakest country,so the number of armies will be increased.
		assertEquals(4,c3.getArmiesNum());
		//the number of armies will not change in c1
		assertEquals(3,c1.getArmiesNum());
		//the number of armies will not change in c2
		assertEquals(5,c2.getArmiesNum());
		
		//autoFortify
		benevolent.setStrategy(str1);
		benevolent.autoFortify();
		//after autoFortify c1 become the weaker country and c2 become the strong country
		//c2 will move one army to c1
		assertEquals(4,c1.getArmiesNum());
		assertEquals(4,c2.getArmiesNum());
		//the number of armies in c3 will not change.
		assertEquals(4,c3.getArmiesNum());				
	}

}
