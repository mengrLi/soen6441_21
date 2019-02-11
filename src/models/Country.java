package models;
import java.util.ArrayList;

public class Country {

    private String name;

    private Continent continent;

    private Player player;

    private int X;

    private int Y;

    private int ID;

    private boolean flag;


    /** each country has a contiguous country list. */
    private ArrayList<Country> contiguousCountryList = new ArrayList<Country>();

    /** each country has a army list. */
    private ArrayList<Army> ArmyList = new ArrayList<Army>();

    private static int IDNumber=0;

    /**
     * Creates a new country.
     */
    public Country(String name) {
        this.name = name;
        IDNumber++;
        this.ID = IDNumber;
    }

    public Country(String name, int x, int y, Continent continet) {
        this.name = name;
        IDNumber++;
        this.ID = IDNumber;
        this.X = x;
        this.Y = y;
        this.continent = continet;

    }

    /**
     * Gets the ID.
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Gets the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the continent.
     */
    public Continent getContinent() {
        return continent;
    }

    /**
     * Sets the continent.
     */
    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    /**
     * Gets the player.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Gets the number of armies.
     */
    public int getArmiesNum() {
        return ArmyList.size();
    }

    /**
     * Gets the location X.
     */
    public int getX() {
        return X;
    }

    /**
     * Sets the location X.
     */
    public void setX(int X) {
        this.X = X;
    }

    /**
     * Gets the location Y.
     */
    public int getY() {
        return Y;
    }

    /**
     * Sets the location Y.
     */
    public void setY(int Y) {
        this.Y = Y;
    }

    /**
     * Gets the flag.
     */
    public boolean getflag(){

        return flag;
    }

    /**
     * Sets the flag.
     */
    public void setflag(boolean flag){

        this.flag=flag;
    }
    /**
     * Gets the contiguous country list.
     */
    public ArrayList<Country> getcontiguousCountryList() {
        return contiguousCountryList;
    }

    /**
     * Sets the contiguous country list.
     */
    public void setContiguousCountryList(ArrayList<Country> contiguousCountryList) {
        this.contiguousCountryList = contiguousCountryList;
    }

    /**
     * Adds the contiguous country to the list.
     */
    public void addContiguousCountry(Country country) {
        this.contiguousCountryList.add(country);

    }

    /**
     * Removes the contiguous country.
     */
    public void removeContiguousCountry(int countryID){
        for(Country country:this.contiguousCountryList) {
            if(country.getID() == countryID) {
                this.contiguousCountryList.remove(country);
            }
        }
    }

    /**
     * a country has contiguous country determine if they belong to other players
     */
    public boolean contiguousBelongOthers() {
        for(Country country:this.contiguousCountryList) {
            if (!country.getPlayer().equals(player))
                return true;
        }

        return false;
    }


    /**
     * Adds one army to this country.
     */
    public void AddArmy() {
        for (Army army:player.getArmyList()) {
            if(army.getCountry() == null) {
                army.setCountry(this);
                this.ArmyList.add(army);
                break;
            }
        }
    }

    /**
     * Reduce one Army of this country.
     */
    public void reduceArmy() {
        if(ArmyList.size()>1){
            this.ArmyList.remove(1);
        }
    }

    /**
     * Remove a number of Armies from the country's ArmyList and the author's ArmyList.
     */
    public void removeArmies(int ArmiesNo) {
        while(ArmyList.size()>0 && ArmiesNo>0){
            Army army =this.ArmyList.get(0);
            player.getArmyList().remove(army);
            this.ArmyList.remove(army);
            ArmiesNo--;
        }
    }

    /**
     * Increase One Army to the author's ArmyList and the country's ArmyList.
     */
    public void increaseArmy() {
        Army army = new Army(player);
        player.getArmyList().add(army);
        this.ArmyList.add(army);
    }


    @Override
    public String toString() {
        String info = ID+"-"+name +"-"+ X + "," + Y +"-"+continent.getName();
        return info;
    }
}