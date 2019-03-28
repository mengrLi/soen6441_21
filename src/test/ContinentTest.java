package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;

import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameController.*;
import MapController.*;
import org.junit.Test;

/**
 * This is the class to test the Continent class and methods in it
 * @author anhen
 *
 */
public class ContinentTest {
	private Continent continent;
	private Player player ;
	private Country country, country2;
    private static int idNumber=0;
    private ArrayList<Country> CountryList = new ArrayList<Country>();
    
    @Before
	public void setEnvironment(){
	continent = new Continent("Asia", 20);
	player = new Player(1);
	player = new Player(2);
	country = new Country("Canada",20,20,02);
	country2 = new Country("China",80,80,04);
    }
	@Test
	public void testContinent() {
		assertEquals("Asia", continent.getName());
		assertEquals(20, continent.getValue());
		assertEquals(0, this.idNumber);
	}

	@Test
	public void testSetName() {
		continent.setName("Africa");
		assertEquals("Africa", continent.getName());
	}

	@Test
	public void testSetValue() {
		continent.setValue(50);
		assertEquals(50, continent.getValue());
	}
	
	@Test
	public void testSetPlayer() {
		continent.setPlayer(player);
		assertEquals(player, continent.getPlayer());
	}

	@Test
	public void testGetCountryList() {
		assertEquals(this.CountryList, continent.getCountryList());
	}

	@Test
	public void testSetCountryList() {
		continent.setCountryList(CountryList);
		assertEquals(this.CountryList, CountryList);
	}

	@Test
	public void testAddCountry() {
		continent.addCountry(country);
		assertEquals(this.CountryList, CountryList);
	}

	@Test
	public void testRemoveCountry() {
		continent.addCountry(country);
		continent.addCountry(country2);
		continent.removeCountry(02);
		assertEquals(CountryList.size()+1, continent.getCountryList().size());
	}

}
