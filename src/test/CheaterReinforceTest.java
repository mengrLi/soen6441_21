package test;

import GameModel.*;
import GameModel.StrategyPlayer.Cheater;
import MapModel.Map;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;

public class CheaterReinforceTest {
	PlayerEngine playerEngine= new PlayerEngine();
	Map map= Map.getMapInstance();
	
	
	@Test
	public void cheaterReinforceTest() {
		Player cheater= new Player(0);
		cheater.setStrategy(new Cheater());
		Country ctry1= new Country();
		Country ctry2= new Country();
		Country ctry3= new Country();
		ctry1.setPlayer(cheater);
		ctry2.setPlayer(cheater);
		ctry3.setPlayer(cheater);
		//country1 has 2 armies
		ctry1.increaseArmy();
		ctry1.increaseArmy();
		//country2 has 2 armies
		ctry2.increaseArmy();
		ctry2.increaseArmy();
		//country3 has 2 army
		ctry3.increaseArmy();
		ctry3.increaseArmy();
		ArrayList<Country> countryList= new ArrayList<>();
		countryList.add(ctry1);
		countryList.add(ctry2);
		countryList.add(ctry3);
		cheater.setCountriesOwned(countryList);
		cheater.autoReinforce();
		assertEquals(4,ctry1.getArmiesNum());
		assertEquals(4,ctry2.getArmiesNum());
		assertEquals(4,ctry3.getArmiesNum());
		assertEquals(12,cheater.getArmyList().size());
	}
	
}
