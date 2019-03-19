package Popups;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Canvas;
import model.*;

public class PopupAttack extends aPopupMenu implements ActionListener{

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
    //  JLabel label;
    // public JComboBox combo;

    private Map map;
    private Canvas canvas;
    private GameEngine game = new GameEngine();
    /**
     * Constructor
     * @param map our map
     * @param canvas the canvas
     */
    public PopupAttack(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;

        // = = = = = Edit State Pop-up Menu = = = = = = =
        popup = new JPopupMenu();
        popuppanel = new JPanel();
        //  String data[] = {"1", "2", "3", "ALL"};
        //  label = new JLabel("Choose the dice number: ");
        //  combo = new JComboBox(data);


        alldicebutton = new JButton("ALL Dice");
        onedicebutton=new JButton("ONE Dice");
        twodicebutton=new JButton("TWO Dice");
        threedicebutton=new JButton("THREE Dice");
        countryname = new JTextField();
        playername=new JTextField();
        armynumber=new JTextField();
        countrypercent=new JTextField();

        alldicebutton.setBackground(Theme.color);
        onedicebutton.setBackground(Theme.color);
        threedicebutton.setBackground(Theme.color);
        twodicebutton.setBackground(Theme.color);

        popup.setBackground(Theme.color);
        scrollpane.setBackground(Theme.color);
        //continentlist.setBackground(Theme.color);
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



        //   panel.add(label);
        // panel.add(combo);
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
     */
    public void refreshList(Country country) {
        listModel.clear();
//      //  System.out.println("getcontiguousBelongThisPlayer : "+country.getDefendersAroundThisCountry() );
        listModel.addElement("Choose country to attack ");
        for(int i = 0; i < country.getDefendersAroundThisCountry().size(); i++) {
            String cont = country.getDefendersAroundThisCountry().get(i);
            listModel.addElement(cont);
        }
    }
    /**
     * Displays the pop up menu at the given coordinates
     * @param c component
     * @param x coordinates
     * @param y coordinates
     */
    public void show(Component c, int x, int y) {
        // refreshList(Country);
        popup.show(c, x, y);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == threedicebutton) {
            //refreshList();
            Country definectry = map.getCountry(attacklist.getSelectedValue().toString());
            Country attackctry = map.getCountry(canvas.selectedIndex);
            game.diceThree(attackctry,definectry);
            countrypercent.setText(game.percentageOfmap(attackctry.getPlayer()));
            canvas.repaint();
        }else if(e.getSource() == onedicebutton) {
            //refreshList();
            Country definectry = map.getCountry(attacklist.getSelectedValue().toString());
            Country attackctry = map.getCountry(canvas.selectedIndex);
            game.diceOne(attackctry,definectry);
            countrypercent.setText(game.percentageOfmap(attackctry.getPlayer()));
            canvas.repaint();
        }else if(e.getSource() == twodicebutton) {
            //refreshList();
            Country definectry = map.getCountry(attacklist.getSelectedValue().toString());
            Country attackctry = map.getCountry(canvas.selectedIndex);
            game.diceTwo(attackctry,definectry);
            countrypercent.setText(game.percentageOfmap(attackctry.getPlayer()));
            canvas.repaint();
        }

//        if(e.getSource() == setcontinentbutton) {
//            Country destination = map.getCountry(continentlist.getSelectedValue().toString());
//            Country originctn = map.getCountry(canvas.selectedIndex);
//            Player curPlayer = game.getCurPlayer();
//            game.moveArmyBetweenCountries(1,curPlayer, destination, originctn);
//            canvas.repaint();
//
//        }
//    }
    }


}
