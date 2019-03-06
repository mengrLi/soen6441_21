package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

import model.Map;

public class EditPanel extends aPanel {
	public JButton load;
	public JButton clear;
	public JButton save;
	public EditPanel() {
		load = new JButton("Load");
		clear = new JButton("Clear");
		save = new JButton("Save");
		center.remove(label);
		center.add(load);
		center.add(clear);
		center.add(save);
		//back.setText("RISK!");
		
	}
	public void ActionPerformed(Object obj) {
		if(obj == clear) {
			
			
		}
	}
	
	public void AddActionListener(ActionListener e) {
		super.AddActionListener(e);
		load.addActionListener(e);
		clear.addActionListener(e);
		save.addActionListener(e);
	}
}
