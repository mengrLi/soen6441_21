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
	public JButton phasebutton;
	public JButton turnbutton;
	public JButton fullbutton;
	public  JButton auto;
	public JPanel mainpanel;
	public JComboBox combo;
	public JComboBox combo2;
	
	public SimPanel() {
		phasebutton = new JButton("phase");
		turnbutton = new JButton("turn");
		fullbutton = new JButton("full");
		auto = new JButton("auto");
	//	loadsim = new JButton("Load Sim");
	//	savesim = new JButton("Save Sim");
		grid1 = new GridLayout(1, 3);
		JPanel panel = new JPanel();
		panel.add(phasebutton);
		panel.add(turnbutton);
		panel.add(fullbutton);
		panel.add(auto);
		
		
		JPanel left = new JPanel();
		//left.add(loadsim);
	//	left.add(savesim);
		
		
		JPanel center = new JPanel();
		String str[] = {"Default", "Level 0", "Level 1", "Level 2", "Level 3"};
		combo = new JComboBox(str);
		String str2[] = {"Level 1", "Level 2", "Level 3"};
		combo2 = new JComboBox(str2);
		center.add(combo);
		center.add(combo2);
		
		mainpanel = new JPanel(grid1);
		mainpanel.add(left);
		mainpanel.add(center);
		mainpanel.add(panel);
		Color test = Theme.color;
		panel.setBackground(test);
		mainpanel.setBackground(test);
		center.setBackground(test);
		left.setBackground(test);
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
