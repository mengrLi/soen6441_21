package Risk;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import MapController.Canvas;
import MapModel.Map;
import GameController.PanelController;
import GameModel.PlayerEngine;

/**
 * This is the main Class and function
 *
 * @author xiaoyunliao, mengranli
 */
public class RISK extends JFrame implements ActionListener {
    private static final long serialVersionUID = 707427497898546563L;
    public static final int WINDOW_WIDTH = 1100;
    public static final int WINDOW_HEIGHT = 750;
    public static PlayerEngine game = new PlayerEngine();
    public static PanelController panelcontroller = new PanelController(game);
    Canvas canvas = new Canvas(game, panelcontroller);


    public static void main(String[] args) {
        game.log = panelcontroller.log;

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
        panelcontroller.simpanel.disable(); //
        add(panelcontroller.mainpanel, BorderLayout.SOUTH);//


        Map.getMapInstance().addObserver(canvas);

        panelcontroller.AddActionListener(this);

    }

    /**
     * Invoking relevant methods when a certain action performed
     */
    public void actionPerformed(ActionEvent e) {
        panelcontroller.ActionPerformed(e);
        //repaint();
    }


}