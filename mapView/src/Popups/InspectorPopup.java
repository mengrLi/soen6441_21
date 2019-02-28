package Popups;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import View.Theme;

public class InspectorPopup {
	public JTextArea textarea;
	public JPopupMenu popup;
	public InspectorPopup() {
		JPanel panel = new JPanel();
		textarea = new JTextArea(7, 20);
		popup = new JPopupMenu();
		JScrollPane scroll = new JScrollPane(textarea);
		Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 9);
		scroll.setBackground(Theme.color);
		popup.setBackground(Theme.color);
		textarea.setBackground(Theme.color);
		panel.setBackground(Theme.color);
		textarea.setFont(font);
		textarea.setEditable(false);
		panel.add(textarea);
		popup.add(panel);
	}

}
