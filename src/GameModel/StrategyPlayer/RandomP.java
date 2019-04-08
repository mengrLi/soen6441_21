package GameModel.StrategyPlayer;

import GameModel.Player;

import java.util.ArrayList;
import java.util.*;

import GameModel.Card;
import GameModel.Country;
import GameModel.GameState;
import GameModel.PlayerEngine;
import MapModel.Map;

/**
 * This is the class used to implement the random strategy
 * 
 * @author anhen
 */
public class RandomP implements Strategy {
	public String strategyName = "Random";
	private PlayerEngine playerEngine;
	private Map map = Map.getMapInstance();
	public GameState state;
	private int bonusArmyNum = 0; // default bonus army number

	public RandomP() {
		playerEngine = new PlayerEngine();
	}

	public String getStringName() {
		return this.strategyName;
	}

	public void autoReinforce(Player curPlayer) {
//		if (state == GameState.REINFORCE) {
		if (playerEngine.state == GameState.CHOOSECARD) {
			ArrayList<Card> cardList = curPlayer.getCardList();
			System.out.println(cardList);
			int cardNum = cardList.size();
			// if one player has 5 and more cards then exchange card for bonus armies
			if (cardNum >= 5) {
				for (int i = 0; i < 5; i++) {
					Card curCard = cardList.get(0);
					cardList.remove(curCard);
				}
				this.bonusArmyNum = curPlayer.ExchangeCardforArmy(null);
			}
			curPlayer.setArmyList(curPlayer.getArmyList().size());
			ArrayList<Country> randomCountry = curPlayer.getCountriesOwned();
			for (int i = 0; i < randomCountry.size(); i++) {
				int rand = (int) (Math.random() * randomCountry.size());
				Country randCountry = randomCountry.get(rand);
				randCountry.AddArmy();
			}
		}
//		}
	};

	public boolean autoAttack(Player curPlayer) {
//		if (state == GameState.ATTACK) {
		Country curCountry;
		ArrayList<Country> randomCountry = curPlayer.getCountriesOwned();
		int randCountryNum = (int) (Math.random() * curPlayer.getCountriesOwned().size());
		curCountry = randomCountry.get(randCountryNum);
		ArrayList<Country> contigunousCountry = curCountry.getcontiguousCountryList();
		int attackedRandCountrynum = (int) (Math.random() * contigunousCountry.size());
		Country defendCountry = contigunousCountry.get(attackedRandCountrynum);
		int attackNum = (int) (Math.random() * curCountry.getArmiesNum());
		for (int i = 1; i <= attackNum; i++) {
			playerEngine.diceOne(curCountry, defendCountry);
			playerEngine.checkAfterAtteacked(curCountry, defendCountry);
		}
//		}

		boolean ifWinned = false;
		if (state == GameState.END) {
			ifWinned = true;
		}
		return ifWinned;
	};

	public void autoFortify(Player curPlayer) {
//		if (state == GameState.FORTIFY) {
		ArrayList<Country> randCountry = curPlayer.getCountriesOwned();
		int randCountryNum = (int) (Math.random() * curPlayer.getCountriesOwned().size());
		Country originalCountry = randCountry.get(randCountryNum);
		Country destCountry = randCountry.get(randCountryNum);
		while (originalCountry == destCountry) {
			destCountry = randCountry.get(randCountryNum);
		}
		int moveArmy = (int) (Math.random() * originalCountry.getArmiesNum());
		playerEngine.moveArmyBetweenCountries(moveArmy, curPlayer, destCountry, originalCountry);
//		}
	};
}
