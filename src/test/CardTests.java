package test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import GameModel.*;
import MapModel.*;
import GameView.*;
import MapView.*;
import GameController.*;
import MapController.*;

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
