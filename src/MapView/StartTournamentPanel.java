package MapView;

import MapView.aPanel;
import com.sun.tools.javadoc.Start;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @program: 6441-build3
 * @description:
 * @author:
 * @create: 2019-04-04
 **/
public class StartTournamentPanel extends aPanel {


        public JButton selectMap;
        public JLabel choosegamenumber;
        public JComboBox gamecombo;


        public StartTournamentPanel() {
            selectMap = new JButton("Select Map");
            center.remove(label);
            center.add(selectMap);
            String data[] = {"1", "2", "3", "4","5"};
            choosegamenumber = new JLabel("Choose Game Numbers: ");
            gamecombo = new JComboBox(data);
            center.add(choosegamenumber);
            center.add(gamecombo);



        }

        /**
         * this method perform buttons action in this popup menu
         *
         * @param e
         */
        public void AddActionListener(ActionListener e) {
            super.AddActionListener(e);
            selectMap.addActionListener(e);

        }




}
