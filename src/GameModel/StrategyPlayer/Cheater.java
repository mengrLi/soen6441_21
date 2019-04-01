package GameModel.StrategyPlayer;

import java.util.ArrayList;
import java.util.Collection;

import GameModel.*;
import MapModel.Map;


/**
 * This is the strategy for Cheater
 */
public class Cheater implements Strategy{
	public String strategyName = "Cheater";
	private PlayerEngine playerEngine;
	private Map map= Map.getMapInstance();
	
	
	/**
	 * Constructor
	 */
    Cheater(){
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
     * Auto reinforce phase for cheater
     */
    public void autoReinforce(Player curPlayer){
    	System.out.println("Player " + curPlayer + "(Cheater)" + "---------------------");
        System.out.println("Total army before reinforce: " + curPlayer.getNumberOfArmy());
        
        //Double armies of all countries of cheater
        curPlayer.setArmyList(curPlayer.getNumberOfArmy());
        ArrayList<Country> cheaterCountry = curPlayer.getCountriesOwned();
        for(Country curCtry: cheaterCountry) {
        	int doubleSize = curCtry.getArmiesNum();
        	for(int i=0;i<doubleSize;i++) {
        		curCtry.AddArmy();
        	}
        }
        
        System.out.println("Doubles armies on all its countries");
        System.out.println("Total army after reinforce: " + curPlayer.getNumberOfArmy());
    };

    
    /**
     * Auto attack phase for cheater
     */
    public boolean autoAttack(Player curPlayer){
    	
        boolean ifWinned = false; //flag of whether the cheater wins or not
        
        //attack() method automatically conquers all the neighbors of all its countries
    	//steps
        //1. Get all its neighbors.
        //2. Get the neighbor countries' owner, remove this country from its countryList
        //3. Set neighbors' occupier to the cheater
        for(Country attackCtry: curPlayer.getCountriesOwned()) {
        	String ctryName = attackCtry.getName();
        	Collection<String> neighbors = map.getConnectionMap().get(ctryName);
        	for(String oneOfNeighbors : neighbors) {
        		Country oneNeighborCtry = map.getCountriesMap().get(oneOfNeighbors);
        		if(oneNeighborCtry.getPlayer()!= curPlayer) {
        			while(oneNeighborCtry.getArmiesNum()!=0) {
            			oneNeighborCtry.reduceArmy();
            		}
            		playerEngine.checkAfterAtteacked(attackCtry, oneNeighborCtry); 	
        		}
        	}
        }
        
        //Check if the game is end
        if(playerEngine.getCurrentState()==GameState.END) {
        	ifWinned= true;
        }
        
        return  ifWinned;
    };

    
    /**
     * Auto fortify phase for cheater
     */
    public void autoFortify(Player curPlayer){
    	//fortify() method doubles the number of armies on its countries
        //that have neighbors that belong to other players.
        //1.get countries which adject to other player's country
        //2. double its (country from step 1) army number
    	for(Country ctry: curPlayer.getCountriesOwned()) {
    		String ctryName = ctry.getName();
    		Collection<String> neighbors = map.getConnectionMap().get(ctryName);
    		for(String oneOfNeighbors : neighbors) {
    			Country oneNeighborCtry = map.getCountriesMap().get(oneOfNeighbors);
    			if(oneNeighborCtry.getPlayer()!= curPlayer) {
    				//Double the armies of target country
    				curPlayer.setArmyList(ctry.getArmiesNum());
    				for(int i= 0;i<ctry.getArmiesNum();i++) {
    					ctry.AddArmy();
    				}
    				 System.out.println("Doubles the army on country : " + ctry.getName());
    			}
    		}
    	}
    	System.out.println("Success in fortify of Cheater.");
    }
}
