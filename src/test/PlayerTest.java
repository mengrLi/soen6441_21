package test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import model.Army;
import model.Card;
import model.Country;
import model.Player;
/**
 * This is the class to test the player class and  methods in it
 * @author anhen
 *
 */
public class PlayerTest {
    private String name;
    private int ID;
    public Color color;
    private Country capital;
    private Player player;
    private HashMap<Card,Integer> cards = new HashMap<>();
    private ArrayList<Country> countriesOwned = new ArrayList<>();
    private ArrayList<Card> cardList = new ArrayList<Card>();
    private ArrayList<Army> armyList = new ArrayList<>();

	@Before
	public void setEnvironment(){
		player = new Player(2);
	}
	
	@Test
	public void testGetCardList() {
		player.getNewCard();
		assertEquals(1, player.getCardList().size());
	}
	
	@Test
	public void testPlayer() {
		assertEquals(2, player.getID());
	}

	@Test
	public void testSetName() {
		player.setName("Hank");
		assertEquals("Hank", player.getName());
	}

	@Test
	public void testGetID() {
		assertEquals(2, player.getID());
	}

	@Test
	public void testSetColor() {
		player.setColor(color.black);
		assertEquals(color.black, player.getColor());
	}

	@Test
	public void testSetCountriesOwned() {
		player.setCountriesOwned(countriesOwned);
		assertEquals(0, countriesOwned.size());
	}


	@Test
	public void testSetArmyList() {
		player.setArmyList(5);
		assertEquals(5, player.getArmyList().size());
	}

//	@Test
//	public void testCardTypeNumber() {
//		
////	}
//
//	@Test
//	public void testExchangeCard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testTotalExchangeArmy() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveCard() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testRemoveCards() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetNumberOfArmy() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetBonusofContinents() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testExchangeCard1() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testToString() {
//		fail("Not yet implemented");
//	}

}
