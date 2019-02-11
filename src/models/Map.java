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
            //再增加一些读取文件的判断
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
            System.out.println("Loading error："+e);
        }
        finally {
            try{
                if (br != null) br.close();
            }catch (Exception e){
                System.out.println("Loading error："+e);
            }
        }
    }


    public void save_as(){

    }

    public static void main(String[] args){
        String fileName = "World.map";
        getMapInstance();
        map.loadMap(fileName);

        System.out.println( " getcountriesMap-----"+getMapInstance().getCountriesMap());
        System.out.println( "getContinentMap-------"+getMapInstance().getContinentMap());
        System.out.println( "getConnectionMap-------"+getMapInstance().getConnectionMap());

    }
}
