package test;

import GameModel.*;
import GameModel.StrategyPlayer.*;
import MapModel.Map;
import GameController.testTournament;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Before;

/**
 * Test case for tournament mode
 * @author Qing Li
 *
 */
public class TournamentTest {
	PlayerEngine playerEngine= new PlayerEngine();
	Map map= Map.getMapInstance();
	
	/**
	 * To build up a new player list and a new map list for tournament mode
	 */
	@Before
	public void setUp() {
		
		playerEngine.setPlayerList(2);
		Player player1= playerEngine.getPlayerList().get(0);
		player1.setStrategy(new Benevolent());
		Player player2= playerEngine.getPlayerList().get(1);
		player2.setStrategy(new Benevolent());
		String filename= playerEngine.TourMapLoad();
	}
	
	/**
	 * To test if tournament is done and if the result of tournament is exact what we expected
	 */
	@Test
	public void tournamentResultTest() {
		testTournament tournament= new testTournament(GameState.STARTUP, playerEngine.getPlayerList(),
				1,20,playerEngine.mapList);
		tournament.autoPlay();
		assertEquals(20,tournament.round);
		assertEquals(GameState.CHOOSECARD,tournament.state);
		assertEquals(1,tournament.curGame);
		assertEquals("\n "+"Map-1-Game0 : Draw",tournament.tournamentResult.get(0));
	}
}
