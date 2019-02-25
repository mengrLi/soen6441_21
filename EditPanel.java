package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;

public class EditPanel extends Panel{
	public JButton clear;
	public JButton save;
	public JButton exit;
	public EditPanel() {
		clear = new JButton("Clear");
		save = new JButton("Save");
		exit = new JButton("Exit");
		center.remove(label);
		center.add(clear);
		center.add(save);
		center.add(exit);
	}
	public void ActionPerformed(Object obj) {
		if(obj == clear) {
			
		}
	}
	public void AddActionListener(ActionListener e) {
		super.AddActionListener(e);
		clear.addActionListener(e);
		save.addActionListener(e);
		exit.addActionListener(e);
	}
	

}
