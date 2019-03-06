package test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Player;
import model.Card;
import model.CardType;

/**
 * This is the class to test the card class and methods in it
 * @author anhen
 *
 */
public class CardTests {
	
	String cardTypeStr = "Infantry";
	String CCardTypeStr;
	
	/**
	 * Set up before test
	 */
	@Before
	public void setUp(){	
		
		Player player0 = new Player(0);
		Card c = new Card(player0);
		c.setCardType(CardType.Infantry);
		CCardTypeStr = c.getCardType().toString();					
	}
	
	@Test
	public void testCard() {	
		assertTrue(cardTypeStr == CCardTypeStr);
	}
	
	

}
