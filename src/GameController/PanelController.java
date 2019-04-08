package GameController;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import GameModel.GameLoad;
import GameModel.PlayerEngine;
import GameModel.GameState;
import GameView.*;
import GameView.StartTournamentPanel;
import MapController.Canvas;
import MapModel.Map;
import MapView.*;

/**
 * This is a game controller class,
 * used to control game state match with panel version and control button function in panels.
 *
 * @author: Mengran Li
 */

public class PanelController {
    private PlayerEngine game;
    private Map map = Map.getMapInstance();
    public JPanel version, test;
    public JPanel mainpanel;
    public LogWindow log = new LogWindow();
    public AssignPlayerPanel assignplayerpanel = new AssignPlayerPanel();
    public AssignRolesPanel assignRolesPanel = new AssignRolesPanel();
    public EditPanel editpanel = new EditPanel();
    public static PlaceArmyPanel placearmypanel = new PlaceArmyPanel();
    public ReinforcePanel reinforcepanel = new ReinforcePanel();
    public static SimPanel simpanel = new SimPanel();
    public static ChooseModePanel chooseModePanel = new ChooseModePanel();
    public static TournamentPanel tournamentPanel = new TournamentPanel();

    public AssignTourRolesPanel assignTourRolesPanel=new AssignTourRolesPanel();
    public StartTournamentPanel startTournamentPanel = new StartTournamentPanel();
    public AutoPlayPanel autoPlayPanel=new AutoPlayPanel();
    public SelectMapNumPanel selectMapNumPanel =new SelectMapNumPanel();
    public SelectMapPanel selectMapPanel=new SelectMapPanel();


    public PanelController(PlayerEngine game) {
        this.game = game;
        chooseModePanel.panel.setVisible(true);
        mainpanel = new JPanel();
        version = new JPanel(new CardLayout());

        GridBagConstraints c = new GridBagConstraints();

        version.add(chooseModePanel.panel);
        version.add(editpanel.panel);
        version.add(simpanel.mainpanel);
        version.add(assignplayerpanel.panel);
        version.add(assignRolesPanel.panel);
        version.add(placearmypanel.panel);
        version.add(reinforcepanel.panel);
        version.add(tournamentPanel.panel);
        version.add(assignTourRolesPanel.panel);
        version.add(startTournamentPanel.panel);
        version.add(autoPlayPanel.panel);
        version.add(selectMapNumPanel.panel);
        version.add(selectMapPanel.panel);

        c.fill = GridBagConstraints.HORIZONTAL;
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Theme.color);

        c.gridx = 0;
        c.gridy = 0;
        // c.gridwidth = 1;
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

    /**
     * @Descpription: this method is to set panel version match with game state
     * @Params: panel
     * @Date: 2019/3/26
     */

    public void SetActivePanel(aPanel panel) {
        chooseModePanel.SetVisible(false);
        assignplayerpanel.SetVisible(false);
        assignRolesPanel.SetVisible(false);
        editpanel.SetVisible(false);
        placearmypanel.SetVisible(false);
        reinforcepanel.SetVisible(false);
        tournamentPanel.SetVisible(false);
        assignTourRolesPanel.SetVisible(false);
        startTournamentPanel.SetVisible(false);
        autoPlayPanel.SetVisible(false);
        selectMapNumPanel.SetVisible(false);
        selectMapPanel.SetVisible(false);

        panel.SetVisible(true);
    }

    /**
     * Resets the game
     */
    public void Reset() {
        map.reset();

    }

    /**
     * @Descpription: this method is to control all buttons in different panels and perform action with click actions.
     * @Params: action event
     * @Date: 2019/3/26
     */

