package control;
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

import view.InfoPopup;
import control.PopupEdit;
import models.Map;
import models.GameState;
import models.Country;
import models.GameDriver;

import javax.swing.JPanel;

public class MapEditor extends JPanel implements Observer {
	GameDriver game;
	PopupEdit popup;
	InfoPopup info = new InfoPopup();
	static final int EDIT = 0;
	static final int CHOOSE_PLAYER = 1;
	static final int PLACE_TROOPS = 2;
	Cursor curCursor;
	
	int currMouseX, currMouseY; //Current Mouse Position
	//Selects the Index of the first node to be connected to the second node while dragging
		public static int selectedIndex = -1;
		public int mouseOverIndex = -1;
		int linemode = 0;// Change this later
		
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

			// Plotting The Graph
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
				//if(prevselectedindex != selectedIndex) ;
				//System.out.print("\nPressed");
			}

			public void mouseReleased(MouseEvent e) {
				//System.out.print("\nReleased");
				//selectedIndex = -1;
				//repaint();
			}

			public void mouseEntered(MouseEvent e) {
				//System.out.println("Entered");
			}

			public void mouseExited(MouseEvent e) {
				//System.out.println("Exited");
			}

			public void mouseClicked(MouseEvent e) {
				//System.out.print("\nClicked");

				if(e.getButton() == e.BUTTON3) {
					if(e.getClickCount() == 2) {

						// = = = = = Deleting Nodes = = = = =
						if(game.state == GameState.EDITMAP) {

							Map.getMapInstance().DeleteNode(mouseOverIndex);
						}

					}
					else if(e.getClickCount() == 1) 

						//Map.getMapInstance().Inspect(selectedIndex);
						if(mouseOverIndex != -1){
							info.textarea.setText(Map.getMapInstance().Inspect(mouseOverIndex));
							info.popup.show(e.getComponent(), e.getX(), e.getY());
						}
				}


				if(e.getButton() == e.BUTTON1) {

					// = = = = = Adding Nodes = = = = =
					if(game.state == GameState.EDITMAP) {
						if(e.getClickCount() == 2)
						{	if(mouseOverIndex != -1) 
						{
							popup.textfield2.setText(Map.getMapInstance().country[selectedIndex].name);
							if(Map.getMapInstance().country[selectedIndex].continent != null)
								popup.textfield1.setText(Map.getMapInstance().country[selectedIndex].continent.name);
							popup.show(e.getComponent(), e.getX(), e.getY());
						}
						}
						else if(mouseOverIndex == -1) 
						{
					//todo
							//Map.getMapInstance().addCountry();
						}
					}
				}
				//repaint();
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
					if(e.getButton() == e.BUTTON1) {
						Map.getMapInstance().MoveNode(selectedIndex, currMouseX, currMouseY);
					}

					if(e.getButton() == e.BUTTON3) {
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

//				if(game.state == GameState.ATTACK) {
//					if(e.getButton() == e.BUTTON1) {
//						if(mouseOverIndex != -1) {
//							linemode = 2;
//							Map.getMapInstance().drawConnectingLine = true;
//							if(selectedIndex != mouseOverIndex) {
//								//if(mouseOverIndex != -1 && selectedIndex != -1)
//									//game.Attack(game.player.get(0), selectedIndex, mouseOverIndex);
//									selectedIndex = -1;
//								Map.getMapInstance().drawConnectingLine = false;
//							}
//						}
//					}
//				}
				
			}
		
		}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
