package GameView;

import GameModel.PlayerEngine;
import MapView.aPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: 6441-build3
 * @description:
 * @author:
 * @create: 2019-04-02
 **/
public class AssignTourRolesPanel extends aPanel implements ActionListener {
    public JButton button1;
    // public JButton button2;
    PlayerEngine game = new PlayerEngine();
    public JLabel label;
    public JComboBox combo;
    public AssignTourRolesPanel()
    {
        String data[] = {"Cheater","Aggressive","Benevolent","RandomP"};
        label = new JLabel("Player 0");
        combo = new JComboBox(data);

        button1= new JButton("Set");
        //  button2=new JButton("Next");

        // center.add(button2);
        center.add(label);
        center.add(combo);
        center.add(button1);


    }


    @Override
    public void actionPerformed(ActionEvent e) {


    }
}
