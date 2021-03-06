package GameView;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import GameModel.Country;
import GameModel.PlayerEngine;
import GameModel.Player;
import MapModel.Map;
import MapView.Theme;
import MapController.Canvas;
import MapView.aPopupMenu;

/**
 * This class creates a pop-up menu to to move army in the map
 */
public class PopupMove extends aPopupMenu implements ActionListener {


    DefaultListModel listModel = new DefaultListModel();
    JList continentlist = new JList(listModel);
    JScrollPane scrollpane = new JScrollPane(continentlist);
    public JTextField textfield1;
    public JButton setcontinentbutton;
    JPopupMenu popup;
    JPanel popuppanel;

    private Map map;
    private Canvas canvas;
    private PlayerEngine game = new PlayerEngine();

    /**
     * Constructor
     *
     * @param map    our map
     * @param canvas the canvas
     */
    public PopupMove(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;

        // = = = = = Edit State Pop-up Menu = = = = = = =
        popup = new JPopupMenu();
        popuppanel = new JPanel();

        setcontinentbutton = new JButton("Set");
        textfield1 = new JTextField();
        setcontinentbutton.setBackground(Theme.color);

        popup.setBackground(Theme.color);
        scrollpane.setBackground(Theme.color);
        continentlist.setBackground(Theme.color);
        textfield1.setSize(50, 15);
        scrollpane.setWheelScrollingEnabled(true);
        GridLayout grid = new GridLayout(1, 2);
        GridLayout grid2 = new GridLayout(1, 3);
        JPanel panel = new JPanel();
        panel.setLayout(grid2);
        panel.setBackground(Theme.color);
        popuppanel.setLayout(grid);
        textfield1.setText("From country");
        popuppanel.add(textfield1);

        panel.add(setcontinentbutton);
        popuppanel.setBackground(Theme.color);
        popup.add(popuppanel);
        popup.add(panel);
        popup.add(scrollpane);

        setcontinentbutton.addActionListener(this);

    }

    /**
     * Refreshes the continent list
     */
    public void refreshList(Country country) {
        listModel.clear();
        listModel.addElement("to Country");
        for (int i = 0; i < country.getcontiguousBelongThisPlayer().size(); i++) {
            String cont = country.getcontiguousBelongThisPlayer().get(i);
            listModel.addElement(cont);
        }
    }

    /**
     * Displays the pop up menu at the given coordinates
     *
     * @param c component
     * @param x coordinates
     * @param y coordinates
     */
    public void show(Component c, int x, int y) {
        popup.show(c, x, y);
    }

    /**
     * this method perform buttons action in this popup menu
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == setcontinentbutton) {
            Country destination = map.getCountry(continentlist.getSelectedValue().toString());
            Country originctn = map.getCountry(canvas.selectedIndex);
            Player curPlayer = game.getCurPlayer();
            game.moveArmyBetweenCountries(1, curPlayer, destination, originctn);
            canvas.repaint();
        }
    }

}
