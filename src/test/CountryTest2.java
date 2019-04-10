package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameController.*;
import MapController.*;

/**
 * This is the class to test the country class and some methods in it
 * @author anhen
 *
 */
public class CountryTest2 {

	String name;
	Country country;
	Continent continent;
	Player player;
	int ID;
	ArrayList<Country> contiguousCountryList = new ArrayList<Country>();
	ArrayList<Army> ArmyList = new ArrayList<Army>();

	@Before
	public void setvironment() {
		country = new Country("Canada", 01);
	}

	@Test
	public void testGetPlayer() {
		this.player = country.getPlayer();
		assertEquals(this.player, player);
	}

	@Test
	public void testSetPlayer() {
		country.setPlayer(player);
		assertEquals(this.player, player);
	}

	@Test
	public void testGetX() {
		country.setX(20);
		assertEquals(20, country.getX());
	}

	@Test
	public void testSetX() {
		int X = 0;
		country.setX(20);
		assertEquals(20, country.getX());
	}

//	@Test
//	public void testGetcontiguousCountryList() {
//		this.contiguousCountryList = country.getcontiguousCountryList();
//		assertEquals(this.contiguousCountryList, contiguousCountryList);
//	}

	@Test
	public void testSetContiguousCountryList() {
		country.setContiguousCountryList(contiguousCountryList);
		assertEquals(this.contiguousCountryList, contiguousCountryList);
	}

	@Test
	public void testAddContiguousCountry() {
		Country country1 = null;
		country.addContiguousCountry(country1);
		assertEquals(this.contiguousCountryList, contiguousCountryList);
	}

//	@Test
//	public void testRemoveContiguousCountry() {
//		country.removeContiguousCountry(02);
//		assertEquals(null, contiguousCountryList);
//	}

	@Test
	public void testContiguousBelongOthers() {
		boolean flag = country.contiguousBelongOthers();
		assertEquals(false, flag);
	}

//	@Test
//	public void testToString() {
//		country = new Country("Canada", 40, 40, 02);
//		String info1 = country.toString();
//		assertEquals("2-Canada-40,40", info1);
//	}

}