    public void ActionPerformed(ActionEvent e) {
        if (e.getSource() == chooseModePanel.Single_Game_Mode) {
            game.state = GameState.EDITMAP;
            SetActivePanel(editpanel);

        }
        else if(e.getSource()==chooseModePanel.Tournament_Mode)
        {
            SetActivePanel(selectMapNumPanel);
        }
        else if(e.getSource()== selectMapNumPanel.next)
        {
            int mapNumber=Integer.parseInt(selectMapNumPanel.combo.getSelectedItem().toString());
            System.out.println(mapNumber);
            game.numberofmaps=mapNumber;
            SetActivePanel(selectMapPanel);

        }
        else if(e.getSource()==selectMapPanel.next)
        {

            SetActivePanel(startTournamentPanel);
        }
        else if(e.getSource()==selectMapPanel.select)
        {
            String loadInfo = game.TourMapLoad();
            log.add(loadInfo);
        }
        else if (e.getSource() == startTournamentPanel.next) {
            //   String checkInfo = map.checkMapValidation("load");
            //   System.out.println(" checkMapValidation next" + map.checkMapValidation("load"));
            //   log.add(checkInfo);
            //   if (checkInfo.equals("Map is successfully connected!")) {
            int numOfGames = Integer.parseInt(startTournamentPanel.gamecombo.getSelectedItem().toString());
            System.out.println("numOfPlayers:" + numOfGames);
            game.gameNum=numOfGames;

            SetActivePanel(tournamentPanel);

        } else if (e.getSource() == startTournamentPanel.back) {
            game.state = GameState.CHOOSEMODEL;
            SetActivePanel(chooseModePanel);
//        } else if (e.getSource() == startTournamentPanel.selectMap) {
//            String loadInfo = map.Load();
//            log.add(loadInfo);
        } else if (e.getSource() == tournamentPanel.back) {
            SetActivePanel(startTournamentPanel);
        }
        else if(e.getSource()==tournamentPanel.next)
        {
            int numOfPlayers = Integer.parseInt(tournamentPanel.playercombo.getSelectedItem().toString());
            System.out.println("numOfPlayers:" + numOfPlayers);
            game.setPlayerList(numOfPlayers);

            int numberOfTurns = Integer.parseInt(tournamentPanel.turncombo.getSelectedItem().toString());
            System.out.println("numOfTurns:" + numberOfTurns);
            game.maxRoundNum = numberOfTurns;

            game.state = GameState.ASSIGNROLES;
            SetActivePanel(assignTourRolesPanel);
        }

        if (e.getSource() == editpanel.clear) {
            Reset();
        } else if (e.getSource() == editpanel.save) {
            String checkInfo = map.checkMapValidation("save");
            log.add(checkInfo);
            if (checkInfo.equals("Map is successfully connected!")) {
                String saveInfo = map.save();
                log.add(saveInfo);
            }

        } else if (e.getSource() == editpanel.load) {
            String loadInfo = map.Load();
            log.add(loadInfo);
        } else if (e.getSource() == editpanel.back) {
            game.state = GameState.CHOOSEMODEL;
            SetActivePanel(chooseModePanel);
        }
        //========================================= game load and save ============================================
        else if(e.getSource()==editpanel.loadGame)
        {
            GameLoad gameload = new GameLoad();
            gameload.LoadGame();
            log.add("Now current player is " + game.getCurPlayerNameWithColor());
            log.add(game.state.name());
            SetActivePanel(reinforcepanel);
            simpanel.enable();
            Runnable gameStart = ()-> {
                game.continueToplay();
            };
            Thread thread = new Thread(gameStart);
            thread.start();

        }
        else if(e.getSource()==simpanel.savebutton)
        {// nonepointererror
            game.save();
        }
        //========================================= game load and save ============================================

        if (e.getSource() == assignplayerpanel.next) {
            int numOfPlayers = Integer.parseInt(assignplayerpanel.combo.getSelectedItem().toString());
            System.out.println("numOfPlayers:" + numOfPlayers);
            game.setPlayerList(numOfPlayers);

            game.state = GameState.ASSIGNROLES;
            SetActivePanel(assignRolesPanel);

        } else if (e.getSource() == assignRolesPanel.next) {
            game.state = GameState.STARTUP;
            game.AssignPlayers();
            SetActivePanel(placearmypanel);

        } else if (e.getSource() == assignRolesPanel.back) {
            game.state = GameState.CHOOSEPLAYER;
            SetActivePanel(assignplayerpanel);
        } else if (e.getSource() == assignTourRolesPanel.next) {
            Runnable gameStart = ()-> {
                game.autoPlay();
            };
            Thread thread = new Thread(gameStart);
            thread.start();
            SetActivePanel(autoPlayPanel);

        } else if (e.getSource() == assignTourRolesPanel.back) {

            SetActivePanel(tournamentPanel);
        } else if (e.getSource() == assignplayerpanel.back) {
            game.state = GameState.EDITMAP;
            SetActivePanel(editpanel);

        } else if (e.getSource() == editpanel.next) {
            String checkInfo = map.checkMapValidation("load");
            System.out.println(" checkMapValidation next" + map.checkMapValidation("load"));
            log.add(checkInfo);
            if (checkInfo.equals("Map is successfully connected!")) {
                SetActivePanel(assignplayerpanel);
                game.state = GameState.CHOOSEPLAYER;
            }

        } else if (e.getSource() == placearmypanel.next) {
            System.out.println("game Start!");
            game.state = GameState.CHOOSECARD;
            SetActivePanel(reinforcepanel);
            simpanel.enable();
            //if the first player is computer, auto play the game
            Runnable gameStart = () -> {
                game.gameStart();
            };
            Thread thread = new Thread(gameStart);
            thread.start();

        } else if (e.getSource() == placearmypanel.back) {
            game.state = GameState.ASSIGNROLES;
            SetActivePanel(assignRolesPanel);


        } else if (e.getSource() == reinforcepanel.back) {
            game.state = GameState.STARTUP;
            SetActivePanel(placearmypanel);
            simpanel.disable();

        } else if (e.getSource() == simpanel.reinforcebutton) {

            game.state = GameState.REINFORCE;
            game.reinforce();

        } else if (e.getSource() == reinforcepanel.next) {
            game.state = GameState.CHOOSECARD;
            System.out.println("next player");

        } else if (e.getSource() == simpanel.movebutton) {
            game.state = GameState.FORTIFY;
            System.out.println("fortify");


        } else if (e.getSource() == assignRolesPanel.button1) //set
        {
            assignRolesPanel.label.setText(game.getCurPlayer().getName());
            String strategyName = assignRolesPanel.combo.getSelectedItem().toString();
            System.out.println("strategyName ;" + strategyName);
            int playerID = game.getCurPlayer().getID();
            System.out.println("playerID ;" + playerID);
            game.setPlayerStrategy(playerID, strategyName);

            game.getNextPlayer();
            assignRolesPanel.label.setText(game.getCurPlayer().getName());
        } else if (e.getSource() == assignTourRolesPanel.button1) //set
        {
            assignTourRolesPanel.label.setText(game.getCurPlayer().getName());
            String strategyName = assignTourRolesPanel.combo.getSelectedItem().toString();
            System.out.println("strategyName ;" + strategyName);
            int playerID = game.getCurPlayer().getID();
            System.out.println("playerID ;" + playerID);
            game.setPlayerStrategy(playerID, strategyName);

            game.getNextPlayer();
            assignTourRolesPanel.label.setText(game.getCurPlayer().getName());
        } else if (e.getSource() == reinforcepanel.button) {
            game.state = GameState.CHOOSECARD;
            Runnable nextPlayer = () -> {
                game.turnToNextPlayer();
            };
            Thread thread = new Thread(nextPlayer);
            thread.start();
            reinforcepanel.label.setText(game.getCurPlayer().getName());
            System.out.println("next player");

        } else if (e.getSource() == simpanel.attackbutton) {
            game.state = GameState.ATTACK;

            System.out.println("attack");
            log.add("attack phase");
        } else if (e.getSource() == simpanel.choosecardbutton) {
            game.state = GameState.CHOOSECARD;

        }
    }

