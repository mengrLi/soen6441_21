package Models;


import java.awt.*;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import View.PanelBar;

//import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import Models.*;

/**
 * We have used Adjacency Matrix implementation for the graph that is: a array
 * of States and a matrix to save the graph
 * 
 * @author aliahadi,hengwang, saranejad, liqiyang
 * 
 */
public class Map extends Observable {
	private Color colorset[] = {Color.white,  Color.gray, Color.green, Color.RED, Color.yellow, Color.blue};
	private String continentset[] = {"North America", "South America", "Africa", "Europe", "Asia", "Australia"};
	int colorsetiterator = 0;
	int contisetiterator = 0;
	public static final int MAX_NODE_COUNT = 30; // Maximum number of nodes
	public static final int MAX_CONTINENTS = 6;
	public boolean drawConnectingLine = false; // To draw the line when
	public GameState gamestate = GameEngine.state;
	// right-click and dragged
	public int nodeCount; // Number of nodes alive
	public static int[][] link = new int[MAX_NODE_COUNT][MAX_NODE_COUNT]; // Adjacency
	// Matrix
	public State[] state = new State[MAX_NODE_COUNT];// List (Array) of States
	// Change to List later on
	//public List<State> state = new ArrayList<State>();
	//public List<Continent> continents = new ArrayList<Continent>();	

	private int testObsNum = 0; // For Observer Pattern Unit Test
	private String mapName = "";
	
	private static Map mapInstance = null;
	private static int instanceNum = 0;
	
	public static String messageLoad = "";
	
	public static Map getMapInstance() {
		if (mapInstance == null) {
			mapInstance = new Map();
			instanceNum++;
		}
		return mapInstance;
	}
	
