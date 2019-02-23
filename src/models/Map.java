package models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * This is class Map module to represent a map in the game.
 * We have used three hashmap implementation to save continents, countries and adjacency states.
 * This class follows the singleton pattern
 * @author xiaoyunliao
 */

public class Map {  //
    private int id;
    private String name;
    private String author, warn, image, wrap, scroll;
    public HashMap<String,Continent> continents = new HashMap<String,Continent>() ;
    public HashMap<String,Country> countries = new HashMap<String,Country> ();
    public HashMap<String, Collection<String>> countriesInContinet = new HashMap<String, Collection<String>>();
    private HashMap<String, Collection<String>> connectionMap = new HashMap<String, Collection<String>> ();
    static int Max_Continent;
    static int Max_country;
    private static Map map = null;
    private static int mapNum = 0;

    /**
     * Private constructor, prevent someone create multiple map instances
     */
    private Map(){  // Private constructor, prevent someone create multiple map instances

    }

    /**
     * return a unique instance
     * @return  a unique instance
     */
    public static Map getMapInstance(){
        if(mapNum == 0){
            map = new Map();
            mapNum ++;
        }
        return map;
    }

    /**
     * get all continents
     * @return  a collection includes all continents
     */
    public Collection<Continent> getAllContinent(){
        return continents.values();
    }


    /**
     * get all countries
     * @return  a collection includes all countries
     */
    public Collection<Country> getAllCountries(){
        return countries.values();
    }

    /**
     * How many countries
     */
    public int getCountryNum(){
        return countries.size();
    }

    /**
     * How many continents
     */
    public int getContinentNum(){
        return continents.size();
    }

    /**
     * adjacency states
     *  @return a hashMap, key is country name, value is all countries adjacent to it.
     */
    public  HashMap<String, Collection<String>> getConnectionMap()
    {
        return this.connectionMap;
    }


    /**
     * get a hashmap includes all continents
     *  @return a hashMap, key is continent name, value is continent instance .
     */
    public HashMap<String,Continent> getContinentMap() {
        return continents;
    }


    /**
     * get a hashmap includes all countries
     *  @return a hashMap, key is country name, value is country instance .
     */
    public HashMap<String,Country> getCountriesMap() {
        return countries;
    }

    /**
     * find a continent instance by its name
     */
    public Continent getContinent(String name){
        return continents.get(name);
    }

    /**
     * find a country instance by its name
     */
    public Country getCountry(String name){
        return countries.get(name);
    }


    /**
     * clear map content
     */
    public void reset(){
        getMapInstance();
        name = "";
        map.continents.clear();
        map.countries.clear();
        map.connectionMap.clear();
    }


    public void setName(String name){
        this.name = name;
    }

    /**
     * add a continent
     */
    public void addContinent(String name){
        Random ra = new Random(47);
        int value = ra.nextInt(30); // value between 0 ~ 30
        Continent continent = new Continent(name,value);
        map.continents.put(name,continent); //add a new continent to map
    }



    /**
     * add a country
     */
    public void addCountry(String name, int x , int y, Continent continent, List<String> connection){
        Country country = new Country(name, x , y, continent); //create a new country
        map.countries.put(name,country);
        continent.addCountry(country); //add a new country to this continent
        map.getConnectionMap().put(name,connection); // add connection
    }


    /**
     * load map
     */
    public void loadMap(String nameOfMapFile){
        reset(); //before loading the map, clear the map first
        BufferedReader br = null;
        String inputLine = null;
        String dataArea = "";
        int index;
        int rowNum = 0;
        HashMap<String, Set<String>> connectionSet = new HashMap<String, Set<String>> ();

        try{
            br = new BufferedReader(new FileReader(nameOfMapFile));
            //鍐嶅鍔犱竴浜涜鍙栨枃浠剁殑鍒ゆ柇
            while ((inputLine = br.readLine()) != null){
                rowNum++ ;
                inputLine = inputLine.trim();
                if(!inputLine.isEmpty()){
                    switch (inputLine){
                        case "[Map]":
                            dataArea = "Map";
                            break;
                        case "[Continents]":
                            dataArea = "Continents";
                            break;
                        case "[Territories]":
                            dataArea = "Territories";
                            break;
                         default:
                             switch (dataArea){
                                 case "Map":
                                     index = inputLine.indexOf("=");
                                     if(index != -1){
                                         String key = inputLine.split("=")[0].trim().toLowerCase();
                                         String value = inputLine.split("=")[1].trim().toLowerCase();
                                         if(!key.isEmpty()){
                                             switch (key){
                                                 case "author":
                                                     this.author = value;
                                                     break;
                                                 case "warn":
                                                     this.warn = value;
                                                     break;
                                                 case "image":
                                                     this.warn = value;
                                                     break;
                                                 case "wrap":
                                                     this.warn = value;
                                                     break;
                                                 case "scroll":
                                                     this.warn = value;
                                                     break;
                                                 default:
                                                     System.out.println("Error in "+ rowNum + "row");
                                             }
                                         }
                                     }
                                     break;

                                 case "Continents":
                                     index = inputLine.indexOf("=");
                                     if(index != -1){
                                         String continentName = inputLine.split("=")[0].trim().toLowerCase();
                                         String value = inputLine.split("=")[1].trim().toLowerCase();
                                         if(!continentName.isEmpty()){
                                             addContinent(continentName);
                                         }
                                     }
                                     break;
                                 case "Territories":
                                     String[] countryInfo = inputLine.split(",");
                                     if(countryInfo.length >= 4){
                                         String countryName = countryInfo[0];
                                         int x = Integer.parseInt(countryInfo[1]);
                                         int y = Integer.parseInt(countryInfo[2]);

                                         Continent continent = map.continents.get(countryInfo[3].trim().toLowerCase());
                                         System.out.println("continet:  "+continent);
                                         String[] connectionCountries = new String[countryInfo.length-4];
                                         System.arraycopy(countryInfo,4, connectionCountries,0,countryInfo.length- 4);
                                         List<String> connection = Arrays.asList(connectionCountries);
                                         addCountry(countryName,x,y,continent,connection);

                                     } else {
                                         System.out.println("Error in "+ rowNum + "row");
                                     }
                                     break;
                                 default: System.out.println("Error in "+ rowNum + "row");
                             }
                    }


                }
            }

        }catch (IOException e){
            System.out.println("Loading error锛�"+e);
        }
        finally {
            try{
                if (br != null) br.close();
            }catch (Exception e){
                System.out.println("Loading error锛�"+e);
            }
        }
    }


