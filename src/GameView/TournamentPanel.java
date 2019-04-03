package GameView;

import MapView.aPanel;

import javax.swing.*;

import java.awt.event.ActionListener;

/**
 * @program: 6441-build3
 * @description:
 * @author:
 * @create: 2019-04-03
 **/
public class TournamentPanel extends aPanel {

    public JButton selectMap;
    public JLabel chooseplayernum;
    public JComboBox playercombo;
    public JLabel chooseturns;
    public JComboBox turncombo;

    public TournamentPanel() {
        selectMap = new JButton("Select Map");
       // clear = new JButton("Clear");
        //save = new JButton("Save");
        //loadGame = new JButton("loadGame");
        center.remove(label);
        left.add(selectMap);
        String data[] = {"2", "3", "4", "5"};
        chooseplayernum = new JLabel("Choose Players: ");
        playercombo = new JComboBox(data);
        center.add(chooseplayernum);
        center.add(playercombo);
        String turnsdata[]={"10","20","30","40","50"};
        chooseturns = new JLabel("Choose Turns: ");
        turncombo = new JComboBox(turnsdata);
        center.add(chooseturns);
        center.add(turncombo);

    }

    /**
     * this method perform buttons action in this popup menu
     *
     * @param e
     */
    public void AddActionListener(ActionListener e) {
        super.AddActionListener(e);
        selectMap.addActionListener(e);

    }


}
