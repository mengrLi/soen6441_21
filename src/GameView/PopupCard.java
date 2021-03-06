package GameView;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import GameModel.PlayerEngine;
import MapModel.Map;
import MapView.Theme;
import MapController.Canvas;
import MapView.aPopupMenu;
/**
 * This class is a view match choose card state
 * for each country belong to player, click to show popup menu and choose card options
 * @author mengranli
 */
public class PopupCard extends aPopupMenu implements ActionListener {


    DefaultListModel listModel = new DefaultListModel();
    JList cardoptionList = new JList(listModel);
    JScrollPane scrollpane = new JScrollPane(cardoptionList);
    public JTextField card1_num; //card 1 number
    public JTextField card2_num; // card 2 number
    public JTextField card3_num;// card 3 number
    public JButton selectcardbutton;
    JPopupMenu popup;
    JPanel popuppanel;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    private Map map;
    private Canvas canvas;
    private PlayerEngine game = new PlayerEngine();

    /**
     * Constructor
     * This method is popup menu view design
     * @param map    our map
     * @param canvas the canvas
     */
    public PopupCard(Map map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;

        // = = = = = Edit State Pop-up Menu = = = = = = =
        popup = new JPopupMenu();
        popuppanel = new JPanel();

        selectcardbutton = new JButton("Select");
        selectcardbutton.setBackground(Theme.color);
        popup.setBackground(Theme.color);
        scrollpane.setBackground(Theme.color);
        cardoptionList.setBackground(Theme.color);
        label1 = new JLabel("Infantry: ");
        label2 = new JLabel("Cavalry: ");
        label3 = new JLabel("Artillery: ");
        card1_num = new JTextField();
        card1_num.setSize(50, 15);
        card2_num = new JTextField();
        card2_num.setSize(50, 15);
        card3_num = new JTextField();
        card3_num.setSize(50, 15);

        scrollpane.setWheelScrollingEnabled(true);
        GridLayout grid = new GridLayout(1, 2);
        GridLayout grid2 = new GridLayout(1, 3);
        JPanel panel = new JPanel();
        panel.setLayout(grid2);
        panel.setBackground(Theme.color);
        popuppanel.setLayout(grid);

        popuppanel.add(label1);
        card1_num.setText("0");
        popuppanel.add(card1_num);
        popuppanel.add(label2);
        card2_num.setText("0");
        popuppanel.add(card2_num);
        popuppanel.add(label3);
        card3_num.setText("0");
        popuppanel.add(card3_num);

        panel.add(selectcardbutton);
        popuppanel.setBackground(Theme.color);
        popup.add(popuppanel);
        popup.add(panel);
        popup.add(scrollpane);

        selectcardbutton.addActionListener(this);


    }

    /**
     * Refreshes the continent list
     */
    public void refreshList() {
        listModel.clear();
        listModel.addElement("Card options:");
        for (String option : game.getCurPlayer().chooseExchangeWay()) {
            listModel.addElement(option);
        }
    }

    /**
     * Displays the pop up menu at the given coordinates
     * @param c component
     * @param x coordinates
     * @param y coordinates
     */
    public void show(Component c, int x, int y) {
        refreshList();
        popup.show(c, x, y);
    }

    /**
     * this method perform buttons action in this popup menu
     *
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectcardbutton) {
            String options = cardoptionList.getSelectedValue().toString();
            System.out.println(options);
            if (options != null) {
                game.getCurPlayer().ExchangeCardforArmy(options);
                game.cardChangeFlage = true;

                int card1 = game.getCurPlayer().getcardTypeNumber().get("Infantry") != null ? game.getCurPlayer().getcardTypeNumber().get("Infantry") : 0;
                int card2 = game.getCurPlayer().getcardTypeNumber().get("Cavalry") != null ? game.getCurPlayer().getcardTypeNumber().get("Cavalry") : 0;
                int card3 = game.getCurPlayer().getcardTypeNumber().get("Artillery") != null ? game.getCurPlayer().getcardTypeNumber().get("Artillery") : 0;
                card1_num.setText(String.valueOf(card1));
                card2_num.setText(String.valueOf(card2));
                card3_num.setText(String.valueOf(card3));
                refreshList();
                canvas.repaint();
            }
        }
    }

}
