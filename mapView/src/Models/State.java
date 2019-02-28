package Models;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import View.*;
/**
 * 
 * Defines each node of the graph
 * 
 */
public class State{
	public static final int NODE_DIAMETER = 25;

	public String name;
	//public Resources resource;
	//public Player ownedByPlayer;
//	public Army army;
//	public Army getArmy() {
//		return army;
//	}
//	public void setArmy(Army army) {
//		this.army = army;
//	}
//	public Continent getContinent() {
//		return continent;
//	}
//	public void setContinent(Continent continent) {
//		this.continent = continent;
//	}
//	public Continent continent;
	public int x;
	public int y; // Position in the map
	public boolean deleted; //To indicate if a node has been deleted
	public boolean capital; // Indicates if a node has is capital, only the first node can be capital??
	/**
	 * Constructor: initializes the newly created state
	 */
	State(){
		x = y = 0;
		//resource = null;
//		ownedByPlayer = null;
//		continent = null;
		deleted = true;
		capital = false;
		//army = new Army();
		name = "untitled";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public Resources getResource() {
//		return resource;
//	}
//	public void setResource(Resources resource) {
//		this.resource = resource;
//	}
//	public Player getOwnedByPlayer() {
//		return ownedByPlayer;
//	}
//	public void setOwnedByPlayer(Player ownedByPlayer) {
//		this.ownedByPlayer = ownedByPlayer;
//	}
	/**
	 * Constructor overload
	 * @param x coordinates on the screen
	 * @param y coordinates in the screen
	 * @param resource type of resource
	 * @param ownedByPlayer owner
	 * @param name name of the state
	 */
	State(int x, int y,  String name) {
		this.x = x;
		this.y = y;
//		this.resource = resource;
//		this.ownedByPlayer = ownedByPlayer;
		this.name = name;
	}
	/**
	 * Sets the position of the node
	 * @param posx x coord
	 * @param posy y coord
	 */
	public void SetPosition(int posx, int posy) {
		x = posx;
		y = posy;
	}
	/**
	 * Determines if the mouse is pointing at the state
	 * @param x current mouse x
	 * @param y current mouse y
	 * @return returns true if the mouse is over this object
	 */
	public boolean MouseOver(int x, int y) {
		if(deleted == false) {
			int r = NODE_DIAMETER;
			if(x >= this.x-r/2 && x <= this.x+r/2 && y>=this.y-r/2 && y <= this.y+r/2) return true;
			else return false;
		}
		else return false;
	}
	public boolean IsNotDeleted() {
		return !deleted;
	}
	public boolean IsDeleted() {
		return deleted;
	}
	public boolean IsCapital() {
		return capital;
	}
	public boolean IsNotCapital() {
		return !capital;
	}
//	public boolean IsNotOwned() {
//		if(ownedByPlayer == null) return true;
//		else return false;
//	}
//	public boolean IsOwned() {
//		if(ownedByPlayer == null) return false;
//		else return true;
//	}
	/**
	 * Drawing the object
	 * @param g now we use Graphics2D, yeepee!
	 */
	public void Draw(Graphics2D g) {
		//		g = (Graphics2D) g;
		//		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int d2 = 4;
		if(!deleted) {


			//if(continent != null) g.setColor(continent.color);
			 g.setColor(Color.black);
			if(capital) g.fillRoundRect(x - NODE_DIAMETER/2 - d2/2 , y - NODE_DIAMETER/2 - d2/2, NODE_DIAMETER+d2, NODE_DIAMETER+d2, 10, 10);
			else
				g.fillOval(x - NODE_DIAMETER/2 - d2/2, y - NODE_DIAMETER/2 -d2/2, NODE_DIAMETER + d2, NODE_DIAMETER + d2);

			//if(ownedByPlayer != null) g.setColor(ownedByPlayer.color);
			 g.setColor(Theme.color);
			if(capital) g.fillRoundRect(x - NODE_DIAMETER/2  , y - NODE_DIAMETER/2 , NODE_DIAMETER, NODE_DIAMETER, 10, 10);
			else
				g.fillOval(x - NODE_DIAMETER/2, y - NODE_DIAMETER/2, NODE_DIAMETER, NODE_DIAMETER);
			
				String strI = "" ;
				g.setColor(Color.BLACK);
				Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 8);
				g.setFont(font);
				g.drawString(strI, x - 10, y);
				//int power = army.getPower();
				//String strII = "" + power;
				//g.drawString(strII, x-5, y+12);
			}
					

		}

	
}