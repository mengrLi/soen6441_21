package Popups;

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

import risk.Map;
import risk.Canvas;
import risk.Continent;
import risk.Theme;
/**
 * This class creates a pop-up menu to to edit continents in the map
 * @author aliahadi
 *
 */
public class PopupEdit extends aPopupMenu implements ActionListener  {


	DefaultListModel listModel = new DefaultListModel();
	JList continentlist = new JList(listModel);
	JScrollPane scrollpane = new JScrollPane(continentlist);
	public JTextField textfield;
	public JTextField textfield2;
	public JButton addcontinentbutton;
	public JButton setcontinentbutton;
	public JButton deletecontinentbutton;
	JPopupMenu popup; 
	JPanel popuppanel;

	private Map map;
	private Canvas canvas;
	/**
	 * Constructor
	 * @param map our map
	 * @param canvas the canvas
	 */
	public PopupEdit(Map map, Canvas canvas) {
		this.map = map;
		this.canvas = canvas;

		// = = = = = Edit State Pop-up Menu = = = = = = = 
		popup = new JPopupMenu();
		popuppanel = new JPanel();
		addcontinentbutton = new JButton("+");
		deletecontinentbutton = new JButton("-");
		setcontinentbutton = new JButton("Set");
		textfield = new JTextField();
		textfield2 = new JTextField();
		addcontinentbutton.setBackground(Theme.color);
		setcontinentbutton.setBackground(Theme.color);

		popup.setBackground(Theme.color);
		scrollpane.setBackground(Theme.color);
		continentlist.setBackground(Theme.color);
		textfield.setSize(30, 10);
		scrollpane.setWheelScrollingEnabled(true);
		GridLayout grid = new GridLayout(1, 2);
		GridLayout grid2 = new GridLayout(1, 3);
		JPanel panel = new JPanel();
		panel.setLayout(grid2);
		panel.setBackground(Theme.color);
		popuppanel.setLayout(grid);
		textfield.setText("Continent");
		popuppanel.add(textfield);
		popuppanel.add(textfield2);
		textfield2.setText("State Name");
		panel.add(addcontinentbutton);
		panel.add(setcontinentbutton);
		panel.add(deletecontinentbutton);
		popuppanel.setBackground(Theme.color);
		popup.add(popuppanel);
		popup.add(panel);
		popup.add(scrollpane);

		addcontinentbutton.addActionListener(this);
		setcontinentbutton.addActionListener(this);

	}
	/**
	 * Refreshes the continent list
	 */
	private void refreshList() {
		listModel.clear();
		for(int i = 0; i < map.continents.size(); i++) {
			Continent cont = map.continents.get(i);
			listModel.addElement(cont.name);
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
		if(e.getSource() == addcontinentbutton) {
			if(map.AddContinent())
				refreshList();
		}

		else if(e.getSource() == setcontinentbutton) {
			map.SetContinent(canvas.selectedIndex, continentlist.getSelectedIndex());
			map.state[canvas.selectedIndex].name = textfield2.getText();
			canvas.repaint();

		}
	}

}
