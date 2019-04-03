package MapView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @program: 6441-build3
 * @description:
 * @author:
 * @create: 2019-04-03
 **/
public class ChooseModePanel extends aPanel implements ActionListener {
    public JButton Tournament_Mode;
    public JButton Single_Game_Mode;



    public ChooseModePanel() {
        Tournament_Mode = new JButton("Tournament_Mode");
        Single_Game_Mode = new JButton("Single_Game_Mode");


        center.add(Tournament_Mode);
        center.add(Single_Game_Mode);


    }
    /**
     * this method perform buttons action in this popup menu
     *
     * @param e
     */
    public void AddActionListener(ActionListener e) {
        Tournament_Mode.addActionListener(e);
        Single_Game_Mode.addActionListener(e);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==Tournament_Mode)
        {

          //  tournamentPanel.SetVisible(true);
        }
    }
}
