package GameModel;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class Continent, defines a continent with these properties: ID , Color, Name
 * of the continent, value of continent
 *
 * @author
 */
public class Continent {

    public Color color;
    private int id;

    private String name;

    /**
     * The control value.
     */
    private int Value;

    private Player player;

    private ArrayList<Country> CountryList = new ArrayList<Country>();

    private static int idNumber = 0;

    /**
     * Creates a new continent include the name，control value and id.
     */
    public Continent(String name, int Value) {
        this.name = name;
        this.Value = Value;
        idNumber++;
        this.id = idNumber;
    }


    /**
     * Gets the id.
     */
    public int getID() {
        return this.id;
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
     * Gets the Value.
     */
    public int getValue() {
        return Value;
    }

    /**
     * Sets the Value.
     */
    public void setValue(int value) {
        this.Value = value;
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
     * Check the player.
     */
    public Player checkPlayer() {
        return this.player;
    }

    /**
     * Gets the countryList.
     */
    public ArrayList<Country> getCountryList() {
        return CountryList;
    }

    /**
     * Sets the countryList.
     */
    public void setCountryList(ArrayList<Country> countryList) {
        this.CountryList = countryList;
    }

    /**
     * Adds the country to the countryList.
     */
    public void addCountry(Country country) {
        this.CountryList.add(country);
    }

    /**
     * Removes the country from the countryList.
     */
    public void removeCountry(int countryID) {
        for (Country country : this.CountryList) {
            if (country.getID() == countryID) {
                this.CountryList.remove(country);
                return;
            }
        }
    }

    @Override
    public String toString() {
        String info = id + "-" + name + "-" + CountryList;
        return info;
    }
}