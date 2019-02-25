package control;
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

import view.PopupMenu;
import models.Map;
import models.Continent;
import view.Theme;
import control.MapEditor;
public class PopupEdit extends PopupMenu implements ActionListener {
	
	DefaultListModel listModel = new DefaultListModel();
	JList continentlist = new JList(listModel);
	JScrollPane scrollpane = new JScrollPane(continentlist);
	public JTextField textfield1;
	public JTextField textfield2;
	public JButton addcontinentbutton;
	public JButton setcontinentbutton;
	public JButton deletecontinentbutton;
	JPopupMenu popup; 
	JPanel popuppanel;
	public Map map;
	public MapEditor mapeditor;
	
	public PopupEdit(Map map, MapEditor mapeditor) {
		this.map = map;
		this.mapeditor = mapeditor;

		// = = = = = Edit State Pop-up Menu = = = = = = = 
		popup = new JPopupMenu();
		popuppanel = new JPanel();
		addcontinentbutton = new JButton("+");
		deletecontinentbutton = new JButton("-");
		setcontinentbutton = new JButton("Set");
		textfield1 = new JTextField();
		textfield2 = new JTextField();
		addcontinentbutton.setBackground(Theme.color);
		setcontinentbutton.setBackground(Theme.color);

		popup.setBackground(Theme.color);
		scrollpane.setBackground(Theme.color);
		continentlist.setBackground(Theme.color);
		textfield1.setSize(30, 10);
		scrollpane.setWheelScrollingEnabled(true);
		GridLayout grid = new GridLayout(1, 2);
		GridLayout grid2 = new GridLayout(1, 3);
		JPanel panel = new JPanel();
		panel.setLayout(grid2);
		panel.setBackground(Theme.color);
		popuppanel.setLayout(grid);
		textfield1.setText("Continent");
		popuppanel.add(textfield1);
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
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == addcontinentbutton) {
			if(map.getContinentNum()<=6)
			//todo
				//map.addContinent(continentlist.getSelectedIndex());
				refreshList();
		}

		else if(e.getSource() == setcontinentbutton) {
		// map
			// map.SetContinent(mapeditor.selectedIndex, continentlist.getSelectedIndex());
			map.country[mapeditor.selectedIndex].name = textfield2.getText();
			mapeditor.repaint();

		}
		
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
	public void show(Component c, int x, int y) {
		refreshList();
		popup.show(c, x, y);
	}

}
