package Models;


import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.security.acl.Owner;
import java.sql.Time;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TimerTask;

import View.*;






/**
 * This is the game engine which is a singleton class and 
 * controls the game flow
 * @author aliahadi, liqiyang, hengweng, saranejad
 *
 */
public class GameEngine {
	public static LogWindow log;

	Map map = Map.getMapInstance();
	int MAX_STATES = Map.MAX_NODE_COUNT;
	int MAX_PLAYERS = 7;// 0 ~ 6
	public static GameState state;//Game State
	//public List<Player> playerlist = new ArrayList<Player>();
	public int numberofplayers = 0;
	int currplayer = 0;
	public int technologylevel = 1;
	public static int round = 0;
	public boolean override = false;
	Color playercolors[] = {Color.lightGray, Color.MAGENTA, Color.cyan, Color.GREEN, Color.yellow};

	/**
	 * Game Engine Constructor, creates map and sets the game state to Edit mode
	 */
	public GameEngine() {
		state = GameState.EDIT;
	}
}
//
//	public void nextState() {
//		if(state == GameState.REENFORCE) {
//			if(!ReEnforce()) NextPlayer();
//			state = GameState.ATTACK;
//		}
//		else if(state == GameState.ATTACK) {
//			Move();
//			state = GameState.FORTIFY;
//		}
//		else if(state == GameState.FORTIFY) {
//			NextPlayer();
//			if(GameOver()) log.add("Game Over");
//			state = GameState.REENFORCE;
//		}
//	}
//
//	public GameState getCurrentState() {
//		return state;
//	}

	/**
	 * Adding a new player
	 */
