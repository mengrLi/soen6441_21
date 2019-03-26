package test;
import model.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Assert.*;
import org.junit.Before;

/**
 * This is the test case for armies results when exchange three same type cards
 * @author Tian Wang
 *
 */

public class CardForArmies1Test {
	Player player0 = new Player(0);
	Player player1 = new Player(1);
	Player player2 = new Player(2);
	/**
	 * Set up the initial state
	 */
	@Before
	public void setUp() {
		
		Card c1 = new Card(player0);
		Card c2 = new Card(player0);
		Card c3 = new Card(player0);
		Card c4 = new Card(player1);
		Card c5 = new Card(player1);
		Card c6 = new Card(player1);
		Card c7 = new Card(player2);
		Card c8 = new Card(player2);
		Card c9 = new Card(player2);
		c1.setCardType(CardType.Infantry);
		c2.setCardType(CardType.Infantry);
		c3.setCardType(CardType.Infantry);
		c4.setCardType(CardType.Cavalry);
		c5.setCardType(CardType.Cavalry);
		c6.setCardType(CardType.Cavalry);
		c7.setCardType(CardType.Artillery);
		c8.setCardType(CardType.Artillery);
		c9.setCardType(CardType.Artillery);

	}
	
	
	/**
	 * test case1: if player0 wants to exchange three infantry cards for armies.
	 */
	@Test
	public void CaseTest1() {

		int number=player0.ExchangeCardforArmy("Infantry (3)");
		assertEquals(5,number);
		
	
	}
	
	
	/**
	 * test case2: if player1 wants to exchange three cavalry cards for armies.
	 */
	public void CaseTest2() {
	
		int number=player1.ExchangeCardforArmy("Cavalry (3)");
		assertEquals(5,number);
	}
	
	
	/**
	 * test case1: if player2 wants to exchange three artillery cards for armies.
	 */
	public void CaseTest3() {

		int number=player2.ExchangeCardforArmy("Artillery (3)");
		assertEquals(5,number);
	}
	


}
