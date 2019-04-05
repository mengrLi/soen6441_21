package MapView;


import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * This is the panel match with edit map state
 * show the canvas and log windows
 */
public class EditPanel extends aPanel {
    public JButton load;
    public JButton clear;
    public JButton save;
    public JButton loadGame;

    public EditPanel() {
        load = new JButton("Load");
        clear = new JButton("Clear");
        save = new JButton("Save");
        loadGame = new JButton("loadGame");
        center.remove(label);
        center.add(loadGame);
        center.add(load);
        center.add(clear);
        center.add(save);

    }
    /**
     * this method perform buttons action in this popup menu
     *
     * @param e
     */
    public void AddActionListener(ActionListener e) {
        super.AddActionListener(e);
        load.addActionListener(e);
        clear.addActionListener(e);
        save.addActionListener(e);
        loadGame.addActionListener(e);
    }
}
