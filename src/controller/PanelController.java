package controller;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import model.*;
import View.*;
import model.Theme;


public class PanelController {
	private GameEngine game;
	private Map map = Map.getMapInstance();
	public int selectedIndex = Canvas.selectedIndex;
	public JPanel version, test;
	public JPanel mainpanel;
	public LogWindow log = new LogWindow();
	public AssignPlayerPanel assignplayerpanel = new AssignPlayerPanel();
	public EditPanel editpanel = new EditPanel();
	public static PlaceArmyPanel placearmypanel = new PlaceArmyPanel();
	public ReinforcePanel reinforcepanel = new ReinforcePanel();
	//	public MovePanel movepanel = new MovePanel();
	public static SimPanel simpanel = new SimPanel();
	boolean auto = false;


	public PanelController(GameEngine game) {
		this.game = game;
		editpanel.panel.setVisible(true);
		mainpanel = new JPanel();
		version = new JPanel(new CardLayout());
		GridBagConstraints c = new GridBagConstraints();

		version.add(editpanel.panel, "1");
		version.add(assignplayerpanel.panel, "2");
		version.add(placearmypanel.panel, "3");
		version.add(reinforcepanel.panel, "4");
//		card.add(movepanel.panel, "5");
		c.fill = GridBagConstraints.HORIZONTAL;
		mainpanel.setLayout(new GridBagLayout());
		mainpanel.setBackground(Theme.color);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		mainpanel.add(version, c);
		c.gridx = 0;
		c.gridy = 1;
		mainpanel.add(simpanel.getComponent(), c);


		c.gridx = 0;
		c.gridy = 2;
		mainpanel.add(log.getComponent(), c);
		test = new JPanel();
		test.add(mainpanel);


	}
	public void SetActivePanel(aPanel panel) {
		assignplayerpanel.SetVisible(false);
		editpanel.SetVisible(false);
		placearmypanel.SetVisible(false);
		reinforcepanel.SetVisible(false);
//		//movepanel.SetVisible(false);
		panel.SetVisible(true);
	}
	/**
	 * Resets the game
	 */
	public void Reset() {
		map.reset();
		//playerlist.clear();
	}
	public void ActionPerformed(ActionEvent e) {

		if (e.getSource() == editpanel.clear) {
			Reset();
		} else if (e.getSource() == editpanel.save) {
			String checkInfo = map.checkMapValidation("save");
			log.add(checkInfo);
			if(checkInfo.equals("Map is successfully connected!")) {
				String saveInfo = map.save();
				log.add(saveInfo);
			}

		} else if (e.getSource() == editpanel.load) {
			String loadInfo = map.Load();
			log.add(loadInfo);
		}


		if (e.getSource() == assignplayerpanel.next) {
			int numOfPlayers = Integer.parseInt(assignplayerpanel.combo.getSelectedItem().toString());
			System.out.println("numOfPlayers:" + numOfPlayers);
			game.setPlayerList(numOfPlayers);

			game.state = GameState.STARTUP;
			game.AssignPlayers();

			SetActivePanel(placearmypanel);

		} else if (e.getSource() == assignplayerpanel.back) {
			game.state = GameState.EDITMAP;
			SetActivePanel(editpanel);
		}
		else if (e.getSource() == editpanel.next) {
			String checkInfo = map.checkMapValidation("load");
			System.out.println(" checkMapValidation next"+map.checkMapValidation("load"));
			log.add(checkInfo);
			if(checkInfo.equals("Map is successfully connected!")) {
				SetActivePanel(assignplayerpanel);
				game.state = GameState.CHOOSEPLAYER;
			}
//			else if(e.getSource()== ReinforcePanel.textfield)
//			{
//				//textfield.setText(game.getCurPlayer().getName());
//			}
		} else if (e.getSource() == placearmypanel.next) {
//			System.out.println("1 placearmypanel.next");
//			System.out.println(game.getCurrentState());
			game.state = GameState.CHOOSECARD;
			SetActivePanel(reinforcepanel);
			simpanel.enable();

		} else if (e.getSource() == placearmypanel.back) {
			game.state = GameState.CHOOSEPLAYER;
			SetActivePanel(assignplayerpanel);


		} else if (e.getSource() == reinforcepanel.back) {
			game.state = GameState.STARTUP;
			SetActivePanel(placearmypanel);
			simpanel.disable();

		}
		else if(e.getSource() == simpanel.reinforcebutton) {

			game.state=GameState.REINFORCE;
			game.reinforce();


		}
		else if(e.getSource() == reinforcepanel.next) {
			game.state=GameState.CHOOSECARD;
			//SetActivePanel(assignplayerpanel);
			// simpanel.disable();
			game.turnToNextPlayer();
			System.out.println("next player");

		}
		else if(e.getSource() == simpanel.movebutton) {
			game.state=GameState.FORTIFY;

			System.out.println("fortify");



		}
		else if(e.getSource() == reinforcepanel.button) {
			game.state=GameState.CHOOSECARD;
			//SetActivePanel(assignplayerpanel);
			// simpanel.disable();
			game.turnToNextPlayer();
			reinforcepanel.label.setText(game.getCurPlayer().getName());
			System.out.println("next player");

		}
		else if(e.getSource() == simpanel.attackbutton) {
			game.state=GameState.ATTACK;

			System.out.println("attack");
			log.add("attack phase");
		}

		else if(e.getSource()==simpanel.choosecardbutton){
			game.state=GameState.CHOOSECARD;

		}
	}


	public void AddActionListener (ActionListener e){
		assignplayerpanel.AddActionListener(e);
		editpanel.AddActionListener(e);
		placearmypanel.AddActionListener(e);
		reinforcepanel.AddActionListener(e);
		simpanel.reinforcebutton.addActionListener(e);
		simpanel.movebutton.addActionListener(e);
		simpanel.attackbutton.addActionListener(e);
		simpanel.choosecardbutton.addActionListener(e);
		reinforcepanel.button.addActionListener(e);

	}

}
