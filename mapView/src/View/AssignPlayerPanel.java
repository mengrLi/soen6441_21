package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public final class AssignPlayerPanel extends aPanel implements ActionListener{
	JLabel label;
	JComboBox combo;
	
	public AssignPlayerPanel() {
		//this.game = game;
		String data[] = {"2", "3", "4", "5"};
		label = new JLabel("Choose Number of Players: ");
		combo = new JComboBox(data);
		center.add(label);
		center.add(combo);
	}

	
	//@Override
	public void actionPerformed(ActionEvent e) {
//		if(e.getSource() == next) {
//			for(int i = 0; i < combo.getSelectedIndex()+2; i++)
//				game.AddANewPlayer();
//			
//			game.mode = 2;
//			game.AssignPlayers();
//			
//			// = = = GAME LOOP = = =
//			game.ReInforce(game.player.get(0));
//			String str = "You Have " + game.player.get(0).troops + " " + game.player.get(0).armytype.getName();
//			if(game.player.get(0).troops > 1) str += "s";
//		}
//		else if(e.getSource() == back) {
//
//		}		
	}

}
