package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.GameEngine;

public class ReinforcePanel extends aPanel implements ActionListener {
	// JButton auto;
	//public  JTextField textfield;
	public JButton button;
	// public JLabel label;
	GameEngine game = new GameEngine();

	public ReinforcePanel() {


		center.add(label);
		label.setText("Player 0 ");

		//      textfield = new JTextField();
		button =new JButton("next");
//  textfield.setSize(50,60);
//  textfield.setText("Player0");
		// center.add(auto,BorderLayoutder.)
		//   center.setSize(50,60);
		//center.add(label);
		// label.setText("Player 0");

		center.add(button);
		//textfield.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if("next".equals((e.getActionCommand())))
		{
			label.setText(game.getCurPlayer().getName());
			System.out.println("field text is :"+label.getText());
		}

	}
}