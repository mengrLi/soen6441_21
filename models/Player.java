package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.awt.Color;

/**
 * This is the class of player
 *
 */
public class Player {
    public String name;
    private int ID;
    public Color color;
    private HashMap<Card,Integer> cards = new HashMap<>();
    private ArrayList<Country> countriesOwned = new ArrayList<>();
    private ArrayList<Army> armys = new ArrayList<>();
    
    /**
     * This is the constructor of player
     * @param ID is the ID of each player defined by user
     */
    public Player(int ID) {
    	this.ID = ID;	
    }
    
    /**
     * Set the name of a player
     * @param name is the name of a player
     */
    public void setName(String name) {
    	this.name =name;
    }
    
    /**
     * Get the name of a player
     * @return string name 
     */
    public String getName() {
    	return this.name;
    }
    
    /**
     * Get the ID of a player
     * @return int ID
     */
    public int getID() {
    	return this.ID;
    }
    
    /**
     * Set the color of a player
     * @param color is the color randomly assigned to players at the start up phase
     */
//    public void setColor(Color color) {
//    	int pick = new Random().nextInt(Color.values().length);
//    	this.color = Color.values()[pick];
//    }
//    
    /**
     * Get the color of a player
     * @return color 
     */
    public Color getColor() {
    	return this.color;
    }
    
    /**
     * Set the countryList owned by a player
     * @param countryList is a list of countries owned by a player
     */
    public void setCountriesOwned(ArrayList<Country> countryList) {
    	this.countriesOwned = countryList;
    }
    
    /**
     * Get the list of countries owned by a player
     * @return ArrayLis<Country>
     */
    public ArrayList<Country> getCountriesOwned(){
    	return this.countriesOwned;
    }
    
    /**
     * Get the list of army owned by a player
     * @return array list of army
     */
    public ArrayList<Army> getArmyList(){
       return  this.armys;
    }


}
