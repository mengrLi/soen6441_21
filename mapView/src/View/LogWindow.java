package View;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.w3c.dom.css.Rect;
import Models.*;

/**
 * This class creates an easy to use and beautiful log window user only needs to use add() function to append very well-formatted text
 * to the log window
 * @author aliahadi
 *
 */
public class LogWindow {
	private JTextArea text;
	private JScrollPane scroll;
	Color theme = Theme.color;
	/**
	 * Constructor, creates the panel and text area and initializes them
	 */
	public LogWindow() {
		text = new JTextArea(6, 80);
		Font f = new Font(Font.MONOSPACED, Font.BOLD, 13);
		text.setBackground(Color.white);
		text.setForeground(theme);
		text.setFont(f);
		text.setEditable(false);
		text.setSelectionColor(Color.WHITE);
		text.setSelectedTextColor(theme);
		text.setAutoscrolls(true);
		scroll = new JScrollPane(text);
		scroll.setAutoscrolls(true);
		scroll.setBackground(theme);
		scroll.setToolTipText("Game Log");
		scroll.setHorizontalScrollBar(null);
		
		
	}
	/**
	 * use this function to simply log your game! enjoy!
	 * @param str String to be added
	 */
	public void add(String str) {
		text.setSelectionColor(Color.BLACK);
		text.setSelectedTextColor(theme);
		text.append("\n " + GameEngine.round + " > " + str);
		//text.selectAll();
		
		//text.append("\n >" + str);
	}
	/**
	 * Returns the component to be added on other containers
	 * @return returns component
	 */
	public JScrollPane getComponent() {
		return scroll;
	}
	
}
