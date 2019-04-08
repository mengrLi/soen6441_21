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
//		if (state == GameState.REINFORCE) {
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
//		}

	};

	public boolean autoAttack(Player curPlayer) {
//		if (state == GameState.ATTACK) {
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
			if (contiguousCountry != null && attackCountry.getArmiesNum() > 1) {
				playerEngine.diceAll(attackCountry, contiguousCountry.get(i));
				playerEngine.checkAfterAtteacked(attackCountry, contiguousCountry.get(i));
			}
		}

//		}

		boolean ifWinned = false;
		if (playerEngine.getCurrentState() == GameState.END)
			ifWinned = true;
		return ifWinned;
	};

	public void autoFortify(Player curPlayer) {
//		if (state == GameState.FORTIFY) {
		int max = 0;
		Country strongestCountry = null;
		boolean flag = true;
		int z = 0;
		int idx = 0;
		for (Country c : curPlayer.getCountriesOwned()) {
			if (c.getArmiesNum() > max) {
				strongestCountry = c;
				max = c.getArmiesNum();
			}
		}
		ArrayList<Country> contiguousCountry = strongestCountry.getcontiguousCountryList();
		while (z < contiguousCountry.size()) {
			if (contiguousCountry.get(z).getPlayer() != curPlayer) {
				flag = false;
				break;
			}
			z++;
		}
		if (flag == false) {
			for (int i = 0; i < contiguousCountry.size(); i++) {
				if (contiguousCountry.get(i).getArmiesNum() > 1) {
					int armyMoved = contiguousCountry.get(i).getArmiesNum() - 1;
					playerEngine.moveArmyBetweenCountries(armyMoved, curPlayer, strongestCountry,
							contiguousCountry.get(i));
				}
			}
		} else {
			for (int i = 0; i < contiguousCountry.size(); i++) {
				while (idx < contiguousCountry.get(i).getcontiguousCountryList().size()) {
					if (contiguousCountry.get(i).getcontiguousCountryList().get(idx).getPlayer() != curPlayer) {
						playerEngine.moveArmyBetweenCountries(strongestCountry.getArmiesNum() - 1, curPlayer,
								contiguousCountry.get(i).getcontiguousCountryList().get(idx), strongestCountry);
						break;
					}
					idx++;
				}
			}
		}
	}
}
