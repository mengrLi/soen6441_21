package GameModel.StrategyPlayer;

import GameModel.Country;
import GameModel.GameState;
import GameModel.Player;
import GameModel.PlayerEngine;
import MapModel.Map;
import java.util.*;

/**
 * @author anhen This class is used to implement the aggressive strategy Created
 *         by liaoxiaoyun on 2019-03-30.
 */
public class Aggressive implements Strategy {
	public String strategyName = "Aggressive";
	private PlayerEngine playerEngine;
	private GameState state;
	private Map map = Map.getMapInstance();

	public Aggressive() {
		playerEngine = new PlayerEngine();
	}

	public String getStrategyName() {
		return this.strategyName;
	}

	public void autoReinforce(Player curPlayer) {
		if (state == GameState.REINFORCE) {
			curPlayer.setArmyList(curPlayer.getArmyList().size());
			int max = 0;
			Country reinCountry = null;
			for (Country c : curPlayer.getCountriesOwned()) {
				if (c.getArmiesNum() > max) {
					reinCountry = c;
					max = c.getArmiesNum();
				}
			}
			for (int i = 0; i < curPlayer.getNumberOfArmy(); i++) {
				reinCountry.AddArmy();
			}
		}

	};

	public boolean autoAttack(Player curPlayer) {
		if (state == GameState.ATTACK) {
			int max = 0;
			Country attackCountry = null;
			for (Country c : curPlayer.getCountriesOwned()) {
				if (c.getArmiesNum() > max) {
					attackCountry = c;
					max = c.getArmiesNum();
				}
			}
			ArrayList<Country> contiguousCountry = attackCountry.getcontiguousCountryList();
			for (int i = 0; i < contiguousCountry.size(); i++) {
				while (contiguousCountry != null && attackCountry.getArmiesNum() > 0) {
					playerEngine.diceAll(attackCountry, contiguousCountry.get(i));
					playerEngine.checkAfterAtteacked(attackCountry, contiguousCountry.get(i));
				}
			}

		}

		boolean ifWinned = false;
		if (playerEngine.getCurrentState() == GameState.END)
			ifWinned = true;
		return ifWinned;
	};

	public void autoFortify(Player curPlayer) {
		if (state == GameState.FORTIFY) {
			int max = 0;
			Country fortifyCountry = null;
			for (Country c : curPlayer.getCountriesOwned()) {
				if (c.getArmiesNum() > max) {
					fortifyCountry = c;
					max = c.getArmiesNum();
				}
			}
			ArrayList<Country> contiguousCountry = fortifyCountry.getcontiguousCountryList();
			for (int i = 0; i < contiguousCountry.size(); i++) {
				while (contiguousCountry.get(i).getArmiesNum() > 1) {
					int amyMoved = contiguousCountry.get(i).getArmiesNum() - 1;
					playerEngine.moveArmyBetweenCountries(amyMoved, curPlayer, fortifyCountry,
							contiguousCountry.get(i));
				}
			}
		}
	}
}
