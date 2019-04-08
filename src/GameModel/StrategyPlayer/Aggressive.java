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
	public ArrayList<Country> countriesList = new ArrayList<>();

	public Aggressive() {
		playerEngine = new PlayerEngine();
	}

	public String getStrategyName() {
		return this.strategyName;
	}

	public void autoReinforce(Player curPlayer) {
//		if (state == GameState.REINFORCE) {
        int max = 0;
        Country destination = null;
		curPlayer.setArmyList(curPlayer.getArmyList().size());
        for(Country  destinationCntry: curPlayer.getCountriesOwned()){
            //loop all getCountriesOwned
            if(destinationCntry.getDefendersAroundThisCountry().size()>0){
                if(destinationCntry.getArmiesNum() > max ){
                    max = destinationCntry.getArmiesNum();
                    destination = destinationCntry;
                }
            }
        }
        if(destination != null){
            int getReinfoArmyNum = curPlayer.getNumberOfArmy();
            curPlayer.setArmyList(getReinfoArmyNum);
            while (getReinfoArmyNum > 0){
                destination.AddArmy();
                getReinfoArmyNum --;
            }
        }

//		Country reinCountry = null;
//		for (Country c : curPlayer.getCountriesOwned()) {
//			if (c.getArmiesNum() > max) {
//				reinCountry = c;
//				max = c.getArmiesNum();
//			}
//		}
//		for (int i = 0; i < curPlayer.getNumberOfArmy(); i++) {
//			reinCountry.AddArmy();
//		}
//		}

	}

	public boolean autoAttack(Player curPlayer) {
		boolean ifWinned = false;

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

			ifWinned = false;
			if (playerEngine.getCurrentState() == GameState.END)
				ifWinned = true;
		return ifWinned;
	}

	public void autoFortify(Player curPlayer) {
        int maxArmy = 0;
        Country destination = null;
        Country origin = null;
		for(Country  destinationCntry: curPlayer.getCountriesOwned()){
		    //loop all getCountriesOwned
			if(destinationCntry.getDefendersAroundThisCountry().size()>0){
                ArrayList<String> contiguousBelongThisPlayer = destinationCntry.getcontiguousBelongThisPlayer();
                for(String contryName : contiguousBelongThisPlayer) {
                    Country originctn = map.getCountry(contryName);
                    if (originctn.getArmiesNum() + destinationCntry.getArmiesNum() > maxArmy) {
                        origin = originctn;
                        destination = destinationCntry;
                        maxArmy = originctn.getArmiesNum() + destinationCntry.getArmiesNum();
                    }
                }
			}
		}
		//recursiveFind(curPlayer);
        if(destination !=null && origin != null){
            playerEngine.moveArmyBetweenCountries(origin.getArmiesNum()-1,curPlayer,destination,origin);
        }
	}


	public void recursiveFind(Player curPlayer){
		//for循环可以fortify的国家，找出相邻国家数最大的，或者本身他就是最大的
		//

		Country destination = null;
		int max = 0;
		for(Country mmm : countriesList){
			if(mmm.getArmiesNum()> max){
				destination = mmm;
				max = destination.getArmiesNum();
			}
		}

		Country originctn = null;
		if(destination.getcontiguousBelongThisPlayer().size()>0){
			//ArrayList<Country> contiguousBelongThisPlayer = new ArrayList<>();

			int max1 = 0;
			for(String contryName :  destination.getcontiguousBelongThisPlayer()){
				Country ddd = map.getCountry(contryName);
				//contiguousBelongThisPlayer.add(ddd);

				if(ddd.getArmiesNum()> max1){
					originctn = ddd;
					max1 = originctn.getArmiesNum();
				}
			}
			playerEngine.moveArmyBetweenCountries(originctn.getArmiesNum()-1,curPlayer,destination,originctn);
			countriesList.clear();
		}else {
			countriesList.remove(destination);
			recursiveFind(curPlayer);
		}
	}
}
