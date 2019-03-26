package test;
import model.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.Assert.*;
import org.junit.Before;

public class CardForArmies2Test {
	
	Player player0 = new Player(0);
	
	/**
	 * Set up the initial state
	 */
	@Before
	public void setUp() {
		
		Card c1 = new Card(player0);
		Card c2 = new Card(player0);
		Card c3 = new Card(player0);
		c1.setCardType(CardType.Infantry);
		c2.setCardType(CardType.Cavalry);
		c3.setCardType(CardType.Artillery);
	}
	
	
	/**
	 * test case: if player0 wants to exchange three different types cards for armies.
	 */
	@Test
	public void CaseTest() {

		int number=player0.ExchangeCardforArmy("Infantry (1),  Cavalry (1), Artillery (1)");
		assertEquals(5,number);
		
	
	}
	
	


}
