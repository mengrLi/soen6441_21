package GameView;

import MapView.aPanel;

/**
 * This class is match start up state, design a panel let user easy to add armies
 * user click country to add army
 *
 * @author mengranli
 */
public class PlaceArmyPanel extends aPanel {
    public PlaceArmyPanel() {
        center.add(label);
        label.setText("Assign Army");
    }


}
