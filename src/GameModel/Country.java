package GameModel;

import MapModel.Map;
import MapView.Theme;
import MapView.LogWindow;

import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;

/**
 * This is the class used to define country
 *
 * @author tianwnag
 */
public class Country {
    public static LogWindow log;
    private static int MAX_NODE_COUNT = 50;
    private String name;

    private Continent continent;

    private Player player;

    private int X;

    private int Y;

    private int ID;

    private boolean flag;

    public static final int NODE_DIAMETER = 25;

    private Army army;


    public boolean deleted;

    public GameState resource;

    public boolean capital;

    private Map map = Map.getMapInstance();

    /**
     * each country has a contiguous country list.
     */
    private ArrayList<Country> contiguousCountryList = new ArrayList<Country>();

    /**
     * each country has a army list.
     */
    private ArrayList<Army> ArmyList = new ArrayList<Army>();


    /**
     * Creates a new country.
     */

    public Country() {

    }

    /**
     * Creates a new country with name and id
     */
    public Country(String name, int id) {
        this.name = name;
        this.ID = id;
    }

    /**
     * Creates a new country with name coordinateX, coordianateY and id
     */
    public Country(String name, int x, int y, int id) {
        this.X = x;
        this.Y = y;
        this.name = name;
        this.ID = id;
    }

    /**
     * Creates a new country with name coordinateX, coordianateY, continent and id
     */
    public Country(String name, int x, int y, Continent continet, int id) {
        this.name = name;
        this.ID = id;
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
    public boolean getflag() {

        return flag;
    }


    /**
     * Sets the flag.
     */
    public void setflag(boolean flag) {

        this.flag = flag;
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
    public void removeContiguousCountry(int countryID) {
        for (Country country : this.contiguousCountryList) {
            if (country.getID() == countryID) {
                this.contiguousCountryList.remove(country);
            }
        }
    }

    /**
     * a country has contiguous country determine if they belong to other players
     */
    public boolean contiguousBelongOthers() {
        for (Country country : this.contiguousCountryList) {
            if (!country.getPlayer().equals(player))
                return true;
        }

        return false;
    }


    /**
     * Adds one army to this country.
     */
    public void AddArmy() {

        for (Army army : player.getArmyList()) {
            if (army.getCountry() == null) {
                System.out.println("AddArmy: " + player.getArmyList().indexOf(army));
                army.setCountry(this);
                this.ArmyList.add(army);
                break;
            } else {
                // log.add("You have placed all your armies!");
            }
        }
    }


    /**
     * Reduce one Army of this country.
     */
    public void reduceArmy() {
        if (ArmyList.size() >= 1) {
            Army rmArmy = this.ArmyList.get(0);
            this.player.getArmyList().remove(rmArmy);
            this.ArmyList.remove(rmArmy);

        }
    }

    public void moveOutOneArmy() {
        reduceArmy();
        for (Army army : player.getArmyList()) {
            if (army.getCountry() == this) {
                army.setCountry(null);
                break;
            }
        }
    }


    /**
     * This is the method to draw the country in map
     *
     * @param g
     */
    public void Draw(Graphics2D g) {
        if (!(X == 0 && Y == 0)) {
            //		g = (Graphics2D) g;
            //		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int d2 = 4;
            if (!deleted) {


                if (continent != null) g.setColor(continent.color);
                else g.setColor(Color.black);
                if (capital)
                    g.fillRoundRect(X - NODE_DIAMETER / 2 - d2 / 2, Y - NODE_DIAMETER / 2 - d2 / 2, NODE_DIAMETER + d2, NODE_DIAMETER + d2, 10, 10);
                else
                    g.fillOval(X - NODE_DIAMETER / 2 - d2 / 2, Y - NODE_DIAMETER / 2 - d2 / 2, NODE_DIAMETER + d2, NODE_DIAMETER + d2);

                if (player != null) g.setColor(player.color);
                else g.setColor(Theme.color);
                if (capital)
                    g.fillRoundRect(X - NODE_DIAMETER / 2, Y - NODE_DIAMETER / 2, NODE_DIAMETER, NODE_DIAMETER, 10, 10);
                else
                    g.fillOval(X - NODE_DIAMETER / 2, Y - NODE_DIAMETER / 2, NODE_DIAMETER, NODE_DIAMETER);

                //add number of army on node
                String numberOfArmy = "" + ArmyList.size();
                g.setColor(Color.BLACK);
                Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 15);
                g.setFont(font);
                g.drawString(numberOfArmy, X - 5, Y + 5);


//                 if (resource != null) {
//                     String strI = "" + resource.toString();
//                     g.setColor(Color.BLACK);
//                     Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 8);
//                     g.setFont(font);
//                     g.drawString(strI, X - 10, Y);
//                     //int power = army.getPower();
//                     //String strII = "" + power;
//                     //g.drawString(strII, X-5, Y+12);
//                 }


            }
        }
    }

    public boolean IsNotDeleted() {
        return !deleted;
    }

    public boolean IsDeleted() {
        return deleted;
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
        String info = ID + "-" + name + "-" + X + "," + Y;

        return info;
    }

    /**
     * Determines if the mouse is pointing at the state
     *
     * @param x current mouse x
     * @param y current mouse y
     * @return returns true if the mouse is over this object
     */
    public boolean MouseOver(int x, int y) {
        if (deleted == false) {
            int r = NODE_DIAMETER;
            if (x >= this.X - r / 2 && x <= this.X + r / 2 && y >= this.Y - r / 2 && y <= this.Y + r / 2) return true;
            else return false;
        } else return false;
    }


    public boolean IsNotOwned() {
        if (player == null) return true;
        else return false;
    }

    public boolean IsOwned() {
        if (player == null) return false;
        else return true;
    }

    /**
     * get contiguous belong this country's owner
     *
     * @return country name list
     */
    public ArrayList<String> getcontiguousBelongThisPlayer() {

        //System.out.println("getcontiguousBelongThisPlayer  -----"+map.getConnectionMap().get(name));

        ArrayList<String> countryNameList = new ArrayList<String>();
        for (String contiguousName : map.getConnectionMap().get(name)) {
            if (map.getCountry(contiguousName).getPlayer().getName().equals(this.player.getName())) {
                countryNameList.add(contiguousName);
            }
        }

        System.out.println("countryNameList" + countryNameList);
        return countryNameList;
    }

    /**
     * get contiguous which can be defender
     *
     * @return country name list
     */
    public ArrayList<String> getDefendersAroundThisCountry() {

        ArrayList<String> countryNameList = new ArrayList<String>();
        for (String contiguousName : map.getConnectionMap().get(name)) {
            if (!map.getCountry(contiguousName).getPlayer().getName().equals(this.player.getName())) {
                countryNameList.add(contiguousName);
            }
        }

        System.out.println("Defender countryNameList" + countryNameList);
        return countryNameList;
    }


}