//	public void AddANewPlayer() {
//		if(playerlist.size() < MAX_PLAYERS) {
//			playerlist.add(new Player(playerlist.size(), playercolors[playerlist.size()])) ;
//		}
//		numberofplayers++;
//	}
//	/**
//	 * Assigning States to Players, each player at has one capital
//	 */
//	public void AssignPlayers() {
//		log.add("Assigning Players");
//		Random random = new Random();
//		currplayer = random.nextInt(playerlist.size());
//		log.add(playerlist.get(currplayer).name + ", you play first!");
//		for(int i = 0; i < playerlist.size(); i++) {
//
//			int rnd = random.nextInt(MAX_STATES);
//			while(map.state[rnd].IsDeleted() || map.state[rnd].IsOwned()) 
//			{			
//				rnd = random.nextInt(MAX_STATES);
//			}
//			map.state[rnd].ownedByPlayer = playerlist.get(i);
//			playerlist.get(i).capital = map.state[rnd];
//			map.state[rnd].capital = true;
//		}
//		int it = 0;
//		for(int i = 0; i < MAX_STATES; i++ ) {
//			System.out.print("\n # " + map.state[i].name + "it = " + it);
//			if(map.state[i].ownedByPlayer == null) {
//				System.out.print(" yes!");
//				if(it >= playerlist.size()) it = 0;
//				map.state[i].ownedByPlayer = playerlist.get(it);
//				it++;
//			}
//			else System.out.print(" no:(");
//		}
//	}
//	/**
//	 * Re-Enforcing current player
//	 * @return false if player has lost the game
//	 */
//	public boolean ReEnforce() {
//		state = GameState.REENFORCE;
//		Player player = playerlist.get(currplayer);
//		log.add(player.name + ": Re-inforcing");
//		// Calculating Number of States Owned by Player "player"
//		int statesowned = 0;
//		for(int i = 0; i < MAX_STATES; i++) {
//			if(map.state[i].IsNotDeleted()) {
//				Player temp = map.state[i].ownedByPlayer;
//				if(temp != null)
//					if(temp == player) 
//					{
//						statesowned++;
//						if(override == false) {
//							Resources r = map.state[i].resource;
//							if(r == Resources.METL) player.metal = true;
//							else if(r == Resources.ENRG) player.energy = true;
//							else if(r == Resources.KLDG) player.knowledge = true;
//						}
//					}
//			}
//		}
//		player.statesowned = statesowned;
//		if(statesowned == 0) {
//			log.add(player.name + " has lost the game :(");
//			playerlist.remove(currplayer);
//			return false;
//		}
//		player.getResourceLevel();
//		if(override == true) PanelBar.simpanel.combo.setSelectedIndex(player.getResourceLevel()+1);
//		else PanelBar.simpanel.combo.setSelectedIndex(0);
//		String str1 = "Resource Level = " + player.getResourceLevel() + "\n Tech = " + technologylevel;
//		PanelBar.placearmypanel.label1.setText(str1);
//
//		// Calculating Number of Continents Owned by Player "player"
//		int continentsowned = 0;
//
//		for(int i = 0; i < map.continents.size(); i++) {
//
//			Continent tempcont = map.continents.get(i);//temporary continent
//			List<Integer> list = new ArrayList<Integer>();
//			list.clear();
//			for(int j = 0; j < MAX_STATES; j++) {
//				if(map.state[i].IsNotDeleted())
//					if(map.state[j].continent == tempcont) {
//						list.add(j);
//						//System.out.print(list + " / " + j + "; ");
//					}
//			}
//
//			// up to this point, we have created a list that contains the IDs 
//			// of the states that belong to temporary continent "tempcont"
//			// Let say QC has s01, s04, s07 so the list contains: 1, 4, 7
//
//
//			// now, we'll check if "player" owns all those states in "list"
//			boolean same = false;		
//			if(list.size() > 0) {
//				same = true;
//				for(int kk = 0; kk < list.size(); kk++) {
//					if(map.state[list.get(kk)].ownedByPlayer != player) {
//						same = false;
//						break;
//					}
//				}
//			}
//			else same = false;
//			// at this point, if "same" is true; that means, player owns continent "tempcont"
//			if(same == true) continentsowned++;
//		}
//		log.add(player.name + " owns " + statesowned + " States and " + continentsowned + " Continents");
//		log.add(player.name + "\'s Resource Level is " + player.getResourceLevel());
//
//		ArmyUnit armytype = ArmyFactory.getArmyUnit(technologylevel, player.getResourceLevel());
//		armytype.setSize(statesowned + 10 * continentsowned);
//		if(armytype != null) {
//			player.Produce(armytype);
//			String temp = armytype.getName();
//
//			String str2 = "";
//			str2 = "States = " + statesowned + " Continents = " + continentsowned + "\n PR = " 
//					+ statesowned + 10 * continentsowned + " " + armytype.getName();
//			PanelBar.placearmypanel.label2.setText(str2);
//			log.add(player.name + " has produced " + armytype.size() + " " + armytype.getName() + "(s)");
//			return true;
//
//		}
//		else return false; 
//	}
//	/**
//	 * Current Player can move their to neighboring states
//	 * If the adjacent state is owned by enemy, player decides to
//	 * attack based on the state powers
//	 */
//	public void Move() {
//		state = GameState.ATTACK;
//		Player player = playerlist.get(currplayer);
//
//		for(int i = 0; i < MAX_STATES; i++) {
//			State temp = map.state[i];
//			if(temp.IsNotDeleted() && temp.ownedByPlayer == player) {
//				for(int j = 0; j < MAX_STATES; j++) {
//					State temp2 = map.state[j];
//					if(temp2.IsNotDeleted() && map.link[i][j] == 1) {
//						Attack(temp, temp2);
//					}
//				}
//			}
//		}
//	}
//	/**
//	 * Attacking from source state to destination state
//	 * @param source source state
//	 * @param dest destination state
//	 */
//	public void Attack(State source, State dest) {
//		Player player = playerlist.get(currplayer);
//		if(source.ownedByPlayer != dest.ownedByPlayer) {
//			//Attack
//			if(dest.army.getPower() < source.army.getPower()) {
//				log.add(player.name + ": " + source.name + " captured " + dest.name);
//				dest.army.add(source.army);
//				source.army.clear();
//				dest.ownedByPlayer = player;
//			}
//		}
//		else {
//			//Move
//			String str = "";
//			Random rnd = new Random();
//			boolean temp = true;
//			int c = rnd.nextInt(9);
//			switch(c) {
//			case 0:
//				if(source.army.volunteer == 0) temp=false;
//				dest.army.volunteer += source.army.volunteer;
//				str = source.army.volunteer + " Volunteer(s)";
//				source.army.volunteer = 0;
//				break;
//			case 1:
//				if(source.army.aircraft == 0) temp=false;
//				dest.army.aircraft += source.army.aircraft;
//				str = source.army.aircraft + " Aircraft(s)";
//				source.army.aircraft = 0;
//				break;
//			case 2:
//				if(source.army.artillery == 0) temp=false;
//				dest.army.artillery += source.army.artillery;
//				str = source.army.artillery + " Artillery(s)";
//				source.army.artillery = 0;
//				break;
//			case 3:
//				if(source.army.conscript == 0) temp=false;
//				dest.army.conscript += source.army.conscript;
//				str = source.army.conscript + " Conscript(s)";
//				source.army.conscript = 0;
//				break;
//			case 4:
//				if(source.army.heavyprecisionartillery == 0) temp=false;
//				dest.army.heavyprecisionartillery += source.army.heavyprecisionartillery;
//				str = source.army.heavyprecisionartillery + " Heavy Precision Artillery(s)";
//				source.army.heavyprecisionartillery = 0;
//				break;
//			case 5:
//				if(source.army.mechanizedinfantry == 0) temp=false;
//				dest.army.mechanizedinfantry += source.army.mechanizedinfantry;
//				str = source.army.mechanizedinfantry + " Mechanized Infantry(s)";
//				source.army.mechanizedinfantry = 0;
//				break;
//			case 6:
//				if(source.army.nuclearmissile == 0) temp=false;
//				dest.army.nuclearmissile += source.army.nuclearmissile;
//				str = source.army.nuclearmissile + " Nuke(s)";
//				source.army.nuclearmissile = 0;
//				break;
//			case 7:
//				if(source.army.professionalsoldier == 0) temp=false;
//				dest.army.professionalsoldier += source.army.professionalsoldier;
//				str = source.army.professionalsoldier + " Professional Soldier(s)";
//				source.army.professionalsoldier = 0;
//				break;
//			case 8:
//				if(source.army.specialoperationssoldier == 0) temp=false;
//				dest.army.specialoperationssoldier += source.army.specialoperationssoldier;
//				str = source.army.specialoperationssoldier + " Special Operation Solider(s)";
//				source.army.specialoperationssoldier = 0;
//				break;
//			case 9:
//				if(source.army.tacticalmissile == 0) temp=false;
//				dest.army.tacticalmissile += source.army.tacticalmissile;
//				str = source.army.tacticalmissile + " Tactical Missile(s)";
//				source.army.tacticalmissile = 0;
//				break;
//			default:
//				str = "Nothing";
//			}
//			//dest.army += source.army;
//			//source.army.clear();
//			if(temp == true)
//				log.add(playerlist.get(currplayer).name + " has moved " + str + " from " + source.name +" to " + dest.name);
//			//else log.add(playerlist.get(currplayer).name + " didn't move their army");
//		}
//	}
//	/**
//	 * Determines if the game is over and indicates the winner
//	 * @return true if the game is over
//	 */
//	public boolean GameOver() {
//		if(playerlist.size() == 1) {
//			currplayer = 0;
//			log.add("Congradulations " + playerlist.get(0).name + ", you won !");
//			return true;
//		}
//		else return false;
//	}
//	/**
//	 * Selects the next player in the queue
//	 */
//	public void NextPlayer() {
//		currplayer++;
//		if(currplayer >= playerlist.size()) { 
//			currplayer = 0;
//			round++;
//			if(round == 5) {
//				technologylevel = 2;
//				log.add("Technology Level is now " + technologylevel);
//			}
//			else if(round == 10) {
//				technologylevel = 3;
//				log.add("Technology Level is now " + technologylevel);
//			}
//		}
//	}
//
//	/**
//	 * This is the game loop has been used for automatic simulation
//	 */
//	public void Loop() {
//		while(!GameOver()) {
//			if(!ReEnforce()) {NextPlayer(); continue;}
//			Move();
//			NextPlayer();
//		}
//
//		//		while(!gameIsOver()) {
//		//			if(!ReEnforce()) {NextPlayer(); continue;}
//		//			//liqiReenforce(getCurrentPlayer());
//		////			liqiAttack(getCurrentPlayer());
//		//			analyseAndBattle1(getCurrentPlayer());
//		//			liqiFortify(getCurrentPlayer());
//		//			NextPlayer();
//		//		}
//
//		System.out.print("\nGame Over");
//	}
//
//	/**
//	 * Returns the current player name
//	 * @return current player name (string)
//	 */
//	public String getCurrentPlayerName() {
//		return playerlist.get(currplayer).name;
//	}
//	public Player getCurrentPlayer() {
//		if(playerlist.size()>0)
//			return playerlist.get(currplayer);
//		else return null;
//	}
//	/**
//	 * Returns the current player color
//	 * @return player color
//	 */
//	public Color getCurrentPlayerColor() {
//		return playerlist.get(currplayer).color;
//	}
//
//	/**
//	 * Resets the game
//	 */
//	public void Reset() {
//		map.Reset();
//		playerlist.clear();
//	}
///**
// * In this method we save the simulation , first we save the map then we save all players and states and armys
// */
//	public void SaveSim()  {
//
//		PrintWriter outputstream = null;
//		try {
//			outputstream = new PrintWriter(new FileOutputStream("Sim.txt"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		//////////////////SAVE MAP///////////
//		// Saving Node Count
//					outputstream.println(map.nodeCount);
//					
//					// Saving Links
//					for (int i = 0; i < map.MAX_NODE_COUNT; i++) {
//						for (int j = 0; j < map.MAX_NODE_COUNT; j++) {
//							outputstream.print(map.link[i][j] + " ");
//						}
//					}
//					outputstream.println();
//					
//					// Save Continent Count
//					outputstream.println(map.continents.size());
//
//					// Saving Nodes
//					for (int i = 0; i < map.MAX_NODE_COUNT; i++) {
//						outputstream.print(map.state[i].name + " ");
//						outputstream.print(map.state[i].x + " " + map.state[i].y + " ");
//
//						if(map.state[i].continent != null) 
//							outputstream.print(map.state[i].continent.id +" ");
//						else outputstream.print(-1 + " ");// Throw Exception Here: State i is belong to an continents
//						if(map.state[i].resource != null) {
//							if(map.state[i].resource == Resources.NONE) 
//								outputstream.print(0 + " ");
//							else if(map.state[i].resource == Resources.METL) 
//								outputstream.print(1 + " ");
//							else if(map.state[i].resource == Resources.ENRG)
//								outputstream.print(2 + " ");
//							else
//								outputstream.print(3 + " ");
//						} else 
//							outputstream.print(-1 + " ");
//						
//						outputstream.print(map.state[i].capital+ " ");						
//						outputstream.print(map.state[i].deleted+ " ");
//						outputstream.println();
//					}
//		
//		////////////////////////////////////
//		
//		outputstream.println(state.ordinal());
//
//		 outputstream.println(playerlist.size());
//
//		 outputstream.println(currplayer);
//
//		 outputstream.println(technologylevel);
//
//		 for(int i = 0; i < MAX_STATES; i++) {
//			 outputstream.println(
//					 map.state[i].ownedByPlayer.id + " "
//					 + map.state[i].army.aircraft + " " 
//					 + map.state[i].army.artillery + " "
//					 + map.state[i].army.conscript + " "
//					 + map.state[i].army.heavyprecisionartillery + " "
//					 + map.state[i].army.mechanizedinfantry + " "
//					 + map.state[i].army.nuclearmissile + " "
//					 + map.state[i].army.professionalsoldier + " "
//					 + map.state[i].army.specialoperationssoldier + " "
//					 + map.state[i].army.tacticalmissile + " "
//					 + map.state[i].army.volunteer + " ");
//		 }
//		
//		
//		outputstream.close();
//
//	}
///**
// * In this method we Load the simulation from a file that we have of one saved simulation. 
// * first we load the map and then we load the players, states and armys.
// */
//	public void LoadSim() {
//		Scanner inputstream = null;
//		
//		try {
//			inputstream = new Scanner(new FileInputStream("Sim.txt"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			System.out.println("Problem opening files.!!!!!!!!!!!!!!");
//		}
//	
//		/////////////////////////////////////////////////////////////////////////
//		////////////LOAD MAP////////////////////////////////////////////////////
//		Reset();
//		// Loading Node Count
//		map.nodeCount = inputstream.nextInt();	
//		
//		// Loading Links
//		if (inputstream.hasNextLine()) {
//			for (int i = 0; i < map.MAX_NODE_COUNT; i++) {
//				for (int j = 0; j < map.MAX_NODE_COUNT; j++) {
//					map.link[i][j] = inputstream.nextInt();
//				}
//			}
//		}
//		inputstream.nextLine();
//
//		// Loading Continent Count and Creating Continents
//	//	map.continents.clear();
//		int count = inputstream.nextInt();
//
//	//	map.colorsetiterator = 0;
//		for(int i = 0; i < count; i++) map.AddContinent();
//		//inputStream.nextLine();
//
//		// Loading Nodes
//		//if (inputStream.hasNextLine()) {
//		for (int i = 0; i < map.MAX_NODE_COUNT; i++) {
//			map.state[i].name = inputstream.next();
//			map.state[i].x = inputstream.nextInt();
//			map.state[i].y = inputstream.nextInt();
//			
//			int id = inputstream.nextInt();
//			if(id != -1) map.state[i].continent = map.continents.get(id);
//			else map.state[i].continent = null;
//			
//			int resource = inputstream.nextInt();
//			if(resource == 1) map.state[i].resource = Resources.METL;
//			else if(resource == 2) map.state[i].resource = Resources.ENRG;
//			else if(resource == 3) map.state[i].resource = Resources.KLDG;
//			else map.state[i].resource = Resources.NONE;
//			
//			map.state[i].capital = inputstream.nextBoolean(); 
//			map.state[i].deleted = inputstream.nextBoolean();
//			inputstream.nextLine();
//		}
//		/////////////////////////////////////////////////////////////////////////
//		/////////////////////////////////////////////////////////////////////////
//	
//		switch(inputstream.nextInt()) {
//		 case 2: state = GameState.REENFORCE;
//		 break;
//		 case 3: state = GameState.ATTACK;
//		 break;
//		 case 4: state = GameState.FORTIFY;
//		 break;
//	 }
//
//	 playerlist.clear();
//
//	 int playerCount = inputstream.nextInt();
//
//	 for(int i = 0; i < playerCount ; i++) AddANewPlayer();
//	 currplayer = inputstream.nextInt();
//	 technologylevel = inputstream.nextInt();
//
//	 for(int i = 0; i < MAX_STATES; i++) {
//
//		 //int id = inputstream.nextInt();
//		 Player player = playerlist.get(inputstream.nextInt());
//		 map.state[i].ownedByPlayer = player;
//		 if(map.state[i].capital == true) player.capital = map.state[i];
//		 map.state[i].army.aircraft = inputstream.nextInt();
//		 map.state[i].army.artillery = inputstream.nextInt();
//		 map.state[i].army.conscript = inputstream.nextInt();
//		 map.state[i].army.heavyprecisionartillery = inputstream.nextInt();
//		 map.state[i].army.mechanizedinfantry = inputstream.nextInt();
//		 map.state[i].army.nuclearmissile = inputstream.nextInt();
//		 map.state[i].army.professionalsoldier = inputstream.nextInt();
//		 map.state[i].army.specialoperationssoldier = inputstream.nextInt();
//		 map.state[i].army.tacticalmissile = inputstream.nextInt();
//		 map.state[i].army.volunteer = inputstream.nextInt();
//	 }
//		inputstream.close();
//		
//		String str = getCurrentPlayerName() + ": " + getCurrentState().toString() ;
//		Color c = getCurrentPlayerColor();
//		PanelBar.placearmypanel.setText(str, c);
//	}
//
//	
//	/**
//	 * Production Stage
//	 * @param player Current player
//	 */
//	public void liqiReenforce(Player player){
//		State capital = new State();
//		int[] tempContinent = new int[Map.MAX_NODE_COUNT];
//		int t = 0;
//		boolean sameContinent = true;
//		for (int i = 0; i < Map.MAX_NODE_COUNT; i++) {
//			if (player == map.state[i].getOwnedByPlayer()&&
//					map.state[i].IsCapital()){
//				capital = map.state[i];
//				for (int j = 0; j < Map.MAX_NODE_COUNT; j++) {
//					if (player == map.state[j].getOwnedByPlayer()){
//						//player.productionStage(player,capital,map.state[j],1);
//					}
//				}
//				for (int j = 0;j < Map.MAX_NODE_COUNT; j++) {
//					for (int m = 0;m < Map.MAX_NODE_COUNT; m++) tempContinent[m] = Map.MAX_NODE_COUNT+1;
//					t = 0;
//					sameContinent = true;
//					for (int m = 0;m < Map.MAX_NODE_COUNT; m++) {
//						if (map.state[m].IsNotDeleted() && map.state[m].getContinent().id == j){
//							tempContinent[t] = m;
//							System.out.println("tempContinent[t]="+tempContinent[t]+j+map.state[m].getContinent().id);
//							t = t+1;
//						}
//					}
//					//System.out.println("tempContinent[t]=");
//					for (int m = 1;m < t; m++){
//						if (map.state[tempContinent[0]].getOwnedByPlayer() != map.state[tempContinent[m]].getOwnedByPlayer()){
//							sameContinent = false;
//							break;
//						}
//					}
//					if (t>0 && sameContinent == true && map.state[tempContinent[0]].getOwnedByPlayer() == player ) {
//						for (int m = 0;m < t; m++){
//							//player.productionStage(player, capital, map.state[tempContinent[m]],10);
//						}
//					}
//				}
//			}
//		}
//	}
//
//	public void liqiFortify(Player currentplayer){
//		for (int j = 0; j < Map.MAX_NODE_COUNT; j++) {
//			if (map.state[j].IsNotDeleted() 
//					&& map.state[j].getOwnedByPlayer() == currentplayer){
//				System.out.println("state[j]==="+j);
//				for (int k=j+1;k<Map.MAX_NODE_COUNT;k++){
//					if (map.state[k].IsNotDeleted() &&
//							map.state[j].getOwnedByPlayer() == map.state[k].getOwnedByPlayer()&&
//							Map.link[j][k] == 1){
//						System.out.println("state[k]==="+k);
//						for (int m=j+1;m<Map.MAX_NODE_COUNT;m++){
//							if (map.state[m].IsNotDeleted() 
//									&&	map.state[k].getOwnedByPlayer() != map.state[m].getOwnedByPlayer()
//									&&	Map.link[k][m] == 1 
//									&&	Map.link[j][m] == 0
//									//&&  state[j].getPower()+state[k].getPower()>state[m].getPower()
//									){
//								System.out.println("movement");
//								currentplayer.movementStage(map.state[j], map.state[k], map.state[m]);
//							}								
//						}
//					}						
//				}
//			}
//		}
//	}
//
//	/**
//	 * For a player, it will continue to attack if it find there exists a state 
//	 * that the power less than itself.
//	 * @param player
//	 */
//	public void liqiAttack(Player player){
//		int difference = 0;
//
//		do {
//			difference = analyseStage(player);
//			System.out.println("difference=="+difference);
//		} 
//		while (difference > 0);
//	}
//
//	/**
//	 * For a player, we compare the power of each state belong to this player and 
//	 * the power of each state connected with it and belong to the other player
//	 * if difference less than zero, we don't attack
//	 * else we will attack the state which the difference is maximum.  
//	 * @param player Current player
//	 */
//	public int analyseStage(Player player){
//		System.out.println("begin analyse");
//		int difference = 0;
//		int attacking = 0;
//		int attacked = 0;
//		for (int i = 0; i < Map.MAX_NODE_COUNT; i++) {
//
//			if (map.state[i].ownedByPlayer == player){
//
//				for (int j = 0; j < Map.MAX_NODE_COUNT; j++) {
//					if (Map.link[i][j]==1 
//							&& map.state[j].ownedByPlayer != player
//							&& map.state[i].army.getPower() - map.state[j].army.getPower()> difference){
//						difference = map.state[i].army.getPower() - map.state[j].army.getPower();
//						attacking = i;
//						attacked = j;
//					}
//				}
//			}
//		}
//		if (difference > 0){
//			//			attackStage(player, state[attacking],state[attacked]);
//			//			System.out.println("state[attacking]---"+attacking);
//			//			System.out.println("state[attacked]---"+attacked);
//			//			System.out.println("difference---"+difference);
//			//prepare for multi thread
//			int totalPower = 0;
//			for (int i = 0; i < Map.MAX_NODE_COUNT; i++){
//				if (Map.link[i][attacked]==1 
//						&& map.state[i].ownedByPlayer == player){
//					totalPower = totalPower + map.state[i].army.getPower();
//				}
//			}
//			for (int i = 0; i < Map.MAX_NODE_COUNT; i++){
//				if (Map.link[i][attacked]==1 
//						&& map.state[i].ownedByPlayer == player){
//					AttackThreadBuilder attackThreadBuilder = new AttackThreadBuilder(
//							player, 
//							map.state[i], map.state[attacked],
//							map.state[i].army.getPower(),
//							map.state[attacked].army.getPower()*map.state[i].army.getPower()/totalPower);
//					attackThreadBuilder.build();
//					Thread thread = new Thread(attackThreadBuilder.getAttackStageThread());
//					thread.start();
//				}
//			}	
//		}
//		return difference;	
//	}
//
//	/**
//	 * 
//	 * This function is useless!!!
//	 * @return true test is over
//	 * 	       false test is not over
//	 */
//	public boolean gameIsOver(){
//		boolean gameOver = true;
//		//int first = 0;
//		Player first = new Player();
//		for (int i = 0; i < Map.MAX_NODE_COUNT; i++) {
//			if (map.state[i].IsNotDeleted()) {
//				first = map.state[i].getOwnedByPlayer();
//				break;
//			}
//		}
//		for (int i = 0; i < Map.MAX_NODE_COUNT; i++) {
//			if (map.state[i].IsNotDeleted() && first != map.state[i].getOwnedByPlayer()){
//				gameOver = false;
//				break;
//			}
//		}
//		System.out.println("Game is Over---"+gameOver);
//		return gameOver;
//	}
//
//	public void analyseAndBattle1(Player player){
//		int difference = 0;
//		do {
//			difference = analyseStage1(player);
//			//			System.out.println("difference=="+difference);
//		} 
//		while (difference > 0);
//
//	}
//	public int analyseStage1(Player player){
//		System.out.println("begin analyse");
//		int difference = 0;
//		int attacking = 0;
//		int attacked = 0;
//		for (int i = 0; i < Map.MAX_NODE_COUNT; i++) {
//			if (map.state[i].IsNotDeleted()){
//				System.out.println("i=="+i+";;map.state[i].army.getPower()=="+map.state[i].army.getPower()
//						+";;map.state[i].ownedByPlayer.name"+map.state[i].ownedByPlayer.name);
//			}
//		}
//
//		for (int i = 0; i < Map.MAX_NODE_COUNT; i++) {
//
//			if (map.state[i].ownedByPlayer == player&&map.state[i].IsNotDeleted()){
//
//				for (int j = 0; j < Map.MAX_NODE_COUNT; j++) {
//					if (Map.link[i][j]==1 
//							&& map.state[j].ownedByPlayer != player
//							&& map.state[i].army.getPower()-map.state[j].army.getPower()> difference){
//						difference = map.state[i].army.getPower()-map.state[j].army.getPower();
//						attacking = i;
//						attacked = j;
//					}
//				}
//			}
//		}
//
//		if (difference > 0){
//			System.out.println("difference="+difference);	
//			System.out.println("Attacking"+attacking+ " ; " +attacked);
//			attackStage(player, map.state[attacking], map.state[attacked]);
//		}
//		return difference;	
//	}
//
//	//	public void attackStageMultiThread(Player player, 
//	//									State stateAttacking,
//	//									State stateAttacked, 
//	//									int stateAttackingPower,
//	//									int stateAttackedPower){
//	//		int i = stateAttackingPower;
//	//		int j = stateAttackedPower;
//	//		while (i>0 && j>0){
//	//			Random random = new Random();
//	//			int k = random.nextInt(2);
//	//			if (k == 0) 
//	//				i = i - 1;
//	//			else j = j - 1;
//	//		}
//	//		if (i == 0){
//	//			battleResultSet(stateAttacking,stateAttacked,j);
//	//		}	
//	//		else {
//	//			battleResultSet(stateAttacked,stateAttacking,i);
//	//		}	
//
//	//		int i = stateAttacking.army.getPower()-stateAttacked.army.getPower();
//	//		int j = i-i/2;
//	//		stateAttacking.army.setPower(i/2);
//	//		stateAttacked.army.setPower(j);
//	//		stateAttacked.setOwnedByPlayer(stateAttacking.getOwnedByPlayer());
//	//	}
//
//	public void battleResultSet(State stateAttacking,State stateAttacked,int j){
//
//		stateAttacking.setOwnedByPlayer(stateAttacked.getOwnedByPlayer());
//		stateAttacking.army.setVolunteer(0);
//		stateAttacking.army.setAircraft(0);
//		stateAttacking.army.setArtillery(0);
//		stateAttacking.army.setConscript(0);
//		stateAttacking.army.setHeavyprecisionartillery(0);
//		stateAttacking.army.setMechanizedinfantry(0);
//		stateAttacking.army.setNuclearmissile(0);
//		stateAttacking.army.setPower(0);
//		stateAttacking.army.setProfessionalsoldier(0);
//		stateAttacking.army.setSpecialoperationssoldier(0);
//		stateAttacking.army.setTacticalmissile(0);
//		int a1000 = (int)Math.floor(j/1000);
//		stateAttacked.army.setNuclearmissile(a1000);
//		int a500 = (int)Math.floor((j - a1000*1000)/500);
//		stateAttacked.army.setTacticalmissile(a500);
//		int a200 = (int)Math.floor((j - a1000*1000-a500*500)/200);
//		stateAttacked.army.setHeavyprecisionartillery(a200);
//		int a100 = (int)Math.floor((j - a1000*1000-a500*500-a200*200)/100);
//		stateAttacked.army.setAircraft(a100);
//		int a50 = (int)Math.floor((j - a1000*1000-a500*500-a200*200-a100*100)/50);
//		stateAttacked.army.setMechanizedinfantry(a50);
//		int a20 = (int)Math.floor((j - a1000*1000-a500*500-a200*200-a100*100-a50*50)/20);
//		stateAttacked.army.setArtillery(a20);
//		int a10 = (int)Math.floor((j - a1000*1000-a500*500-a200*200-a100*100-a50*50-a20*20)/10);
//		stateAttacked.army.setSpecialoperationssoldier(a10);
//		int a5 = (int)Math.floor((j - a1000*1000-a500*500-a200*200-a100*100-a50*50-a20*20-a10*10)/5);
//		stateAttacked.army.setProfessionalsoldier(a5);
//		int a2 = (int)Math.floor((j - a1000*1000-a500*500-a200*200-a100*100-a50*50-a20*20-a10*10-a5*5)/2);
//		stateAttacked.army.setConscript(a2);
//		int a1 = (int)Math.floor(j - a1000*1000-a500*500-a200*200-a100*100-a50*50-a20*20-a10*10-a5*5-a2*2);
//		stateAttacked.army.setVolunteer(a1);
//		stateAttacked.army.setPower(j);
//	}
//
//	/**
//	 * Attack Stage
//	 * It just is a sample.We will do it during the Build 3
//	 * @param player Current Stage
//	 * @param stateAttacking Attacking
//	 * @param stateAttacked Attacked
//	 */
//	public void attackStage(Player player, State stateAttacking,State stateAttacked){
//		int i = stateAttacking.army.getPower();
//		int j = stateAttacked.army.getPower();
//		while (i>0 && j>0){
//			Random random = new Random();
//			int k = random.nextInt(100);
//			int m = (int) stateAttacking.army.getPower()*100/(stateAttacking.army.getPower()+stateAttacked.army.getPower()) ;
//			if (k <m ) 
//				j = j - 1;
//			else i = i - 1;
//		}
//		if (i == 0){
//			battleResultSet(stateAttacking,stateAttacked,j);
//		}	
//		else {
//			battleResultSet(stateAttacked,stateAttacking,i);
//		}	
//	}
//
//}
