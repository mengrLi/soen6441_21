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
    	playerEngine.state=GameState.REINFORCE;
    	int reinforcementArmyNumber = curPlayer.getNumberOfArmy();
		int min = Integer.MAX_VALUE;
		Country country = null;
		curPlayer.setArmyList(reinforcementArmyNumber);
		for(Country c:curPlayer.getCountriesOwned()) {
			if(c.getArmiesNum()<min) {
				country = c;
				min = c.getArmiesNum();
			}
		}
		
		if(country!=null){
			for (int i=0; i<reinforcementArmyNumber; i++) {
				country.AddArmy();
			}
		}
     
    };

    
    /**
     * Auto attack phase for Benevolent
     */
    public boolean autoAttack(Player curPlayer){
    	
    	// do not attack
    	return true;
    };

    
    /**
     * Auto fortify phase for Benevolent
     */
    public void autoFortify(Player curPlayer){	
    	playerEngine.state=GameState.FORTIFY;
        HashMap <Country, ArrayList<Country>> connectedCountryList = new HashMap<Country, ArrayList<Country>>();
        ArrayList<Country> NeighborCtry=new ArrayList<Country>();
		for (Country c:curPlayer.getCountriesOwned()) {
			if(c.getArmiesNum()>0) {
				String ctryName = c.getName();
				Collection<String> neighbors = map.getConnectionMap().get(ctryName);
				for(String oneOfNeighbors : neighbors) {
					Country oneNeighborCtry = map.getCountriesMap().get(oneOfNeighbors);
	    			if(oneNeighborCtry.getPlayer()== curPlayer) {
	    				NeighborCtry.add(oneNeighborCtry);
	    			}
				}
    			
				for (int i=0; i<NeighborCtry.size(); i++) {
					connectedCountryList.put(c, NeighborCtry);				
				}
    			
				NeighborCtry=null;
			}
		}	
		
		int min = Integer.MAX_VALUE;
		ArrayList<Country> fromCountries = new ArrayList<Country>();
		Country toCountry = null;
		for(Country f: connectedCountryList.keySet()) {
			for(Country t: connectedCountryList.get(f)) {
				if(t.getArmiesNum()<min) {
					fromCountries.add(f);
					toCountry = t;
					min = t.getArmiesNum();					
				}
			}
		}
		
		int max = Integer.MIN_VALUE;
		Country fromCountry = null;
		for(Country c:fromCountries) {
			if(c.getArmiesNum()>max) {
				fromCountry = c;
				max = c.getArmiesNum();
			}
		}
		
		if (fromCountry != null) {
			int realQt = fromCountry.getArmiesNum()-1;
			// Move armies
			for(int i = 0; i<realQt; i++){
				fromCountry.moveOutOneArmy(toCountry);
			}
		}	
     }
    
}

