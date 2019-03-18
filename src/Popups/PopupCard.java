package Popups;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import model.Canvas;
import model.*;
public class PopupCard extends aPopupMenu implements ActionListener {


    DefaultListModel listModel = new DefaultListModel();
    JList cardoptionList = new JList(listModel);
    JScrollPane scrollpane = new JScrollPane(cardoptionList);
    public JTextField card1_num; //card 1 number
    public JTextField card2_num; // card 2 number
    public JTextField card3_num;// card 3 number

    //public JButton addcontinentbutton;
    public JButton selectcardbutton;
    //	public JButton deletecontinentbutton;
    JPopupMenu popup;
    JPanel popuppanel;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    private Map map;
    private Canvas canvas;
    private GameEngine game = new GameEngine();
    /**
     * Constructor
     * @param map our map
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
        label1 = new JLabel("Card1: ");
        label2 = new JLabel("Card2: ");
        label3 = new JLabel("Card3: ");
        card1_num = new JTextField();
        card1_num.setSize(50, 15);
        card2_num =new JTextField();
        card2_num.setSize(50, 15);
        card3_num =new JTextField();
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
     //   System.out.println("getcontiguousBelongThisPlayer : "+country.getcontiguousBelongThisPlayer() );
        listModel.addElement("Card options:");
        for(int i = 0; i < game.getCurPlayer().chooseExchangeWay().size(); i++) {
            String cont = game.getCurPlayer().chooseExchangeWay().get(i);
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
        refreshList();
        popup.show(c, x, y);
    }

    public void actionPerformed(ActionEvent e) {
		if(e.getSource() == selectcardbutton) {
          //  refreshList();
            String options= cardoptionList.getSelectedValue().toString();
            System.out.println(options);
            game.getCurPlayer().ExchangeCardforArmy(options);

         //   System.out.print(cardoptionList.getSelectedValue().toString());
//            Country destination = map.getCountry(continentlist.getSelectedValue().toString());
//            Country originctn = map.getCountry(canvas.selectedIndex);
//            Player curPlayer = game.getCurPlayer();
//            game.moveArmyBetweenCountries(1,curPlayer, destination, originctn);
              canvas.repaint();

        }
    }

}