	/**
	 * This function prints a current Log of the map on the console
	 * 
	 */
	public void Log() {
		System.out.print("\n\n" + nodeCount + "\n");
		for (int i = 0; i < MAX_NODE_COUNT; i++) {
			for (int j = 0; j < MAX_NODE_COUNT; j++) {
				System.out.print(link[i][j] + ", ");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Constructor, creates and initializes the map
	 */
	public Map() {
		// Creating Map
		nodeCount = 0;
		for (int i = 0; i < MAX_NODE_COUNT; i++) {
			state[i] = new State();// Creating Node Objects
			for (int j = 0; j < MAX_NODE_COUNT; j++)
				link[i][j] = 9; // Initializing Links
		}

	
}

//	/**
//	 * Resets the Map
//	 */
//	public void Reset() {
//		colorsetiterator = 0;
//		contisetiterator = 0;
//		nodeCount = 0;
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//			state[i].deleted = true;
//			state[i].continent = null;
//			state[i].ownedByPlayer = null;
//			state[i].capital = false;
//			for (int j = 0; j < MAX_NODE_COUNT; j++) {
//				link[i][j] = 9;
//
//			}
//		}
//		continents.clear();
//		setChanged();
//		notifyObservers();
//		Log();
//	}
//
	/**
	 * Adds Node
	 * 
	 * @param x
	 *            Coordinates of the new node
	 * @param y
	 *            Coordinates of the new node
	 */
	public void AddNode(int x, int y) {
		if (nodeCount < MAX_NODE_COUNT) {
			// finding a free slot
			int freeIndex = 0;
			while (link[freeIndex][freeIndex] != 9)
				freeIndex++;
			if (freeIndex < MAX_NODE_COUNT) {
				state[freeIndex].x = x;
				state[freeIndex].y = y;
				state[freeIndex].deleted = false;
				state[freeIndex].name = "s" + nodeCount;
				Random random = new Random();
//				switch(random.nextInt(4)) {
//				case 0:
//					state[freeIndex].resource = Resources.NONE;
//					break;
//				case 1:
//					state[freeIndex].resource = Resources.METL;
//					break;
//				case 2:
//					state[freeIndex].resource = Resources.ENRG;
//					break;
//				case 3:
//					state[freeIndex].resource = Resources.KLDG;
//					break;
//				}

				for (int i = 0; i < MAX_NODE_COUNT; i++) {
					link[freeIndex][i] = 0;
					link[i][freeIndex] = 0;
				}
				nodeCount++;
			}
		}
		Log();
		setChanged();
		notifyObservers();
	
	}
//	/**
//	 * Adding a new continent to the map, Maximum of 5 continents can be added to the map
//	 * @return false if current number of continents is 5
//	 */
//	public boolean AddContinent() {
//		if(continents.size() < MAX_CONTINENTS) {
//			String str = continentset[contisetiterator++];
//			continents.add(new Continent(continents.size(), str, colorset[colorsetiterator++]));
//			if(colorsetiterator > MAX_CONTINENTS - 1) {colorsetiterator = 0;contisetiterator = 0;}
//			setChanged();
//			notifyObservers();
//			return true;
//		}
//		else {
//			return false;
//		}
//		
//	}
//
	/**
	 * Deletes a previously created node
	 * 
	 * @param index
	 *            the index of the node to be deleted
	 */
	public void DeleteNode(int index) {
		if (nodeCount >= 0 && index > -1) {
			state[index].deleted = true;
			for (int i = 0; i < MAX_NODE_COUNT; i++) {
				link[i][index] = 9;
				link[index][i] = 9;
			}
			nodeCount--;
		}
		Log();
		setChanged();
		notifyObservers();
	
	}
//
//	/**
//	 * Moving a node to a new location
//	 * 
//	 * @param index
//	 *            index of the node to be moved
//	 * @param x
//	 *            new coordinates
//	 * @param y
//	 *            new coordinates
//	 */
//	public void MoveNode(int index, int x, int y) {
//		if (index != -1) {
//			state[index].x = x;
//			state[index].y = y;
//		}
//		setChanged();
//		notifyObservers();
//	}
//
//	/**
//	 * Sets ownedByPlayer
//	 * 
//	 * @param index
//	 *            index of the node
//	 * @param player
//	 *            player
//	 */
//	public void SetPlayer(int index, Player player) {
//		if (index != -1)
//			state[index].ownedByPlayer = player;
//		setChanged();
//		notifyObservers();
//	}
//	/**
//	 * Sets the continent belongings of a certain state
//	 * @param index index of the state
//	 * @param continent continent to be assigned
//	 */
//	public void SetContinent(int index, int continent) {
//		if (index != -1 && continent != -1)
//		{
//			state[index].continent = continents.get(continent);
//		}
//		setChanged();
//		notifyObservers();
//	}
//	/**
//	 * Makes a certain node to be capital state
//	 * @param index index of the state we want to be capital state
//	 */
//	public void SetCapital(int index) {
//		if (index != -1)
//			state[index].capital = !state[index].capital;
//		setChanged();
//		notifyObservers();
//	}
//
//	/**
//	 * Draws the links between nodes
//	 * 
//	 * @param g
//	 *            Graphics
//	 */
//	public void DrawLinks(Graphics g) {
//		g.setColor(Color.BLACK);
//		Color color = Color.BLACK;
//
//		for (int j = 0; j < MAX_NODE_COUNT; j++) {
//			for (int i = 0; i < MAX_NODE_COUNT; i++) {
//				g.setColor(color);
//				if (link[i][j] == 1)
//					g.drawLine(state[i].x, state[i].y, state[j].x, state[j].y);
//			}
//		}
//	}
//
//	/**
//	 * Draws the line when right-click-dragged
//	 * 
//	 * @param g
//	 *            Graphics
//	 * @param selectedIndex
//	 *            Index of the selected node
//	 * @param currMouseX
//	 *            Current mouse coordinates
//	 * @param currMouseY
//	 *            Current mouse coordinates
//	 */
//	public void DrawConnectingLine(Graphics g, int selectedIndex,
//			int currMouseX, int currMouseY, int mode) {
//
//		int x1 = state[selectedIndex].x;
//		int y1 = state[selectedIndex].y;
//		int x2 = currMouseX;
//		int y2 = currMouseY;
//
//		int x = (x1+x2)/2;
//		int y = (y1+y2)/2;
//
//		double theta = Math.atan2(y2-y1, x2-x1);
//
//		Graphics2D g2 = (Graphics2D) g; 
//		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		g2.translate(x, y);
//		g2.rotate(theta);
//		if(mode == 0) {
//			g2.setColor(Color.black);
//			g2.drawString("Connect", 0, 0);
//		}
//		else if (mode == 1)
//			g2.drawString("Move", 0, 0);
//		else if(mode == 2) {
//			g2.setColor(Color.MAGENTA);
//			g2.drawString("Attack", 0, 0);
//		}
//		g2.rotate(-theta);
//		g2.translate(-x, -y);
//
//		g2.drawLine(state[selectedIndex].x, state[selectedIndex].y, currMouseX,
//				currMouseY);
//		if(mode == 2) {
//			int radius = (int)Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
//			g2.drawArc(x1 - radius , y1 - radius, radius*2, radius*2, 0, 360);
//		}
//	}
//
	/**
	 * Draws nodes
	 * 
	 * @param g
	 *            Graphics
	 */
	public void DrawNodes(Graphics2D g) {
		for (int i = 0; i < MAX_NODE_COUNT; i++) {
			if (state[i].IsNotDeleted())
				state[i].Draw(g);
				setChanged();
				notifyObservers();
		}
	}
	}
//
//	/**
//	 * Connects nodes, by updating the adjacency matrix
//	 * 
//	 * @param firstNode
//	 *            First Node
//	 * @param secondNode
//	 *            Target Node
//	 */
//	public void ConnectNodes(int firstNode, int secondNode) {
//		if (firstNode != secondNode && firstNode != -1 && secondNode != -1) {
//			link[firstNode][secondNode] = 1;
//			link[secondNode][firstNode] = 1;
//			Log();
//		}
//	}
//
//	/**
//	 * Returns the index of the node. if mouse is node over any node, It returns
//	 * -1
//	 * 
//	 * @param x
//	 *            Current mouse coords
//	 * @param y
//	 *            Current mouse coords
//	 * @return Index of the node
//	 */
//	public int GetMouseOverIndex(int x, int y) {
//		for (int i = 0; i < MAX_NODE_COUNT; i++)
//			if (state[i].MouseOver(x, y)) {
//				return i;
//			}
//		return -1;
//	}
//
//	/**
//	 * mask if State has been visited
//	 */
//	public static int[] hasVisit = new int[MAX_NODE_COUNT];
//
//	/**
//	 * Verify Map
//	 */
//
//	public boolean CheckValidity() {
//
//		/* Test Condition 0 */
//		/* The default map is invalid. */
//		/* The default map means that all link[i][j] are 9 */
//
//		boolean defaultMap = true;
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//			for (int j = 0; j < MAX_NODE_COUNT; j++) {
//				if (link[i][j] != 9) {
//					defaultMap = false;
//					break;
//				}
//			}
//			if (defaultMap == false)
//				break;
//
//		}
//		if (defaultMap == true) {
//			System.out.println("False Condition 0");
//			return false;
//		}
//
//		/*
//		 * Test Condition 1 The map includes isolated state(node) is invalid.
//		 * And the map is invalid if it only includes one state.
//		 */
//		boolean isolatedNode = true;
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//			for (int j = 0; j < MAX_NODE_COUNT; j++) {
//				// if (link[i][j] != 0) {
//				if (link[i][j] == 1 || state[i].deleted == true) {
//					isolatedNode = false;
//					break;
//				}
//			}
//			if (isolatedNode == true) {
//				System.out.println("False Condition 1: Isolated Node");
//				return false;
//			} else
//				isolatedNode = true;
//		}
//
//		/*
//		 * Test Condition 2 If one continent includes more than one state And
//		 * one of those is isolated from other states in the same continent. The
//		 * map is invalid.
//		 */
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//			boolean isolatedNodeInContinent = true;
//			boolean invalidNode = false;
//			for (int j = 0; j < MAX_NODE_COUNT; j++) {
//				// if (link[i][j] == 9){
//				if (state[i].deleted == true) {
//					invalidNode = true;
//				}
//
//				if (link[i][j] == 1 && state[i].continent == state[j].continent) {
//					isolatedNodeInContinent = false;
//					break;
//				}
//
//			}
//			if (invalidNode == false && isolatedNodeInContinent == true) {
//				int sameContinentNuber = 0;
//				for (int j = 0; j < MAX_NODE_COUNT; j++) {
//					if (state[i].continent == state[j].continent)
//						sameContinentNuber++;
//				}
//				if (sameContinentNuber > 1) {
//					System.out
//					.println("False Condition 2: Continent Not Connected");
//					return false;
//				}
//			}
//
//		}
//
//		/*
//		 * Test Condition 3 Any continents can be reached by any other
//		 * continents Otherwise, the map is invalid.
//		 */
//
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//			if (state[i].deleted == true)
//				hasVisit[i] = 9;
//			else
//				hasVisit[i] = 0;
//		}
//
//		dfsMap(0, link);
//
//		// for (int i = 0; i < validStateNumber; i++) {
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//			if (hasVisit[i] == 0) {
//				System.out.println("False Condition 3: Separated graphs");
//				return false;
//			}
//
//		}
//
//		/*
//		 * Test Condition 4 All states in a continent can be reached by the
//		 * states from the same continent. Otherwise, the map is invalid.
//		 */
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//			if (state[i].deleted == true)
//				hasVisit[i] = 9;
//			else
//				hasVisit[i] = 0;
//		}
//		// dfsContinent(0,state,link);
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//
//			if (hasVisit[i] == 0) {
//				Boolean hasVisitedContitent = false;
//
//				for (int j = 0; j < MAX_NODE_COUNT; j++) {
//					if (state[i].continent == state[j].continent && i != j
//							&& hasVisit[j] == 1) {
//						hasVisitedContitent = true;
//						break;
//					}
//				}
//				if (hasVisitedContitent == false) {
//					// System.out.println("dfsContinent(i)--"+i);
//					dfsContinent(i, state, link);
//				}
//
//			}
//
//		}
//
//		for (int i = 0; i < MAX_NODE_COUNT; i++) {
//			if (hasVisit[i] == 0) {
//				System.out.println("False Condition 4");
//				return false;
//			}
//		}
//		// Each Continent Should have only one capital
//		System.out.println("Success");
//		return true;
//	}
//
//	/**
//	 * Depth-First Search
//	 * @param i shall not be documented
//	 * @param link shall not be documented
//	 */
//	public void dfsMap(int i, int[][] link) {
//		if (hasVisit[i] == 0) {
//			hasVisit[i] = 1;
//			for (int j = 0; j < MAX_NODE_COUNT; j++) {
//				// System.out.println("ININIAL i= "+i+"  j= "+j);
//				if (link[i][j] == 1) {
//					if (hasVisit[j] == 0) {
//						// System.out.println("dfsMap -- i= "+i+"  j= "+j);
//						dfsMap(j, link);
//					}
//				}
//			}
//		}
//	}
//
//	public void dfsContinent(int i, State[] state, int[][] link) {
//		if (hasVisit[i] == 0) {
//			hasVisit[i] = 1;
//			for (int j = 0; j < MAX_NODE_COUNT; j++) {
//				// System.out.println("ININIAL i= "+i+"  j= "+j);
//				if (link[i][j] == 1) {
//					if (hasVisit[j] == 0
//							&& state[i].continent == state[j].continent) {
//						// System.out.println("dfsContinent -- i= "+i+"  j= "+j);
//						dfsContinent(j, state, link);
//					}
//				}
//			}
//		}
//	}
//
//	/**
//	 * Saving the map into a file in the same folder as the project folder
//	 */
//	public void Save_As() {
//
//		String fileName;
//		PrintWriter outputStream = null;
//		JFrame frm = new JFrame("Save As");
//		frm.setAlwaysOnTop(true);
//		JPanel p = new JPanel();
//		JFileChooser fc = new JFileChooser();
//		Container c = frm.getContentPane();
////		FileNameExtensionFilter filter = new FileNameExtensionFilter(
////				"Txt file");
////		fc.setFileFilter(filter);
//		File f;
//		int flag = 0;
//		c.setLayout(new FlowLayout());
//		c.add(p);
//		fc.setDialogTitle("Save File");
//		
//		try {
//			File filedir=new File("/Users/Sara Nejad/workspace/TuesdayNightProject-alshRisk/");
//			fc.setCurrentDirectory(filedir);
//			flag = fc.showSaveDialog(frm);
//		} catch (HeadlessException he) {
//			System.out.println("Save File Dialog ERROR!");
//		}
//		if (flag == JFileChooser.APPROVE_OPTION) {
//			f = fc.getSelectedFile();
//			fileName = f.getAbsolutePath();
//			try {
//				outputStream = new PrintWriter(new FileOutputStream(fileName));
//			} catch (FileNotFoundException e) {
//				System.out.println("Error Opening the file Test1.txt, true");
//				// System.exit(0);
//			}
//			// Saving Node Count
//			outputStream.println(nodeCount);
//			
//			// Saving Links
//			for (int i = 0; i < MAX_NODE_COUNT; i++) {
//				for (int j = 0; j < MAX_NODE_COUNT; j++) {
//					outputStream.print(link[i][j] + " ");
//				}
//			}
//			outputStream.println();
//			
//			// Save Continent Count
//			outputStream.println(continents.size());
//
//			// Saving Nodes
//			for (int i = 0; i < MAX_NODE_COUNT; i++) {
//				outputStream.print(state[i].name + " ");
//				outputStream.print(state[i].x + " " + state[i].y + " ");
//
//				if(state[i].continent != null) outputStream.print(state[i].continent.id +" ");
//				else outputStream.print(-1 + " ");// Throw Exception Here: State i is belong to an continents
//				if(state[i].resource != null) {
//					if(state[i].resource == Resources.NONE) 
//						outputStream.print(0 + " ");
//					else if(state[i].resource == Resources.METL) 
//						outputStream.print(1 + " ");
//					else if(state[i].resource == Resources.ENRG)
//						outputStream.print(2 + " ");
//					else
//						outputStream.print(3 + " ");
//				} else 
//					outputStream.print(-1 + " ");
//				
//				outputStream.print(state[i].deleted);
//				outputStream.println();
//			}
//			outputStream.close();
//		}
//	}
//
//	/**
//	 * Loading the adjacency matrix, node properties from the file
//	 */
//
//	
//	public void SaveAs() {
//
//		String fileName;
//		PrintWriter outputStream = null;
//		JFrame frm = new JFrame("Save As");
//		frm.setAlwaysOnTop(true);
//		JPanel p = new JPanel();
//		JFileChooser fc = new JFileChooser();
//		Container c = frm.getContentPane();
////		FileNameExtensionFilter filter = new FileNameExtensionFilter(
////				"Txt file");
////		fc.setFileFilter(filter);
//		File f;
//		int flag = 0;
//		c.setLayout(new FlowLayout());
//		c.add(p);
//		fc.setDialogTitle("Save File");
//		
//		try {
//			File filedir=new File("/Users/Sara Nejad/workspace/TuesdayNightProject-alshRisk/");
//			fc.setCurrentDirectory(filedir);
//			flag = fc.showSaveDialog(frm);
//		} catch (HeadlessException he) {
//			System.out.println("Save File Dialog ERROR!");
//		}
//		if (flag == JFileChooser.APPROVE_OPTION) {
//			f = fc.getSelectedFile();
//			fileName = f.getAbsolutePath();
//			try {
//				outputStream = new PrintWriter(new FileOutputStream(fileName));
//			} catch (FileNotFoundException e) {
//				System.out.println("Error Opening the file Test1.txt, true");
//				// System.exit(0);
//			}
//			// Saving Node Count
//			outputStream.println(nodeCount);
//			
//			// Saving Links
//			for (int i = 0; i < MAX_NODE_COUNT; i++) {
//				for (int j = 0; j < MAX_NODE_COUNT; j++) {
//					outputStream.print(link[i][j] + " ");
//				}
//			}
//			outputStream.println();
//			
//			// Save Continent Count
//			outputStream.println(continents.size());
//
//			// Saving Nodes
//			for (int i = 0; i < MAX_NODE_COUNT; i++) {
//				outputStream.print(state[i].name + " ");
//				outputStream.print(state[i].x + " " + state[i].y + " ");
//
//				if(state[i].continent != null) outputStream.print(state[i].continent.id +" ");
//				else outputStream.print(-1 + " ");// Throw Exception Here: State i is belong to an continents
//				if(state[i].resource != null) {
//					if(state[i].resource == Resources.NONE) 
//						outputStream.print(0 + " ");
//					else if(state[i].resource == Resources.METL) 
//						outputStream.print(1 + " ");
//					else if(state[i].resource == Resources.ENRG)
//						outputStream.print(2 + " ");
//					else
//						outputStream.print(3 + " ");
//				} else 
//					outputStream.print(-1 + " ");
//				
//				outputStream.print(state[i].deleted);
//				outputStream.println();
//			}
//			outputStream.close();
//		}
//	}
//
//	/**
//	 * Loading the adjacency matrix, node properties from the file
//	 */
//	public void LoadAs() {
//
//		int testLoad;
//		Scanner inputStream = null;
//		JFrame frm = new JFrame("Load Map");
//		frm.setAlwaysOnTop(true);
//		JPanel p = new JPanel();
//		JFileChooser fc = new JFileChooser();
//		Container c = frm.getContentPane();
//		FileNameExtensionFilter filter = new FileNameExtensionFilter("Txt file", "txt");
//		fc.setFileFilter(filter);
//		File f;
//		int flag = 0;
//
//		c.setLayout(new FlowLayout());
//		c.add(p);
//		fc.setDialogTitle("Open File");
//
//		try { 
//			File filedir=new File("/Users/Sara Nejad/workspace/TuesdayNightProject-alshRisk/");
//			fc.setCurrentDirectory(filedir);
//			flag = fc.showOpenDialog(frm);
//		} catch (HeadlessException head) {
//			System.out.println("Open File Dialog ERROR!");
//		}
//
//		if (flag == JFileChooser.APPROVE_OPTION) {
//			f = fc.getSelectedFile();
//			String fileName = fc.getCurrentDirectory() + "/"+fc.getName(f);
//
//			try {
//				inputStream = new Scanner(new FileInputStream(fileName));
//				testLoad = 1;
//			} catch (FileNotFoundException e) {
//				System.out.println("Problem opening files.");
//				testLoad = 0;
//				// System.exit(0);
//			}
//			
//			try{
//				if (testLoad == 1) {
//					Reset();
//					// Loading Node Count
//					nodeCount = inputStream.nextInt();	
//					
//					//Checks to see if the codeCount read from saved file is higher than MAX_NODE_Count it will through an exception
//					if (nodeCount > MAX_NODE_COUNT){
//						throw new NotCorrectFileformatException();
//					} 
//					
//					// Loading Links
//					if (inputStream.hasNextLine()) {
//						for (int i = 0; i < MAX_NODE_COUNT; i++) {
//							for (int j = 0; j < MAX_NODE_COUNT; j++) {
//								link[i][j] = inputStream.nextInt();
//							}
//						}
//					}
//					inputStream.nextLine();
//	
//					// Loading Continent Count and Creating Continents
//					continents.clear();
//					int count = inputStream.nextInt();
//					
//					//Checks to see if the count read from saved file is equal to zero it will through an exception
//					if (count < 0 ){
//						throw new NotCorrectFileformatException();
//					}
//	
//					colorsetiterator = 0;
//					for(int i = 0; i < count; i++) AddContinent();
//					//inputStream.nextLine();
//	
//					// Loading Nodes
//					//if (inputStream.hasNextLine()) {
//					for (int i = 0; i < MAX_NODE_COUNT; i++) {
//						state[i].name = inputStream.next();
//						state[i].x = inputStream.nextInt();
//						state[i].y = inputStream.nextInt();
//						
//						int id = inputStream.nextInt();
//						if(id != -1) state[i].continent = continents.get(id);
//						else state[i].continent = null;
//						
//						int resource = inputStream.nextInt();
//						if(resource == 1) state[i].resource = Resources.METL;
//						else if(resource == 2) state[i].resource = Resources.ENRG;
//						else if(resource == 3) state[i].resource = Resources.KLDG;
//						else state[i].resource = Resources.NONE;
//						
//						state[i].deleted = inputStream.nextBoolean();
//						inputStream.nextLine();
//					}
//					//}
//					System.out.println("open file----" + f.getName());
//				}
//			} catch(NotCorrectFileformatException e){
//				messageLoad = e.getMessage();
//				System.out.print(e.getMessage())	;
//				return;
//			}
//		}
//	}
//	
//	public void loadDefault() {
//		Scanner inputStream = null;
//		int testLoad;
//
//		try {
//			inputStream = new Scanner(new FileInputStream("original"));
//			testLoad = 1;
//		} catch (FileNotFoundException e) {
//			System.out.println("Problem opening files.");
//			testLoad = 0;
//			// System.exit(0);
//		}
//
//		try {
//			if (testLoad == 1) {
//				Reset();
//				// Loading Node Count
//				nodeCount = inputStream.nextInt();
//				
//				//Checks to see if the codeCount read from saved file is higher than MAX_NODE_Count it will through an exception
//				if (nodeCount > MAX_NODE_COUNT){
//					throw new NotCorrectFileformatException();
//				} 
//				// Loading Links
//				if (inputStream.hasNextLine()) {
//					for (int i = 0; i < MAX_NODE_COUNT; i++) {
//						for (int j = 0; j < MAX_NODE_COUNT; j++) {
//							link[i][j] = inputStream.nextInt();
//						}
//					}
//				}
//				inputStream.nextLine();
//	
//				// Loading Continent Count and Creating Continents
//				continents.clear();
//				int count = inputStream.nextInt();
//				
//				//Checks to see if the count read from saved file is equal to zero it will through an exception
//				if (count == 0 ){
//					throw new NotCorrectFileformatException();
//				}
//				
//				colorsetiterator = 0;
//				for(int i = 0; i < count; i++) AddContinent();
//				//inputStream.nextLine();
//	
//				// Loading Nodes
//				//if (inputStream.hasNextLine()) {
//				for (int i = 0; i < MAX_NODE_COUNT; i++) {
//					state[i].name = inputStream.next();
//					state[i].x = inputStream.nextInt();
//					state[i].y = inputStream.nextInt();
//					
//					int id = inputStream.nextInt();
//					if(id != -1) state[i].continent = continents.get(id);
//					else state[i].continent = null;
//					
//					int resource = inputStream.nextInt();
//					if(resource == 1) state[i].resource = Resources.METL;
//					else if(resource == 2) state[i].resource = Resources.ENRG;
//					else if(resource == 3) state[i].resource = Resources.KLDG;
//					else state[i].resource = Resources.NONE;
//					
//					state[i].deleted = inputStream.nextBoolean();
//	
//					inputStream.nextLine();
//				}
//				//}
//				GameEngine.log.add("Map loaded");
//			}
//		} catch(NotCorrectFileformatException e){
//			System.out.println("This file is not compatible with the required load file");	
//			System.exit(0);
//		}
//	}
//	
//	/**
//	 * inspects a certain state
//	 * @param mouseOverIndex index of the state to be inspected
//	 * @return returns a string containing all information about the state
//	 */
//	public String Inspect(int mouseOverIndex) {
//		String str = "";
//		int i = mouseOverIndex;
//		if(state[i].deleted == false) {
//
//			str = 
//					"State = " + state[i].name
//					+"\n------------\nContinent = ";
//			if(state[i].continent != null) str +=  state[i].continent.name; 
//			str += "\nOwned By = ";
//			if(state[i].ownedByPlayer != null) {
//				str += state[i].ownedByPlayer.name;
//				
//			}
//			str += "\nResources = " + state[i].resource.toString();
//
//			str += "\nAdjacent States = ";
//
//			for(int j = 0; j < MAX_NODE_COUNT; j++) {
//				if(link[i][j] == 1) {
//					str += state[j].name + ", ";
//				}
//			}
//			str += "\n";
//			str += "\n" + state[i].army.volunteer + " Volunteers";
//			str += "\n" + state[i].army.aircraft + " Aircrafts";
//			str += "\n" + state[i].army.artillery + " Artillerys";
//			str += "\n" + state[i].army.conscript + " Conscripts";
//			str += "\n" + state[i].army.heavyprecisionartillery + " Heavy Precisions";
//			str += "\n" + state[i].army.mechanizedinfantry + " Mechanized Infantrys";
//			str += "\n" + state[i].army.nuclearmissile + " Nukes";
//			str += "\n" + state[i].army.professionalsoldier + " Professional Soldiers";
//			str += "\n" + state[i].army.specialoperationssoldier + " S.P. Soldier";
//			str += "\n" + state[i].army.tacticalmissile + " Tactical Missile";
//		}
//		return str;
//	}
//	
//	/**
//	 * Test for Observer Pattern
//	 * @return return a test number which will be changed in the map model
//	 */
//	public int getTestObsNum() {
//		return testObsNum;
//	}
//	/**
//	 * Test for Observer Pattern
//	 * @param testObsNum
//	 * 					An integer number used for Observer Pattern test
//	 */
//	public void setTestObsNum(int testObsNum) {
//		this.testObsNum = testObsNum;
//		setChanged();
//		notifyObservers();
//	}
//	
//	public State getState(int index) {
//		return state[index];
//	}
//	
//	public State[] getState() {
//		return state;
//	}
//
//	/**
//	 * Test for Singleton Pattern
//	 * @return returns the map instance name
//	 */
//	public String getMapName() {
//		return mapName;
//	}
//	
//	/**
//	 * Test for Singleton Pattern
//	 * @param name 
//	 * 					Set the map instance name
//	 */
//	public void setMapName(String name) {
//		this.mapName = name;
//	}
//
//	/**
//	 * Test for Singleton Pattern
//	 * @return returns the number of current map instance
//	 */
//	public int getInstanceNum() {
//		return instanceNum;
//	}
//	
//}
//
