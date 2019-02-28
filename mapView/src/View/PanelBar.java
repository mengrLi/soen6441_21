package View;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Models.*;


public class PanelBar {
	private GameEngine game;
	//private Map map = Map.getMapInstance();
	//public int selectedIndex = MapEditor.selectedIndex;
	public JPanel card, test;
	public JPanel mainpanel;
	public LogWindow log = new LogWindow();
	//public AssignPlayerPanel assignplayerpanel = new AssignPlayerPanel();
	public EditPanel editpanel = new EditPanel();
	//public static PlaceArmyPanel placearmypanel = new PlaceArmyPanel();
//	public AttackPanel attackpanel = new AttackPanel();
//	public MovePanel movepanel = new MovePanel();
//	public static SimPanel simpanel = new SimPanel();
	boolean auto = false;

	public PanelBar(GameEngine game) {
		this.game = game;
		editpanel.panel.setVisible(true);
		mainpanel = new JPanel();
		card = new JPanel(new CardLayout());
		GridBagConstraints c = new GridBagConstraints();

		card.add(editpanel.panel, "1");
//		card.add(assignplayerpanel.panel, "2");
//		card.add(placearmypanel.panel, "3");
//		card.add(attackpanel.panel, "4");
//		card.add(movepanel.panel, "5");
		c.fill = GridBagConstraints.HORIZONTAL;
		mainpanel.setLayout(new GridBagLayout());
		mainpanel.setBackground(Theme.color);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		mainpanel.add(card, c);
		c.gridx = 0;
		c.gridy = 1;
	//	mainpanel.add(simpanel.getComponent(), c);

		//c.fill = GridBagConstraints.VERTICAL;
		c.gridx = 0;
		c.gridy = 2;
		mainpanel.add(log.getComponent(), c);
		test = new JPanel();
		test.add(mainpanel);


	}
	//public void SetActivePanel(Panel panel) {
		//assignplayerpanel.SetVisible(false);
//		editpanel.SetVisible(false);
//		//placearmypanel.SetVisible(false);
//		//attackpanel.SetVisible(false);
//		//movepanel.SetVisible(false);
//		panel.SetVisible(true);
//	}
//
//	public void ActionPerformed(ActionEvent e) {
//
//		if(e.getSource() == editpanel.clear) {
//			game.Reset();
//		}
//		if(e.getSource() == assignplayerpanel.next) {
//			for(int i = 0; i < assignplayerpanel.combo.getSelectedIndex()+2; i++)
//				game.AddANewPlayer();
//
//			game.state = GameState.REENFORCE;
//			game.AssignPlayers();
//			if(!game.GameOver())
//				SetActivePanel(placearmypanel);
//			simpanel.enable();
//		}
//		else if(e.getSource() == assignplayerpanel.back) {
//			game.state = GameState.EDIT;
//			SetActivePanel(editpanel);
//		}

//		else if(e.getSource() == editpanel.next) {
//			if(map.()) {
//				SetActivePanel(assignplayerpanel);
//				game.state = GameState.CHOOSE_PLAYER;
//
//			}
//		}
//		else if(e.getSource() == placearmypanel.next) {
//			game.state = GameState.ATTACK;
//			String str = game.getCurrentPlayer() + ": " ;
//			Color c = game.getCurrentPlayerColor();
//			attackpanel.setText(str, c);
//			SetActivePanel(attackpanel);
//			if(!game.GameOver())
//				if(!game.ReEnforce()) game.NextPlayer();
//
//		}
//
//
//		else if(e.getSource() == attackpanel.next) {
//			game.state = GameState.FORTIFY;
//			String str = game.getCurrentPlayer() + ": " ;
//			Color c = game.getCurrentPlayerColor();
//			movepanel.setText(str, c);
//			if(!game.GameOver()) {
//				game.Move();
//				game.NextPlayer();
//
//				placearmypanel.setText(str, c);
//				SetActivePanel(placearmypanel);
//			}
//			else {
//				attackpanel.setText(str, c);
//				attackpanel.label.setText(" You Won!");
//				attackpanel.next.setEnabled(false);
//			}
//
//		}
//
//		else if(e.getSource() == movepanel.next) {
//			game.state = GameState.REENFORCE;
//			game.NextPlayer();
//			String str = game.getCurrentPlayer() + ": " ;
//			Color c = game.getCurrentPlayerColor();
//			placearmypanel.setText(str, c);
//			SetActivePanel(placearmypanel);
//		}
//		
//		else if(e.getSource() == editpanel.back) {
//			Map.getMapInstance().loadDefault();
//			game.numberofplayers = 3;
//			game.AddANewPlayer();
//			game.AddANewPlayer();
//			game.AddANewPlayer();
//			game.AssignPlayers();
//			game.state = GameState.REENFORCE;
//			SetActivePanel(placearmypanel);
//			simpanel.enable();
//		}

