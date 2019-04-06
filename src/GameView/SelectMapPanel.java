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
public class SelectMapPanel extends aPanel implements ActionListener {
    public JButton select;
    // public JButton button2;
    PlayerEngine game = new PlayerEngine();
//    public JLabel label;
//    public JButton set;
    public SelectMapPanel()
    {
       // String data[] = {"Human","Cheater","Aggressive","Benevolent","RandomP"};
//        label = new JLabel("Map 0");


     //   set= new JButton("Set");
        select = new JButton("Select Map");
        //  button2=new JButton("Next");

        // center.add(button2);
        center.add(label);
        center.add(select);
     //   center.add(set);


    }


    @Override
    public void actionPerformed(ActionEvent e) {



    }
}
