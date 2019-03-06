package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import model.Army;
import model.Continent;
import model.Country;
import model.Player;
/**
 * This is the class to test the country class and some methods in it
 * @author anhen
 *
 */
public class CountryTest {
	
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
	public void testCountryStringInt() {
		country = new Country("Canada", 01);
		assertEquals("Canada", country.getName());
		assertEquals(01, country.getID());
	}

	@Test
	public void testCountryStringIntIntInt() {
		country = new Country("Canada", 20, 20 ,02);
		assertEquals("Canada", country.getName());
		assertEquals(20, country.getX());
		assertEquals(20, country.getY());
		assertEquals(02, country.getID());
	}

	@Test
	public void testCountryStringIntIntContinentInt() {
		country = new Country("Canada", 20, 20 , continent, 03);
		assertEquals("Canada", country.getName());
		assertEquals(20, country.getX());
		assertEquals(20, country.getY());
		assertEquals(this.continent, continent);
		assertEquals(03, country.getID());
	}

	@Test
	public void testGetName() {
		this.name = "America";
		assertEquals("America", this.name);
	}

	@Test
	public void testSetName() {
		country.setName("China");
		assertEquals("China", country.getName());
		}

	@Test
	public void testGetContinent() {
		this.continent = country.getContinent();
		assertEquals(this.continent, continent);
	}

	@Test
	public void testSetContinent() {
		country.setContinent(continent);
		assertEquals(this.continent, continent);
	}


}