		//else if(e.getSource() == editpanel.save) {
		//	map.save_as();
//		}
//		else if(e.getSource() == editpanel.load) {
//			map.LoadAs();
//		}
//		else if(e.getSource() == simpanel.auto) {
//			game.Loop();
//			String str = game.getCurrentPlayerName() + ": " ;
//			Color c = game.getCurrentPlayerColor();
//			placearmypanel.setText(str+" You Won !", c);
//			//placearmypanel.label.setText(" You  Won !");
//		}
//		else if(e.getSource() == simpanel.fullbutton) {
//			if(game.GameOver() == false) {
//				String str = game.getCurrentPlayerName() + ": " + game.getCurrentState().toString() ;
//				Color c = game.getCurrentPlayerColor();
//				placearmypanel.setText(str, c);
//
//				int round = GameEngine.round;
//				while(round == GameEngine.round) {
//					game.ReEnforce();
//					game.Move();
//					game.state = GameState.REENFORCE;
//					game.NextPlayer();
//					if(game.GameOver()) log.add("Game Over");
//				}
//			}
//		}
//		else if(e.getSource() == simpanel.turnbutton) {
//			if(game.GameOver() == false) {
//				String str = game.getCurrentPlayerName() + ": " + game.getCurrentState().toString() ;
//				Color c = game.getCurrentPlayerColor();
//				placearmypanel.setText(str, c);
//
//				game.ReEnforce();
//				game.Move();
//				game.state = GameState.REENFORCE;
//				game.NextPlayer();
//				if(game.GameOver()) log.add("Game Over");
//			}
//
//		}
//		else if(e.getSource() == simpanel.phasebutton) {
//			if(!game.GameOver()) {
//				String str = game.getCurrentPlayerName() + ": " + game.getCurrentState().toString() ;
//				Color c = game.getCurrentPlayerColor();
//				placearmypanel.setText(str, c);
//
//				game.nextState();
//			}
//		}
//		else if(e.getSource() == simpanel.savesim) {
//			game.SaveSim();
//		}
//		else if(e.getSource() == simpanel.loadsim) {
//			game.LoadSim();
//		}
//		else if(e.getSource() == simpanel.combo2) {
//			game.technologylevel = simpanel.combo2.getSelectedIndex()+1;
//			game.log.add("Technology Level is now " + game.technologylevel);
//			Player p = game.getCurrentPlayer();
//			placearmypanel.label1.setText("Resource = " + p.getResourceLevel() + " Tech = " + game.technologylevel);
//		}
//		else if(e.getSource() == simpanel.combo) {
//			int x = simpanel.combo.getSelectedIndex();;
//			Player player = game.getCurrentPlayer();
//			if(x != 0) {
//				game.override = true;
//				String str = "";
//				if (x == 1) {
//					player.metal = false;
//					player.energy = false;
//					player.knowledge = false;
//					str = "Nothing";
//				}
//				if(x == 2) {
//					player.metal = true;
//					player.energy = false;
//					player.knowledge = false;
//					str = "Metal";
//				}
//				else if(x == 3) {
//					player.metal = true;
//					player.energy = true;
//					player.knowledge = false;
//					str = "Energy";
//				}
//				else if(x == 4) {
//					player.metal = true;
//					player.energy = true;
//					player.knowledge = true;
//					str = "Knowledge";
//				}
//				game.log.add(game.getCurrentPlayerName() + " controls " + str);
//
//			}
//			else {
//				game.override = false;
//			}
//
//

	//	}
//
//		Player p = game.getCurrentPlayer();
//		if(p != null)
//			placearmypanel.label1.setText("Resource = " + p.getResourceLevel() + " Tech = " + game.technologylevel);
//
//	}

	public void AddActionListener(ActionListener e) {
		//assignplayerpanel.AddActionListener(e);
		editpanel.AddActionListener(e);
//		placearmypanel.AddActionListener(e);
//		attackpanel.AddActionListener(e);
//		movepanel.AddActionListener(e);
//		attackpanel.auto.addActionListener(e);
//		simpanel.auto.addActionListener(e);
//		simpanel.phasebutton.addActionListener(e);
//		simpanel.turnbutton.addActionListener(e);
//		simpanel.fullbutton.addActionListener(e);
//		simpanel.savesim.addActionListener(e);
//		simpanel.loadsim.addActionListener(e);
//		simpanel.combo2.addActionListener(e);
//		simpanel.combo.addActionListener(e);


	}

	public void ActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stubC
		
		
	}

}
