package GameModel.StrategyPlayer;

import GameModel.Player;

import java.util.ArrayList;
import java.util.*;
import GameModel.Country;
import GameModel.GameState;
import GameModel.PlayerEngine;
import MapModel.Map;

/**
 * This is the class used to implement the random strategy
 * 
 * @author anhen
 */
public class Random implements Strategy {
	public String strategyName = "Random";
	private PlayerEngine playerEngine;
	private Map map = Map.getMapInstance();
	public GameState state;

	public Random() {
		playerEngine = new PlayerEngine();
	}

	public String getStringName() {
		return this.strategyName;
	}

	public void autoReinforce(Player curPlayer) {
		if (state == GameState.REINFORCE) {
			ArrayList<Country> randomCountry = curPlayer.getCountriesOwned();
			int randomNum;
			int reinforceNum = curPlayer.getNumberOfArmy();
			randomNum = (int) (Math.random() * curPlayer.getCountriesOwned().size());
			for (int i = 1; i <= reinforceNum; i++) {
				Country country = randomCountry.get(randomNum - 1);
				country.AddArmy();
			}
		}
	};

	public boolean autoAttack(Player curPlayer) {
		if (state == GameState.ATTACK) {
			Country curCountry;
			ArrayList<Country> randomCountry = curPlayer.getCountriesOwned();
			int randCountryNum = (int) (Math.random() * curPlayer.getCountriesOwned().size());
			curCountry = randomCountry.get(randCountryNum);
			ArrayList<Country> contigunousCountry = curCountry.getcontiguousCountryList();
//			int attackedRandCountrynum = 
		}

		boolean ifWinned = false;

		return ifWinned;
	};

	public void autoFortify(Player curPlayer) {
		if (state == GameState.FORTIFY) {
			ArrayList<Country> randCountry = curPlayer.getCountriesOwned();
			int randCountryNum = (int) (Math.random() * curPlayer.getCountriesOwned().size());
			Country originalCountry = randCountry.get(randCountryNum);
			Country destCountry = randCountry.get(randCountryNum);
			while (originalCountry == destCountry) {
				destCountry = randCountry.get(randCountryNum);
			}
			int moveArmy = (int) (Math.random() * originalCountry.getArmiesNum());
			playerEngine.moveArmyBetweenCountries(moveArmy, curPlayer, destCountry, originalCountry);
		}
	};
}
