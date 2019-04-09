package GameModel.StrategyPlayer;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;

import GameModel.*;
import MapModel.Map;


/**
 * This is the strategy for Benevolent
 */
public class  Benevolent implements Strategy{
	public String strategyName = "Benevolent";
	private PlayerEngine playerEngine;
	private Map map= Map.getMapInstance();
	public static GameState state;
	public ArrayList<Country> notAvalibaleCountry = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Benevolent() {
		this.playerEngine = new PlayerEngine();

	}

	/**
	 * Get the name of current strategy
	 * @return the name of strategy
	 */
	public String getStrategyName() {
		return this.strategyName;
	}


	/**
	 * Auto reinforce phase for Benevolent
	 */
	public void autoReinforce(Player curPlayer){
		//find weakest country
		//the weakest country adjacent to enemy
		//and army number is lowest
		playerEngine.state=GameState.REINFORCE;
		Country weakest = null;
		weakest = curPlayer.getCountriesOwned().get(0);
		for(Country country : curPlayer.getCountriesOwned()){
			if(country.getDefendersAroundThisCountry().size()>0){
				if(country.getArmiesNum() < weakest.getArmiesNum()){
					weakest = country;
				}
			}
		}
		System.out.println(" REINFORCE  weakest " + weakest);
		notAvalibaleCountry.add(weakest);
		//get REINFORCE number
		int NumberOfArmy = curPlayer.getNumberOfArmy();
		curPlayer.setArmyList(NumberOfArmy);
		while (NumberOfArmy > 0){
			weakest.AddArmy();
			NumberOfArmy --;
		}
    };

    
    /**
     * Auto attack phase for Benevolent
     */
    public boolean autoAttack(Player curPlayer){
    	
    	// do not attack
    	return false;
    };


	/**
	 * Auto fortify phase for Benevolent
	 */
	public void autoFortify(Player curPlayer) {
		playerEngine.state = GameState.FORTIFY;
		// just have onc chance to move army


		Country weaker = findWeakestCountry(curPlayer);
		System.out.println(" FORTIFY  weakest " + weaker);
		if(weaker != null){
			Country stronger = getStrongerContry(weaker);
			System.out.println(" FORTIFY  stronger " + stronger);
			if (stronger != null) {
				while (stronger.getArmiesNum() > weaker.getArmiesNum()) {
					stronger.moveOutOneArmy(weaker);
				}
				notAvalibaleCountry.clear();
			}else {
				notAvalibaleCountry.add(weaker);
				if(notAvalibaleCountry.size()<curPlayer.getCountriesOwned().size()){
					autoFortify(curPlayer);
				}
			}
		}else {
			notAvalibaleCountry.clear();
		}
	}


	public Country findWeakestCountry(Player curPlayer){
		Country weakest = null;
		int armyNum = 10000;
		for(Country country : curPlayer.getCountriesOwned()){
			if(country.getDefendersAroundThisCountry().size()>0){
				if(country.getArmiesNum() <= armyNum && !notAvalibaleCountry.contains(country)){
					weakest = country;
					armyNum = weakest.getArmiesNum();
				}
			}
		}
		return weakest;
	}

	public Country getStrongerContry(Country weakest){
		Country strongerCountry = null;
		Country stronger = weakest;
		ArrayList<String> contiguousBelongThisPlayer  = weakest.getcontiguousBelongThisPlayer();
		System.out.println();
		for(String contiguous: contiguousBelongThisPlayer){
			Country contiguousCnty = map.getCountriesMap().get(contiguous);
			if( contiguousCnty.getArmiesNum()> stronger.getArmiesNum() ){
				stronger = contiguousCnty;
			}
		}
		if(stronger != weakest){
			strongerCountry = stronger;
		}
		return strongerCountry;
	}
}

