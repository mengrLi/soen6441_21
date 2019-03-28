package GameView;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import GameModel.Country;
import GameModel.PlayerEngine;
import MapModel.Map;
import MapView.Theme;
import MapController.Canvas;
import MapView.aPopupMenu;

/**
 * This class is a view match attack state
 * for each country belong to player, click to show popup menu and choose country to attack
 * attack function contains 1 dice, 2 dices, 3 dices , and all army
 *
 * @author mengranli
 */
public class PopupAttack extends aPopupMenu implements ActionListener {

    DefaultListModel listModel = new DefaultListModel();
    JList attacklist = new JList(listModel);
    JScrollPane scrollpane = new JScrollPane(attacklist);
    public JTextField countryname; //country
    public JTextField playername; //player
    public JTextField armynumber;//army
    public JTextField countrypercent;

    public JButton alldicebutton;
    public JButton onedicebutton;
    public JButton twodicebutton;
    public JButton threedicebutton;


    JPopupMenu popup;
    JPanel popuppanel;

    private Map map;
    private Canvas canvas;
    private PlayerEngine game = new PlayerEngine();

    /**
     * Constructor
     * This method is popup menu view design
     *
     * @param map    our map
     * @param canvas the canvas
     */
    public PopupAttack(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;

        // = = = = = Edit State Pop-up Menu = = = = = = =
        popup = new JPopupMenu();
        popuppanel = new JPanel();
        alldicebutton = new JButton("ALL Dice");
        onedicebutton = new JButton("ONE Dice");
        twodicebutton = new JButton("TWO Dice");
        threedicebutton = new JButton("THREE Dice");
        countryname = new JTextField();
        playername = new JTextField();
        armynumber = new JTextField();
        countrypercent = new JTextField();

        alldicebutton.setBackground(Theme.color);
        onedicebutton.setBackground(Theme.color);
        threedicebutton.setBackground(Theme.color);
        twodicebutton.setBackground(Theme.color);

        popup.setBackground(Theme.color);
        scrollpane.setBackground(Theme.color);
        countryname.setSize(40, 15);
        playername.setSize(40, 15);
        armynumber.setSize(40, 15);
        countrypercent.setSize(40, 15);

        scrollpane.setWheelScrollingEnabled(true);
        GridLayout grid = new GridLayout(1, 2);
        GridLayout grid2 = new GridLayout(1, 3);
        JPanel panel = new JPanel();
        panel.setLayout(grid2);
        panel.setBackground(Theme.color);
        popuppanel.setLayout(grid);
        countryname.setText("Country name");
        playername.setText("Player name");
        armynumber.setText("Army number");
        countrypercent.setText("country percentage");
        popuppanel.add(countryname);
        popuppanel.add(playername);
        popuppanel.add(armynumber);
        popuppanel.add(countrypercent);

        panel.add(onedicebutton);
        panel.add(twodicebutton);
        panel.add(threedicebutton);
        panel.add(alldicebutton);
        popuppanel.setBackground(Theme.color);
        popup.add(popuppanel);
        popup.add(panel);
        popup.add(scrollpane);

        onedicebutton.addActionListener(this);
        twodicebutton.addActionListener(this);
        threedicebutton.addActionListener(this);
        alldicebutton.addActionListener(this);


    }

    /**
     * Refreshes the continent list
     *
     * @param country
     */
    public void refreshList(Country country) {
        listModel.clear();
        listModel.addElement("Choose country to attack ");
        for (int i = 0; i < country.getDefendersAroundThisCountry().size(); i++) {
            String cont = country.getDefendersAroundThisCountry().get(i);
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
        if (e.getSource() == threedicebutton) {
            //refreshList();
            Country definectry = map.getCountry(attacklist.getSelectedValue().toString());
            Country attackctry = map.getCountry(canvas.selectedIndex);
            game.diceThree(attackctry, definectry);
            countrypercent.setText(game.percentageOfmap(attackctry.getPlayer()));
            refreshList(attackctry);
            canvas.repaint();
        } else if (e.getSource() == onedicebutton) {
            //refreshList();
            Country definectry = map.getCountry(attacklist.getSelectedValue().toString());
            Country attackctry = map.getCountry(canvas.selectedIndex);
            game.diceOne(attackctry, definectry);
            countrypercent.setText(game.percentageOfmap(attackctry.getPlayer()));
            refreshList(attackctry);
            canvas.repaint();
        } else if (e.getSource() == twodicebutton) {
            //refreshList();
            Country definectry = map.getCountry(attacklist.getSelectedValue().toString());
            Country attackctry = map.getCountry(canvas.selectedIndex);
            game.diceTwo(attackctry, definectry);
            countrypercent.setText(game.percentageOfmap(attackctry.getPlayer()));
            refreshList(attackctry);
            canvas.repaint();
        } else if (e.getSource() == alldicebutton) {
            //refreshList();
            Country definectry = map.getCountry(attacklist.getSelectedValue().toString());
            Country attackctry = map.getCountry(canvas.selectedIndex);
            game.diceAll(attackctry, definectry);
            countrypercent.setText(game.percentageOfmap(attackctry.getPlayer()));
            refreshList(attackctry);
            canvas.repaint();
        }

    }


}
