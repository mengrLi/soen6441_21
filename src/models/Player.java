package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Player {
    private String name;
    private Color color;
    private HashMap<Card,Integer> cards;
    private ArrayList<Country> countriesOwned;
    private ArrayList<Army> armys;


   public ArrayList<Army> getArmyList(){
       return  armys;
   }


}
