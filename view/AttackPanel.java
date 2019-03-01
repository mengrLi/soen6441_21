package View;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;

public class AttackPanel extends aPanel{
	JButton auto;

	public AttackPanel() {
		
		auto = new JButton(">>>");
		right.add(auto, BorderLayout.SOUTH);
		center.add(label);
		label.setText("Attack");

	}

}
