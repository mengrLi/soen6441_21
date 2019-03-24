package model;


import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Popups.*;
import controller.*;


public class Canvas extends JPanel implements Observer{
	GameEngine game;
	PanelController panelcontroller;
	PopupEdit popup;
	PopupMove popupm ;
	PopupCard popupCard;
	PopupAttack popupAttack;
	InspectorPopup insp = new InspectorPopup();

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
	 * @param panelcontroller Panel Bar
	 */
	public Canvas(GameEngine g, PanelController panelcontroller) {
		this.panelcontroller = panelcontroller;
		this.game = g;
	
		setBackground(Color.BLUE);
		popup = new PopupEdit(Map.getMapInstance(), this);
		popupm = new PopupMove(Map.getMapInstance(), this);
		popupCard=new PopupCard(Map.getMapInstance(),this);
		popupAttack=new PopupAttack(Map.getMapInstance(),this);


		addMouseListener(new aMouseHandler());
		addMouseMotionListener(new aMotionHandler());
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

	
	/**
	 * Rendering the graphics on the screen
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		super.paint(g2);	
		Map.getMapInstance().DrawLinks(g2);
	
		if(Map.getMapInstance().drawConnectingLine == true)
			if(selectedIndex != -1)
				Map.getMapInstance().DrawConnectingLine(g, selectedIndex, currMouseX, currMouseY, linemode);

		// draw node
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
        System.out.println("mouseClicked -----" + e.toString());
			if(e.getButton() == e.BUTTON3) {
				if(e.getClickCount() == 2) {

					// = = = = = Deleting Nodes = = = = =
					if(game.state == GameState.EDITMAP) {

						Map.getMapInstance().DeleteNode(mouseOverIndex);
					}

				}
				else if(e.getClickCount() == 1) 

					Map.getMapInstance().Inspect(selectedIndex);
					if(mouseOverIndex != -1){
						insp.textarea.setText(Map.getMapInstance().Inspect(mouseOverIndex));
						insp.popup.show(e.getComponent(), e.getX(), e.getY());
					}
			}


			if(e.getButton() == e.BUTTON1) {

				// = = = = = Adding Nodes = = = = =
				if(game.state == GameState.EDITMAP) {
					if(e.getClickCount() == 2)
					{	if(mouseOverIndex != -1) 
					{
						popup.textfield2.setText(Map.getMapInstance().getCountry(selectedIndex).getName());
						if(Map.getMapInstance().getCountry(selectedIndex).getContinent()!= null)
							popup.textfield.setText(Map.getMapInstance().getCountry(selectedIndex).getContinent().getName());
						popup.show(e.getComponent(), e.getX(), e.getY());
					}
					}
					else if(mouseOverIndex == -1) 
					{
						Map.getMapInstance().addNode(e.getX(), e.getY());
					}
				}

				if(game.state == GameState.STARTUP){
					Country country = Map.getMapInstance().getCountry(selectedIndex);
					System.out.println("place army :" + country);
					game.playerPlaceArmy(country);
				}

				if(game.state == GameState.REINFORCE){
					Country country = Map.getMapInstance().getCountry(selectedIndex);
					System.out.println("place army :" + country);
					game.reinforceArmy(country);

				}
				if(game.state==GameState.CHOOSECARD){
					if(e.getClickCount() == 2)
					{ if(mouseOverIndex != -1)
					{
						int card1 = Map.getMapInstance().getCountry(selectedIndex).getPlayer().getcardTypeNumber().get("Infantry") != null ? Map.getMapInstance().getCountry(selectedIndex).getPlayer().getcardTypeNumber().get("Infantry") : 0;
						int card2 = Map.getMapInstance().getCountry(selectedIndex).getPlayer().getcardTypeNumber().get("Cavalry") != null ? Map.getMapInstance().getCountry(selectedIndex).getPlayer().getcardTypeNumber().get("Cavalry") : 0;
						int card3 = Map.getMapInstance().getCountry(selectedIndex).getPlayer().getcardTypeNumber().get("Artillery") != null ? Map.getMapInstance().getCountry(selectedIndex).getPlayer().getcardTypeNumber().get("Artillery") : 0;
						popupCard.card1_num.setText(String.valueOf(card1));
						popupCard.card2_num.setText(String.valueOf(card2));
						popupCard.card3_num.setText(String.valueOf(card3));
						popupCard.refreshList();
						if(Map.getMapInstance().getCountry(selectedIndex).getContinent()!= null)
							//	popupm.textfield2.setText(Map.getMapInstance().getCountry(selectedIndex).getName());

							popupCard.show(e.getComponent(), e.getX(), e.getY());
					}
					}

				}
				if(game.state == GameState.FORTIFY) {
                    if(e.getClickCount() == 2)
                    { if(mouseOverIndex != -1)
                    {
                        popupm.textfield1.setText(Map.getMapInstance().getCountry(selectedIndex).getName());
                        popupm.refreshList(Map.getMapInstance().getCountry(selectedIndex));
                        if(Map.getMapInstance().getCountry(selectedIndex).getContinent()!= null)
                            //	popupm.textfield2.setText(Map.getMapInstance().getCountry(selectedIndex).getName());

                            popupm.show(e.getComponent(), e.getX(), e.getY());
                    }
                    }

                }
                if(game.state == GameState.ATTACK) {
                    if(e.getClickCount() == 2)
                    { if(mouseOverIndex != -1)
                    {
                        popupAttack.countryname.setText(Map.getMapInstance().getCountry(selectedIndex).getName());
                        popupAttack.playername.setText(Map.getMapInstance().getCountry(selectedIndex).getPlayer().getName());
                        popupAttack.armynumber.setText(String.valueOf("Army number"+Map.getMapInstance().getCountry(selectedIndex).getArmiesNum()));
						   popupAttack.countrypercent.setText(game.percentageOfmap(Map.getMapInstance().getCountry(selectedIndex).getPlayer()));
                        popupAttack.refreshList(Map.getMapInstance().getCountry(selectedIndex));
                        if(Map.getMapInstance().getCountry(selectedIndex).getContinent()!= null)
                            //	popupm.textfield2.setText(Map.getMapInstance().getCountry(selectedIndex).getName());

                            popupAttack.show(e.getComponent(), e.getX(), e.getY());
                    }
                    }

                }


			}
			
		}
	}


	/**
	 * Handles Mouse Movements 
	 *
	 */
	public class aMotionHandler extends MouseMotionAdapter {
		int x, y;
		public void mouseDragged(MouseEvent e) {
			currMouseX = e.getX();
			currMouseY = e.getY();

			mouseOverIndex = Map.getMapInstance().GetMouseOverIndex(currMouseX, currMouseY);
			if(game.state == GameState.EDITMAP) {
				if(e.getButton() == e.BUTTON1) {//add node button
					Map.getMapInstance().MoveNode(selectedIndex, currMouseX, currMouseY);
				}

				if(e.getButton() == e.BUTTON3) { // connect
					if(mouseOverIndex != -1) {
						linemode = 0;
						Map.getMapInstance().drawConnectingLine = true;
						if(selectedIndex != mouseOverIndex) {
							Map.getMapInstance().ConnectNodes(selectedIndex, mouseOverIndex);
							selectedIndex = -1;
							Map.getMapInstance().drawConnectingLine = false;
						}
					}
				}
			}


		}
		public void mouseMoved(MouseEvent e) {
			mouseOverIndex = Map.getMapInstance().GetMouseOverIndex(e.getX(), e.getY());
			if(mouseOverIndex != -1) {
				curCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
			}
			else {
				curCursor = Cursor.getDefaultCursor();

			}
			//repaint();
		}
	}
//
	/**
	 * Test for Observer Pattern
	 * @return return the model changed number
	 */
	public int getMapChangedNum() {
		return mapChangedNum;
	}
	
	/**
	 * Test for Observer Pattern
	 * @param mapChangedNum
	 * 						An integer number used for Observer Pattern test
	 */
	public void setMapChangedNum(int mapChangedNum) {
		this.mapChangedNum = mapChangedNum;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		repaint();

	}
	
}
