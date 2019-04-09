package MapView;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import GameModel.PlayerEngine;


/**
 * This class creates an easy to use and beautiful log window user only needs to use add() function to append very well-formatted text
 * to the log window
 */
public class LogWindow {
    private JTextArea text;
    private JScrollPane scroll;
    Color theme = Theme.color;

    /**
     * Constructor, creates the panel and text area and initializes them
     */
    public LogWindow() {
        text = new JTextArea(7, 90);
        Font f = new Font(Font.MONOSPACED, Font.BOLD, 13);
        text.setBackground(Color.white);
        text.setForeground(Color.BLACK);
        text.setFont(f);
        text.setEditable(false);
        text.setSelectionColor(Color.WHITE);
        text.setSelectedTextColor(Color.BLACK);
        text.setAutoscrolls(true);
        scroll = new JScrollPane(text);
        scroll.setAutoscrolls(true);
        scroll.setBackground(theme);
        scroll.setToolTipText("Game Log");
        scroll.setHorizontalScrollBar(null);
    }

    /**
     * use this function to simply log your game! enjoy!
     *
     * @param str String to be added
     */
    public void add(String str) {
        text.setSelectionColor(Color.BLACK);

        text.setSelectedTextColor(theme);
        if(PlayerEngine.mapList.size()>0){
            int curMap = PlayerEngine.mapList.indexOf(PlayerEngine.curMap);
            int curGame = PlayerEngine.curGame;
            text.append("\n " + "Map"+ curMap + "-Game"+curGame + "-Round"+PlayerEngine.round + " > " + str);
        }else {
            text.append("\n " + PlayerEngine.round + " > " + str);
        }

        //text.selectAll();

        //text.append("\n >" + str);
    }

    /**
     * Returns the component to be added on other containers
     *
     * @return returns component
     */
    public JScrollPane getComponent() {
        return scroll;
    }

}
