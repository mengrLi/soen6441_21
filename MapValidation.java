package models;

import java.util.*;

public class MapValidation {
	
	
	/**
	 * This method is used to check the validation of the map
	 * 
	 * @param continents is the map of all continents
	 * @param connectionMap is the map of all countries and their contiguous countries
	 * @return true if  map is connected; false if the map has some errors
	 */
	public boolean checkMapValidation(HashMap<String,Continent> continents, HashMap<String, Collection<String>> connectionMap, HashMap<String,Country> countries) {
		
		String signal = isContinentInternallyConnected(continents);
		if( signal == "ALL PASSED") 
		{
			if(isWholeMapConnected(connectionMap, countries)) 
			{
				return true;
			}
			else {
				System.out.println("Error: Some continent are not externally connected");
			}
		}
		else
		{
			System.out.println("Error: "+signal+"continent is not connected");
		}
		System.out.println("Map is successfully connected!");
	}
	
	
	/**
	 * This method is used to check continent validation
	 * 
	 * @param continents is the map of all continents 
	 * @return specific continent's name if there is a continent unconnected 
	 * @return String "ALL PASSED" if all continents are internally connected
	 */
	public String isContinentInternallyConnected(HashMap<String,Continent> continents) {
		
		for(String continentname : continents.keySet()) 
		{
			//currentctn is the current continent
			Continent currentctn = continents.get(continentname);
			//To check the connection of countries of each continent 
			for(Country ctry1 : currentctn.getCountryList()) 
			{
				//Use a integer to count the number of contiguous countries of each country
				int counter = 0;
				for(Country ctry2 : currentctn.getCountryList()) 
				{
					if(connectionMap.get(ctry1.getName()).contains(ctry2)) 
					{
						counter++;
					}
				}
				//Counter==0 means there is a isolate country, so continent is not connected internally,
				//Return the name of this unconnected continent
				if(counter == 0) 
				{
					return continentname;
				}
			}
		}
		return "ALL PASSED";
	}
	
	
	/**
	 * This method is used to check the validation of the whole map
	 * 
	 * @param connectionMap is the map of all countries and their contiguous countries
	 * @param countries is the map of all countries
	 * @return true if the whole map is successfully connected; false if it is not connected
	 */
	public boolean isWholeMapConnected(HashMap<String, Collection<String>>  connectionMap, HashMap<String,Country> countries) {
		//The first country we used to start doing DFS
		Country sourceNode = null;
		
		//flag is the attribute of the country class, if one country has been searched, the flag is true, otherwise the flag is false
		//At first we set all flags of countries false
		for(String ctryname : connectionMap.keySet()) 
		{
			Country ctry3 = countries.get(ctryname);
			ctry3.setflag(false);
			if(sourceNode == null) 
			{
				sourceNode = ctry3;
			}
		}
		
		//Doing DFS to search all the accessible path of the whole map
		DFS(connectionMap, countries, sourceNode);
		
		//To check if the whole map is connected
		//If there is a flag of a country is false, means this country is isolated, then reject the map
		//Otherwise the map is successfully connected
		for(String ctryname2 : connectionMap.keySet()) 
		{
			Country ctry4 = countries.get(ctryname2);
			if(!ctry4.getflag()) 
			{
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * This method is used to apply DFS algorithm on map validation checking
	 * @param connectionMap is the map of all countries and their contiguous countries
	 * @param countries is the map of all countries
	 * @param sourceNode is the key country we used to check its next connected country 
	 */
	public void DFS(HashMap<String, Collection<String>>  connectionMap, HashMap<String,Country> countries, Country sourceNode) {
		sourceNode.setflag(true);
		//currentctryname is current country name
		for(String currentctryname : connectionMap.get(sourceNode.getName())) 
		{
			//targetNode is the current contiguous country of sourceNode
			Country targetNode = countries.get(currentctryname);
			if(!targetNode.getflag()) 
			{
				DFS(connectionMap, countries, targetNode);
			}
		}
	}

}
