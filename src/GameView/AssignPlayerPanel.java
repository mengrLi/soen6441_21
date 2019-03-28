package GameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import MapView.aPanel;

/**
 * This class is match choose player state, design a panel let user easy to choose player numbers
 * player number is from 2 to 5
 * using a list to show
 *
 * @author mengranli
 */
public final class AssignPlayerPanel extends aPanel implements ActionListener {
    JLabel label;
    public JComboBox combo;

    public AssignPlayerPanel() {
        //this.game = game;
        String data[] = {"2", "3", "4", "5"};
        label = new JLabel("Choose Number of Players: ");
        combo = new JComboBox(data);
        center.add(label);
        center.add(combo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }
}
