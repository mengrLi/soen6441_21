package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import View.*;

public final class AssignPlayerPanel extends aPanel implements ActionListener{
	JLabel label;
	public JComboBox combo;
	
	public AssignPlayerPanel() {
		//this.game = game;
		String data[] = {"2", "3", "4", "5"};
		label = new JLabel("Choose Number of Players: ");
		combo = new JComboBox(data);
		center.add(label);
		center.add(combo);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
