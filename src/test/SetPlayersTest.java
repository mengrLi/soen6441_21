package test;

import model.*;
import java.awt.Color;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import org.junit.Assert.*;

/**
 * This class is used to test the function of setting players
 * @author QingLi
 *
 */
public class SetPlayersTest {
	Player ply0,ply1,ply2,ply3,ply4;
	ArrayList<Player> playerList = new ArrayList<Player>();
	Color playercolors[] = {Color.lightGray, Color.MAGENTA, Color.cyan, Color.GREEN, Color.yellow};
	
	
	/**
	 * Initial players
	 */
	@Before
	public void setup() {
		
		ply0 = new Player(0);
		ply1 = new Player(1);
		ply2 = new Player(2);
		ply3 = new Player(3);
		ply4 = new Player(4);
		ply0.setName("player0");
		ply0.setColor(playercolors[0]);
		ply1.setName("player1");
		ply1.setColor(playercolors[1]);
		ply2.setName("player2");
		ply2.setColor(playercolors[2]);
		ply3.setName("player3");
		ply3.setColor(playercolors[3]);
		ply4.setName("player4");
		ply4.setColor(playercolors[4]);
		
	}
	
	/**
	 * To test the function of setting 2 players
	 */
	@Test
	public void setPlayersNumTest1() {
		GameEngine game1 =new GameEngine();
		game1.setPlayerList(2);
		assertEquals(ply0.getName(), game1.getPlayerList().get(0).getName());
		assertEquals(ply1.getName(), game1.getPlayerList().get(1).getName());
		assertEquals(ply0.getColor(), game1.getPlayerList().get(0).getColor());
		assertEquals(ply1.getColor(), game1.getPlayerList().get(1).getColor());
	}
	
}
