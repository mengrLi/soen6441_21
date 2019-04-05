package GameView;

import MapView.Theme;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * This class is a additional panel show to let user change game state,
 * when finish current state, choose next state button
 *
 * @author mengranli
 */
public class SimPanel {
    GridLayout grid1;
    //public  JButton loadsim;
//	JButton savesim;
    public JButton movebutton;

    public JButton reinforcebutton;
    public JButton attackbutton;
    public JButton choosecardbutton;
    public JButton savebutton;
    public JPanel mainpanel;


    public SimPanel() {
        movebutton = new JButton("Move");
        reinforcebutton = new JButton("Reinforce");
        attackbutton = new JButton("Attack");
        choosecardbutton = new JButton("Choose Card");
        savebutton=new JButton("SaveGame");
        grid1 = new GridLayout(1, 3);
        JPanel center = new JPanel();
        center.add(choosecardbutton);
        center.add(reinforcebutton);
        center.add(attackbutton);
        center.add(movebutton);
        center.add(savebutton);
        mainpanel = new JPanel(grid1);
        mainpanel.add(center);
        Color test = Theme.color;
        mainpanel.setBackground(test);
        center.setBackground(test);
    }

    public JPanel getComponent() {
        return mainpanel;
    }

    public void enable() {
        mainpanel.setVisible(true);
    }

    public void disable() {
        mainpanel.setVisible(false);
    }
}