    /**
     * @Descpription: this method is to add action listener into panel.
     * @Params: action Listener
     * @Date: 2019/3/26
     */
    public void AddActionListener(ActionListener e) {
        chooseModePanel.AddActionListener(e);
        tournamentPanel.AddActionListener(e);
        chooseModePanel.Tournament_Mode.addActionListener(e);
        assignplayerpanel.AddActionListener(e);
        assignRolesPanel.AddActionListener(e);
        editpanel.AddActionListener(e);
        placearmypanel.AddActionListener(e);
        reinforcepanel.AddActionListener(e);
        simpanel.reinforcebutton.addActionListener(e);
        simpanel.movebutton.addActionListener(e);
        simpanel.attackbutton.addActionListener(e);
        simpanel.choosecardbutton.addActionListener(e);
        simpanel.savebutton.addActionListener(e);
        reinforcepanel.button.addActionListener(e);
        assignRolesPanel.button1.addActionListener(e);
        assignTourRolesPanel.button1.addActionListener(e);
        assignTourRolesPanel.AddActionListener(e);
        startTournamentPanel.AddActionListener(e);
        autoPlayPanel.AddActionListener(e);
        selectMapNumPanel.AddActionListener(e);
        selectMapPanel.AddActionListener(e);
        selectMapPanel.select.addActionListener(e);
    }

}
