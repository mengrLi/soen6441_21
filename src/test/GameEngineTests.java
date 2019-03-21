package test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import model.Player;
import model.Country;

/**
 * This is the class to test the GameEngine class and methods in it
 * @author
 *
 */
public class GameEngineTests {
	
	Player player1 = new Player(1);
	Player player2= new Player(2);
	
	/**
	 * Initialization method
	 * @throws Exception
	 */
	@Before
	
	public void setUp() throws Exception {
		
		/*ArrayList<Country> countryList = new ArrayList<Country>();
		ArrayList<Country> countryList1 = new ArrayList<Country>();*/
		for(int i=0; i<10; i++) {
			Country country = new Country();
			player1.getCountriesOwned().add(country);
			
		}
		
		for(int i=0; i<5; i++) {
			Country country = new Country();
			player2.getCountriesOwned().add(country);
		}
		
		player1.settimesArmyforCards(0);
		player2.settimesArmyforCards(1);
		
	}
			
	
	/**
	 * This test case tests calculation of number of reinforcement armies
	 */
	@Test
	public void testmoveArmyBetweenCountries() {
		int expectedResult = 8;
		int result;
//		result = player1.exchangeCard()+player1.NumberOfArmy();
//		assertEquals(expectedResult, result);
//
//		expectedResult = 11;
//		result = player2.exchangeCard()+player2.NumberOfArmy();
//		assertEquals(expectedResult, result);
	}
	

}
