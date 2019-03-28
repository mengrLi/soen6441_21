package MapView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * this is a abstract class of all panel classes
 * design a basic layout of view
 *
 * @author mengranli
 */

public class aPanel {

    private
    GridLayout grid = new GridLayout(1, 3);
    Color theme = Theme.color;
    public
    JPanel panel;
    public JPanel left;
    public JPanel center;
    public JPanel right;
    public JLabel label;
    JLabel label2;
    public JButton back = new JButton("<");
    public JButton next = new JButton(">");

    public aPanel() {
        panel = new JPanel();
        left = new JPanel();
        center = new JPanel();
        right = new JPanel();
        label = new JLabel();

        label2 = new JLabel();
        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 23);
        label.setFont(f);
        Font f2 = new Font(Font.DIALOG_INPUT, Font.BOLD, 25);
        label2.setFont(f2);
        panel.setLayout(grid);
        panel.setBackground(theme);
        left.setBackground(theme);
        center.setBackground(theme);
        right.setBackground(theme);

        left.add(back);
        center.add(label2);
        center.add(label);
        right.add(next, BorderLayout.SOUTH);

        back.setBackground(theme);

        back.setSelected(false);
        back.setContentAreaFilled(false);
        panel.add(left);
        panel.add(center);
        panel.add(right);

        panel.setVisible(false);

    }

    /**
     * set visible at the beginning
     * @param b
     */

    public void SetVisible(boolean b) {
        panel.setVisible(b);
    }
    /**
     * this method perform buttons action in this popup menu
     *
     * @param e
     */
    public void AddActionListener(ActionListener e) {
        next.addActionListener(e);
        back.addActionListener(e);
    }

}
