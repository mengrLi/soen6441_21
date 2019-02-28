package Models;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.JPanel;

import Popups.*;
import View.PanelBar;
/**
 * This class is responsible for plotting the graph (or map) on the screen, and handling mouse motion and mouse clicks
 * @author aliahadi
 *
 */
public class Canvas extends JPanel implements Observer{
	GameEngine game;
	PanelBar panelbar;
	//PopupEdit popup;
	InspectorPopup insp = new InspectorPopup();
	static final int EDIT = 0;
	static final int CHOOSE_PLAYER = 1;
	static final int PLACE_TROOPS = 2;
	Cursor curCursor;
	int currMouseX, currMouseY; //Current Mouse Position

	//Selects the Index of the first node to be connected to the second node while dragging
	public static int selectedIndex = -1;
	public int mouseOverIndex = -1;

	int linemode = 0;// Change this later
	
	private int mapChangedNum = 0; //Just for testing the Observer Pattern
	/**
	 * Constructor used for Observer Pattern unit test
	 */
	public Canvas() {}
	
	/**
	 * Constructor
	 * @param g Game Engine
	 * @param panelbar Panel Bar
	 */
	public Canvas(GameEngine g, PanelBar panelbar) {
		this.panelbar = panelbar;
		this.game = g;
	
		setBackground(Color.BLUE);
		//popup = new PopupEdit(Map.getMapInstance(), this);
		addMouseListener(new aMouseHandler());
		//addMouseMotionListener(new aMotionHandler());
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
	/*create image icon to get image*/
	ImageIcon imageicon = new ImageIcon(getClass().getResource("world.png"));
	Image image = imageicon.getImage();

	/*Draw image on the panel*/
	super.paintComponent(g);

	if (image != null) {
		 g2d.setComposite(AlphaComposite.getInstance(
		            AlphaComposite.SRC_OVER, 0.5f));
	g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
	}
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Rendering the graphics on the screen
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		super.paint(g2);
			
	//	Map.getMapInstance().DrawLinks(g2);
	
//		if(Map.getMapInstance().drawConnectingLine == true)
//			if(selectedIndex != -1)
//				Map.getMapInstance().DrawConnectingLine(g, selectedIndex, currMouseX, currMouseY, linemode);

		// Plotting The Graphs
		Map.getMapInstance().DrawNodes(g2);

		if (curCursor != null)
			setCursor(curCursor);
	}

	/**
	 * 
	 * Handles Mouse Buttons
	 *
	 */
	public class aMouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			if(mouseOverIndex != -1) 
				selectedIndex = mouseOverIndex;
			
		}

		public void mouseReleased(MouseEvent e) {
			
		}

		public void mouseEntered(MouseEvent e) {
			
		}

		public void mouseExited(MouseEvent e) {
			
		}

		public void mouseClicked(MouseEvent e) {

			if(e.getButton() == e.BUTTON3) {
				if(e.getClickCount() == 2) {

					// = = = = = Deleting Nodes = = = = =
					if(game.state == GameState.EDIT) {

						Map.getMapInstance().DeleteNode(mouseOverIndex);
					}

				}
				else if(e.getClickCount() == 1) 

					//Map.getMapInstance().Inspect(selectedIndex);
					if(mouseOverIndex != -1){
						//insp.textarea.setText(Map.getMapInstance().Inspect(mouseOverIndex));
						insp.popup.show(e.getComponent(), e.getX(), e.getY());
					}
			}


			if(e.getButton() == e.BUTTON1) {

				// = = = = = Adding Nodes = = = = =
				if(game.state == GameState.EDIT) {
					if(e.getClickCount() == 2)
					{	if(mouseOverIndex != -1) 
					{
//						popup.textfield2.setText(Map.getMapInstance().state[selectedIndex].name);
//						if(Map.getMapInstance().state[selectedIndex].continent != null)
//							popup.textfield.setText(Map.getMapInstance().state[selectedIndex].continent.name);
//						popup.show(e.getComponent(), e.getX(), e.getY());
					}
					}
					else if(mouseOverIndex == -1) 
					{
						Map.getMapInstance().AddNode(e.getX(), e.getY());
					}
				}
			}
			//repaint();
		}
	}


//	/**
//	 * Handles Mouse Movements 
//	 *
//	 */
//	public class aMotionHandler extends MouseMotionAdapter {
//		int x, y;
//		public void mouseDragged(MouseEvent e) {
//			currMouseX = e.getX();
//			currMouseY = e.getY();
//
//			mouseOverIndex = Map.getMapInstance().GetMouseOverIndex(currMouseX, currMouseY);
//			if(game.state == GameState.EDIT) {
//				if(e.getButton() == e.BUTTON1) {
//					Map.getMapInstance().MoveNode(selectedIndex, currMouseX, currMouseY);
//				}
//
//				if(e.getButton() == e.BUTTON3) {
//					if(mouseOverIndex != -1) {
//						linemode = 0;
//						Map.getMapInstance().drawConnectingLine = true;
//						if(selectedIndex != mouseOverIndex) {
//							Map.getMapInstance().ConnectNodes(selectedIndex, mouseOverIndex);
//							selectedIndex = -1;
//							Map.getMapInstance().drawConnectingLine = false;
//						}
//					}
//				}
//			}
//
//			if(game.state == GameState.ATTACK) {
//				if(e.getButton() == e.BUTTON1) {
//					if(mouseOverIndex != -1) {
//						linemode = 2;
//						Map.getMapInstance().drawConnectingLine = true;
//						if(selectedIndex != mouseOverIndex) {
//							//if(mouseOverIndex != -1 && selectedIndex != -1)
//								//game.Attack(game.player.get(0), selectedIndex, mouseOverIndex);
//								selectedIndex = -1;
//							Map.getMapInstance().drawConnectingLine = false;
//						}
//					}
//				}
//			}
//			//repaint();
//		}
//		public void mouseMoved(MouseEvent e) {
//			mouseOverIndex = Map.getMapInstance().GetMouseOverIndex(e.getX(), e.getY());
//			if(mouseOverIndex != -1) {
//				curCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
//			}
//			else {
//				curCursor = Cursor.getDefaultCursor();
//
//			}
//			//repaint();
//		}
//	}
//
//	/**
//	 * Test for Observer Pattern
//	 * @return return the model changed number
//	 */
//	public int getMapChangedNum() {
//		return mapChangedNum;
//	}
//	
//	/**
//	 * Test for Observer Pattern
//	 * @param mapChangedNum
//	 * 						An integer number used for Observer Pattern test
//	 */
//	public void setMapChangedNum(int mapChangedNum) {
//		this.mapChangedNum = mapChangedNum;
//	}
//	
//	@Override
//	public void update(Observable arg0, Object arg1) {
//		// TODO Auto-generated method stub
//		repaint();
//		setMapChangedNum(((Map)arg0).getTestObsNum());
//	}
	
}
