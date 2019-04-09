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

	/**
	 * This method used to implement auto reinforce of random strategy
	 */
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

	/**
	 * This method used to implement auto attack of random strategy
	 */
	
	public boolean autoAttack(Player curPlayer) {
//		if (state == GameState.ATTACK) {
		Country curCountry;
		ArrayList<Country> ownedCountryList = curPlayer.getCountriesOwned();
		ArrayList<Country> randCountryList = new ArrayList<Country>();
		for(Country c:ownedCountryList) {
			if(c.getDefendersAroundThisCountry().size()>0);
			randCountryList.add(c);
		}
		int randAttackCountryNum = (int) (Math.random() * randCountryList.size());
		Country AttackCountry = randCountryList.get(randAttackCountryNum);
		ArrayList<String> defendCountryString = AttackCountry.getDefendersAroundThisCountry();
		ArrayList<Country> randDefendCountryList = new ArrayList<Country>();
		for(String s: defendCountryString) {
			randDefendCountryList.add(map.getCountry(s));
		}
		int randDefendNum = (int) (Math.random() * randDefendCountryList.size());
		Country defendCountry = randDefendCountryList.get(randDefendNum);
		System.out.println("attack country:"+AttackCountry.getName()+"defend country"+defendCountry.getName());
		int attackNum = (int) (Math.random() * AttackCountry.getArmiesNum())-1;
		for (int i = 1; i <= attackNum; i++) {
		playerEngine.diceOne(AttackCountry, defendCountry);
		playerEngine.checkAfterAtteacked(AttackCountry, defendCountry);
		}
//		ArrayList<Country> randomCountry = curPlayer.getCountriesOwned();
//		int randCountryNum = (int) (Math.random() * curPlayer.getCountriesOwned().size());
//		curCountry = randomCountry.get(randCountryNum);
//		ArrayList<Country> contigunousCountry = curCountry.getcontiguousCountryList();
//		int attackedRandCountrynum = (int) (Math.random() * contigunousCountry.size());
//		Country defendCountry = contigunousCountry.get(attackedRandCountrynum);
//		int attackNum = (int) (Math.random() * curCountry.getArmiesNum());
//		for (int i = 1; i <= attackNum; i++) {
//			playerEngine.diceOne(curCountry, defendCountry);
//			playerEngine.checkAfterAtteacked(curCountry, defendCountry);
//		}
//		}

		boolean ifWinned = false;
		if (state == GameState.END) {
			ifWinned = true;
		}
		return ifWinned;
	};

	/**
	 * This method used to implement auto fortify of random strategy
	 */
	public void autoFortify(Player curPlayer) {
//		if (state == GameState.FORTIFY) {
		ArrayList<Country> ownedCountry = curPlayer.getCountriesOwned();
		ArrayList<Country> cccContiguousBelong = new ArrayList<>();
		for(Country ccc:ownedCountry) {
			if(ccc.getcontiguousBelongThisPlayer().size()>0) {
				cccContiguousBelong.add(ccc);
			}
		}
		int randCountryNum = (int) (Math.random() * curPlayer.getCountriesOwned().size());
		Country destCountry = cccContiguousBelong.get(randCountryNum);
		ArrayList<Country> originCountryList = new ArrayList<>();
		ArrayList<String> origCountyString = new ArrayList<String>();
		origCountyString = destCountry.getcontiguousBelongThisPlayer();
		for(String s:origCountyString) {
			originCountryList.add(map.getCountry(s));
		}
		int randOriginal = (int) (Math.random() * originCountryList.size());
		Country originCountry = originCountryList.get(randOriginal);
		int armyNum =  (int) (Math.random() * originCountry.getArmiesNum());
		playerEngine.moveArmyBetweenCountries(armyNum, curPlayer, destCountry, originCountry);
		System.out.println("you move"+armyNum+"army");

//		if(contiguousBelongToThisPlayer.size()!=0) {
//			Country originalCountry;
//			int randOriCountryNum = (int) (Math.random()*contiguousBelongToThisPlayer.size());
//			originalCountry = contiguousBelongToThisPlayer.get(randOriCountryNum);
//			int moveArmy = (int) (Math.random() * originalCountry.getArmiesNum()-1);
//			playerEngine.moveArmyBetweenCountries(moveArmy, curPlayer, destCountry, originalCountry);
//			System.out.println("you move"+moveArmy+"army");
//		}
//		}
	};
}