    public void save_as(){

    }
    
    
    /**
	 * This is the function of Map Validation
	 * The methods below are described fully in the documentation
	 * @author QingLi
	 */
	
	
	/**
	 * This method is used to check the validation of the map
	 * @param continents is the map of all continents
	 * @param connectionMap is the map of all countries and their contiguous countries
	 * @return true if  map is connected; false if the map has some errors
	 */
	public void checkMapValidation() {
		
		String signal = isContinentInternallyConnected();
		if( signal == "ALL PASSED") {
			if(isWholeMapConnected()) {
				System.out.println("Map is successfully connected!");
			}
			else {
				System.out.println("Error: Some continent are not externally connected");
			}
		}
		else{
			System.out.println("Error: "+signal+" continent is not connected");
		}
	}
	
	
	/**
	 * This method is used to check continent validation
	 * @param continents is the map of all continents 
	 * @return specific continent's name if there is a continent unconnected 
	 * @return String "ALL PASSED" if all continents are internally connected
	 */
	public String isContinentInternallyConnected() {
		
		for(String continentname : continents.keySet()) {
			//currentctn is the current continent
			Continent currentctn = continents.get(continentname);
			//To check the connection of countries of each continent 
			for(Country ctry1 : currentctn.getCountryList()) {
				//Use a integer to count the number of contiguous countries of each country
				int counter = 0;
				for(Country ctry2 : currentctn.getCountryList()) {
					if(connectionMap.get(ctry1.getName()).contains(ctry2)) {
						counter++;
					}
				}
				//Counter==0 means there is a isolate country, so continent is not connected internally,
				//Return the name of this unconnected continent
				if(counter == 0) {
					return continentname;
				}
			}
		}
		return "ALL PASSED";
	}
	
	
	/**
	 * This method is used to check the validation of the whole map
	 * @param connectionMap is the map of all countries and their contiguous countries
	 * @param countries is the map of all countries
	 * @return true if the whole map is successfully connected; false if it is not connected
	 */
	public boolean isWholeMapConnected() {
		//The first country we used to start doing DFS
		Country sourceNode = null;
		
		//flag is the attribute of the country class, if one country has been searched, the flag is true, otherwise the flag is false
		//At first we set all flags of countries false
		for(String ctryname : connectionMap.keySet()) {
			Country ctry3 = countries.get(ctryname);
			ctry3.setflag(false);
			if(sourceNode == null) {
				sourceNode = ctry3;
			}
		}
		
		//Doing DFS to search all the accessible path of the whole map
		DFS(sourceNode);
		
		//To check if the whole map is connected
		//If there is a flag of a country is false, means this country is isolated, then reject the map
		//Otherwise the map is successfully connected
		for(String ctryname2 : connectionMap.keySet()) {
			Country ctry4 = countries.get(ctryname2);
			if(!ctry4.getflag()) {
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
	public void DFS(Country sourceNode) {
		sourceNode.setflag(true);
		//currentctryname is current country name
		for(String currentctryname : connectionMap.get(sourceNode.getName())) {
			//targetNode is the current contiguous country of sourceNode
			Country targetNode = countries.get(currentctryname);
			if(!targetNode.getflag()) {
				DFS(targetNode);
			}
		}
	}

    public static void main(String[] args){
        String fileName = "World.map";
        getMapInstance();
        map.loadMap(fileName);

        System.out.println( " getcountriesMap-----"+getMapInstance().getCountriesMap());
        System.out.println( "getContinentMap-------"+getMapInstance().getContinentMap());
        System.out.println( "getConnectionMap-------"+getMapInstance().getConnectionMap());  
        
        map.checkMapValidation();
        

    }
}
