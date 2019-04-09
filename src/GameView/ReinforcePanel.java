package GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import GameModel.PlayerEngine;
import MapView.aPanel;

/**
 * This class is match reinforcement state, design a panel let user easy to add bonus army
 *
 * @author mengranli
 */
public class ReinforcePanel extends aPanel implements ActionListener {
    // JButton auto;
    //public  JTextField textfield;
    public JButton button;
    // public JLabel label;
    PlayerEngine game = new PlayerEngine();

    public ReinforcePanel() {

        button = new JButton("next");

        center.add(button);
        //textfield.addActionListener(this);

    }

    /**
     * this method perform buttons action in this popup menu
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("next".equals((e.getActionCommand()))) {
            label.setText(game.getCurPlayer().getName());
            System.out.println("field text is :" + label.getText());
        }

    }
}
