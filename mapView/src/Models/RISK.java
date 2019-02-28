package Models;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import Popups.*;
import View.*;





public class RISK extends JFrame implements ActionListener{
	private static final long serialVersionUID = 707427497898546563L;
	public static final int WINDOW_WIDTH = 1100;
	public static final int WINDOW_HEIGHT = 800;
	public static GameEngine game = new GameEngine();
	public static PanelBar panelbar = new PanelBar(game);
	Canvas canvas = new Canvas(game, panelbar);

		
		public static void main(String[] args) {
			game.log = panelbar.log;

			RISK drawing = new RISK();
			drawing.setVisible(true);
		}
		/**
		 * Initializing Graphics, UI, Map
		 */
		public RISK() {
			
			// Graphics Initialization
			super("Team21_project");
			setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
			setLocation(400, 400);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setResizable(false);
			setAlwaysOnTop(true);
			System.out.print("Frame Initialization...");
			
			// Creating the window template
			add(canvas, BorderLayout.CENTER);
		    //panelbar.simpanel.disable(); //
			add(panelbar.mainpanel, BorderLayout.SOUTH);//
			
			
			Map.getMapInstance().addObserver(canvas);
			
			///////////////////////////
			// Default 3 Player mode //
			// comment out this code //
			// block to back to nor- //
			// mal mode ///////////////
//			Map.getMapInstance().loadDefault();
//			game.numberofplayers = 3;
//			game.AddANewPlayer();
//			game.AddANewPlayer();
//			game.AddANewPlayer();
//			game.AssignPlayers();
//			game.state = GameState.REENFORCE;
//			panelbar.SetActivePanel(panelbar.placearmypanel);
//			panelbar.simpanel.enable();

			/////////////////////////

			panelbar.AddActionListener(this);

		}
		/**
		 *  Invoking relevant methods when a certain action performed 
		 */
		public void actionPerformed(ActionEvent e) {
			panelbar.ActionPerformed(e);
			//repaint();
		}



	}