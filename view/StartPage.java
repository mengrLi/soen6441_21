package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class startpage extends JFrame {
	public Image image;

	//private final Action action_1 = new SwingAction_1();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					startpage frame = new startpage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public startpage() {
		setFont(new Font("Apple Braille", Font.PLAIN, 15));
		setTitle("RISK GAME");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 1024,690);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGame = new JMenu("Game");
		menuBar.add(mnGame);
		
		JMenuItem mntmNewMap = new JMenuItem("New Map");
		mnGame.add(mntmNewMap);
		
		JMenuItem mntmOpenMap = new JMenuItem("start");
		//mntmOpenMap.setAction(action_1);
		mnGame.add(mntmOpenMap);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAboutGame = new JMenuItem("About Game");
		mnHelp.add(mntmAboutGame);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("/Users/mengranli/eclipse-workspace/soen6441/risk.png"));
		getContentPane().add(lblNewLabel, BorderLayout.CENTER);
	}
}
