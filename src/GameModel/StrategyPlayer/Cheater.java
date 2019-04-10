package GameModel.StrategyPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import GameModel.*;
import MapModel.Map;


/**
 * This is the strategy for Cheater
 */
public class Cheater implements Strategy {
    public String strategyName = "Cheater";
    private PlayerEngine playerEngine;
    private Map map = Map.getMapInstance();
    private int turnNum = 200; //default total turn number of each cheater
    public int count = 1; //current number of turn
    private int bonusArmyNum = 0; //default bonus army number

    /**
     * Constructor
     */
    public Cheater() {
        this.playerEngine = new PlayerEngine();
    }


    /**
     * Get the name of current strategy
     *
     * @return the name of strategy
     */
    public String getStrategyName() {
        return this.strategyName;
    }


    /**
     * Auto reinforce phase for cheater
     */
    public void autoReinforce(Player curPlayer) {
        if (playerEngine.round < 20) {
            //change card phase
            if (playerEngine.state == GameState.CHOOSECARD) {
                ArrayList<Card> cardList = curPlayer.getCardList();
                System.out.println("cheater cardList:" + cardList);
                int cardNum = cardList.size();
                //if one player has 5 and more cards then exchange card for bonus armies
                if (cardNum >= 5) {
                    for (int i = 0; i < 5; i++) {
                        Card curCard = cardList.get(0);
                        cardList.remove(curCard);
                    }
                    this.bonusArmyNum = curPlayer.ExchangeCardforArmy(null);
                }
                System.out.println("bonus army: " + bonusArmyNum);
            }

            System.out.println("Player " + curPlayer + "(Cheater)" + "---------------------");
            System.out.println("Total army before reinforce: " + curPlayer.getArmyList().size());

            //Double armies of all countries of cheater
            int countryNum = curPlayer.getCountriesOwned().size();
            System.out.println(countryNum);
            curPlayer.setArmyList(2 * count * countryNum + this.bonusArmyNum);
            ArrayList<Country> cheaterCountry = curPlayer.getCountriesOwned();
            for (Country curCtry : cheaterCountry) {
                int doubleSize = 2 * count + Math.floorDiv(bonusArmyNum, countryNum);
                for (int i = 0; i < doubleSize; i++) {
                    curCtry.AddArmy();
                }
            }
            this.bonusArmyNum = 0; //reset bonus army number

            System.out.println("Doubles armies on all its countries");
            System.out.println("Total army after reinforce: " + curPlayer.getArmyList().size());
        }
    }


    /**
     * Auto attack phase for cheater
     */
    public boolean autoAttack(Player curPlayer) {
        boolean ifWinned = false;
        //if (playerEngine.round < 20) {
            //attack() method automatically conquers all the neighbors of all its countries
            //steps
            //1. Get all its neighbors.
            //2. Get the neighbor countries' owner, remove this country from its countryList
            //3. Set neighbors' occupier to the cheater
            System.out.println("Attack phase gets started---------------------");
            ArrayList<Country> ctryList = curPlayer.getCountriesOwned();
            int ctrySize = ctryList.size();
            ArrayList<Country> cheaterOwnedCtry = new ArrayList<>();
            for (int i = 0; i < ctrySize; i++) {
                cheaterOwnedCtry.add(ctryList.get(i));
            }

            for (Country attackCtry : cheaterOwnedCtry) {
                if (attackCtry.getDefendersAroundThisCountry().size() > 0) {
                    ArrayList<String> defendersAroundThisCountry = attackCtry.getDefendersAroundThisCountry();
                    for (String defenderName : defendersAroundThisCountry) {
                        Country defender = map.getCountry(defenderName);
                        if (conquerDefender(attackCtry, defender)) {
                            ifWinned = true;
                            break;
                        }
                    }
                }
            }

//    			String ctryName = attackCtry.getName();
//    			Collection<String> neighbors = map.getConnectionMap().get(ctryName);
//    			for(String oneOfNeighbors : neighbors) {
//    				Country oneNeighborCtry = map.getCountriesMap().get(oneOfNeighbors);
//    				if(oneNeighborCtry.getPlayer()!= curPlayer) {
//    					while(oneNeighborCtry.getArmiesNum()!=0) {
//    						oneNeighborCtry.reduceArmy();
//    					}
//    					playerEngine.checkAfterAtteacked(attackCtry, oneNeighborCtry);
//    				}
//    				else if(oneNeighborCtry.getPlayer()==curPlayer){
//    					while(oneNeighborCtry.getArmiesNum()==0 && attackCtry.getArmiesNum()>3) {
//    						playerEngine.moveArmyBetweenCountries(3, curPlayer, oneNeighborCtry, attackCtry);
//    					}
//    				}
//    			}
//    		}
//    		//Check if the game is end
//            if(playerEngine.getCurrentState()==GameState.END) {
//            	ifWinned= true;
//            }
        //}
        return ifWinned;
    }


    /**
     * Auto fortify phase for cheater
     */
    public void autoFortify(Player curPlayer) {

        if (playerEngine.round < 20) {
            //fortify() method doubles the number of armies on its countries
            //that have neighbors that belong to other players.
            //1. get countries which adject to other player's country
            //2. double its (country from step 1) army number
            System.out.println("Fortifycation phase gets started---------------------");
            for (Country ctry : curPlayer.getCountriesOwned()) {
                boolean addFlag = false;
                String ctryName = ctry.getName();
                Collection<String> neighbors = map.getConnectionMap().get(ctryName);
                for (String oneOfNeighbors : neighbors) {
                    Country oneNeighborCtry = map.getCountriesMap().get(oneOfNeighbors);
                    if (oneNeighborCtry.getPlayer() != curPlayer) {
                        if (addFlag == false) {
                            //Double the armies of target country
                            curPlayer.setArmyList(ctry.getArmiesNum());
                            for (int i = 0; i < ctry.getArmiesNum(); i++) {
                                ctry.AddArmy();
                            }
                            System.out.println("Doubles the army on country : " + ctry.getName());
                            addFlag = true;
                        }
                    }
                }
            }
            this.count++;
            this.turnNum--;
            System.out.println("Success in fortify of Cheater.");
        }
    }


    public boolean conquerDefender(Country attacker, Country defender) {

        while (defender.getArmiesNum() > 0) {
            defender.reduceArmy();
        }
        return playerEngine.checkAfterAtteacked(attacker, defender);
    }
}
