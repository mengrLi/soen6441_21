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
public class AggressiveTest {
	PlayerEngine game= new PlayerEngine();
	Player aggressive=new Player(1);
	Player aggressive2=new Player(2);
	Continent con=new Continent("1",1);
	Continent con2=new Continent("2",2);
	Aggressive str=new Aggressive();
//	Strategy str1=new Benevolent();
	Country c1= new Country();
	Country c2= new Country();
	Country c3=new Country();
	Country c4=new Country();
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
		ArrayList<Country> countriesOwned2=new ArrayList<>();
		game.setPlayerList(2);
		aggressive= game.getPlayerList().get(0);
		aggressive2= game.getPlayerList().get(1);
		aggressive.setName("Player");
		aggressive2.setName("Player2");
		con.setPlayer(aggressive);
		con2.setPlayer(aggressive2);
		c1.setPlayer(aggressive);
		c2.setPlayer(aggressive);
		c3.setPlayer(aggressive);
		c4.setPlayer(aggressive2);
		c1.setName("C1");
		c2.setName("C2");
		c3.setName("C3");
		c4.setName("C4");
		aggressive.setArmyList(10);
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
		for(int i=0;i<1;i++){
			c4.AddArmy();
		}
		countriesOwned.add(c1);
		countriesOwned.add(c2);
		countriesOwned.add(c3);
		countriesOwned2.add(c4);
		c1.setContinent(con);
		c2.setContinent(con);
		c3.setContinent(con);
		c4.setContinent(con2);
		aggressive.setCountriesOwned(countriesOwned);
		aggressive2.setCountriesOwned(countriesOwned2);
		con.setCountryList(countriesOwned);
		con2.setCountryList(countriesOwned2);
		con.setValue(1);
		con.setName("Cont");
		con.setName("Cont2");
		connectionmap.add("C2");
		connectionmap.add("C3");
		connectionmap.add("C4");
		map.getConnectionMap().put("C1", connectionmap);
		connectionmap1.add("C1");
		connectionmap1.add("C3");
		map.getConnectionMap().put("C2", connectionmap1);
		connectionmap2.add("C1");
		connectionmap2.add("C2");
		connectionmap2.add("C4");
		map.getConnectionMap().put("C3", connectionmap2);
		map.getContinentMap().put("Cont", con);
		map.getContinentMap().put("Cont2", con2);
		map.getCountriesMap().put("C1", c1);
		map.getCountriesMap().put("C2", c2);
		map.getCountriesMap().put("C3", c3);
		map.getCountriesMap().put("C4", c4);
	}
	/**
	 * To test the function of autoReinforceTest
	 */
	@Test
	public void autoReinforceTest() {
		
		aggressive.setStrategy(str);
		aggressive.autoReinforce();
		//c3 is weaker country and the number of armies will be increased.
		assertEquals(5,c1.getArmiesNum());
		//c2 and c1 is stronger country and the number of armies will not change.
		assertEquals(5,c2.getArmiesNum());
		assertEquals(2,c3.getArmiesNum());
	
	}


}
