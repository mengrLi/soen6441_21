package View;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;



public class SimPanel {
	GridLayout grid1;
	//public  JButton loadsim;
//	JButton savesim;
	public JButton movebutton;

	public  JButton reinforcebutton;
	public JButton attackbutton;
	public JButton choosecardbutton;
	public JPanel mainpanel;


	public SimPanel() {
		movebutton = new JButton("Move");
//		turnbutton = new JButton("turn");
//		fullbutton = new JButton("full");
		reinforcebutton = new JButton("Reinforce");
		attackbutton = new JButton("Attack");
		choosecardbutton = new JButton("Choose Card");


		grid1 = new GridLayout(1, 3);

//
		JPanel center = new JPanel();
		center.add(choosecardbutton);
		center.add(reinforcebutton);
		center.add(attackbutton);
		center.add(movebutton);


		mainpanel = new JPanel(grid1);
//		mainpanel.add(left);
		mainpanel.add(center);
//		mainpanel.add(panel);
		Color test = Theme.color;
//		panel.setBackground(test);
		mainpanel.setBackground(test);
		center.setBackground(test);
//		left.setBackground(test);
	}
	public JPanel getComponent() {
		return mainpanel;
	}
	public void enable() {
		mainpanel.setVisible(true);
	}
	public void disable() {
		mainpanel.setVisible(false);
	}
}
