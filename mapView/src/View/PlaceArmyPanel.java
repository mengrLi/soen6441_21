package View;

import javax.swing.JLabel;
import javax.swing.JTextArea;



public class PlaceArmyPanel extends aPanel {

	public JLabel label1;
	public JTextArea label2;
	public PlaceArmyPanel() {
	label1 = new JLabel("Resource = , Tech = ");
	label2 = new JTextArea("States = , Continents = ");

	label2.setEditable(false);
	//label2.setLineWrap(true);
	label2.setSelectedTextColor(theme);
	label2.setBackground(theme);
	right.remove(next);
	left.remove(back);
		left.add(label1);
		right.add(label2);
		label.setText("");
		

	}

}
