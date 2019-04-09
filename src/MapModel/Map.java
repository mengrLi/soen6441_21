package MapModel;

import GameModel.Continent;
import GameModel.Country;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * This is class Map module to represent a map in the game.
 * We have used three hashmap implementation to save continents, countries and adjacency states.
 * This class follows the singleton pattern
 *
 * @author xiaoyunliao, mengranli, qingli, tianwang, hengyangan
 */

public class Map extends Observable {  //
    public static final int MAX_NODE_COUNT = 50; // Maximum number of nodes
    public static HashMap<String, Continent> continents = new HashMap<String, Continent>();
    public static int[][] link = new int[MAX_NODE_COUNT][MAX_NODE_COUNT];
    private static Map map = null;
    private static int mapNum = 0;
    public HashMap<String, Country> countries = new HashMap<String, Country>();
    public List<String> continentsDefaultNameList = Arrays.asList("North America", "South America", "Africa", "Europe", "Australia", "Asia");
    public int nodeCount; // Number of nodes alive
    public boolean drawConnectingLine = false;
    private int id;
    private String name;
    public String author = "", warn = "", image = "", wrap = "", scroll = "";
    private HashMap<String, Collection<String>> connectionMap = new HashMap<String, Collection<String>>();


    /**
     * Private constructor, prevent someone create multiple map instances
     */
    private Map() {// Private constructor, prevent someone create multiple map instances
        // Initializing Links
        initialMatrix();
    }

    /**
     * return a unique instance
     *
     * @return a unique instance
     */
    public static Map getMapInstance() {
        if (mapNum == 0) {
            map = new Map();
            mapNum++;
        }
        return map;
    }

    public void initialMatrix() {
        nodeCount = 0;
        for (int i = 0; i < MAX_NODE_COUNT; i++) {
            for (int j = 0; j < MAX_NODE_COUNT; j++)
                link[i][j] = 9;
        }
    }

    /**
     * get all continents
     *
     * @return a collection includes all continents
     */
    public Collection<Continent> getAllContinent() {
        System.out.println(continents.values());
        return continents.values();
    }


    /**
     * get all countries
     *
     * @return a collection includes all countries
     */
    public Collection<Country> getAllCountries() {
        return countries.values();
    }

    /**
     * How many countries
     */
    public int getCountryNum() {
        return countries.size();
    }

    /**
     * How many continents
     */
    public int getContinentNum() {
        return continents.size();
    }

    /**
     * adjacency states
     *
     * @return a hashMap, key is country name, value is all countries adjacent to it.
     */
    public HashMap<String, Collection<String>> getConnectionMap() {
        return this.connectionMap;
    }


    /**
     * get a hashmap includes all continents
     *
     * @return a hashMap, key is continent name, value is continent instance .
     */
    public HashMap<String, Continent> getContinentMap() {
        return continents;
    }


    /**
     * get a hashmap includes all countries
     *
     * @return a hashMap, key is country name, value is country instance .
     */
    public HashMap<String, Country> getCountriesMap() {
        return countries;
    }

    /**
     * find a continent instance by its name
     */
    public Continent getContinent(String name) {
        return continents.get(name);
    }

    /**
     * find a country instance by its name
     */
    public Country getCountry(String name) {
        return countries.get(name);
    }


    public void setName(String name) {
        this.name = name;
    }


    /**
     * add a country to a continent
     *
     * @param countryName   country name
     * @param continentName continent name
     */
    public void putCountryToContinent(String countryName, String continentName) {
        if (!continents.containsKey(continentName)) {
            addContinent(continentName);
        }
        Continent continent = continents.get(continentName);
        countries.get(countryName).setContinent(continent);
        continent.addCountry(countries.get(countryName));//add a new country to this continent
    }

    /**
     * add a country to a continent
     *
     * @param countryId     country's id
     * @param continentName continent name
     */
    public void putCountryToContinent(int countryId, String continentName) {
        Country country = getCountry(countryId);
        if (!continents.containsKey(continentName)) {
            addContinent(continentName);
        }
        Continent continent = continents.get(continentName);
        if (country.getContinent() != null) {
            //reset Continent
            country.getContinent().removeCountry(countryId);
            country.setContinent(null);
        }
        country.setContinent(continent); //set country's continent
        continent.addCountry(country); //add a new country to this continent
    }

    /**
     * get a country instance by country id
     *
     * @param ID country's id
     * @return a country instance
     */
    public Country getCountry(int ID) {
        //System.out.println("ID: "+ID);
        Country country = new Country();
        for (Country ccc : getAllCountries()) {
            if (ccc.getID() == ID) {
                country = ccc;
            }
        }
        return country;
    }

    /**
     * add a continent
     *
     * @param name Continent name
     */
    public void addContinent(String name) {
        Random ra = new Random(47);
        int value = ra.nextInt(30); // value between 0 ~ 30
        if (!continents.containsKey(name)) { //add a new continent to map
            Continent continent = new Continent(name, value);
            this.continents.put(name, continent);
        }
    }


    /**
     * add a country with country name, country's coordinate, continent Instance, contiguousCountry list
     *
     * @param name       country name
     * @param x          country's coordinate X
     * @param y          country's coordinate Y
     * @param continent  continent Instance
     * @param connection contiguousCountry list
     */
    public void addCountry(String name, int x, int y, Continent continent, List<String> connection, int id) {
        Country country = new Country(name, x, y, continent, id); //create a new country
        map.countries.put(name, country);
        continent.addCountry(country); //add a new country to this continent
        map.getConnectionMap().put(name, connection); // add connection
    }


    /**
     * add a country with country name, country's coordinate and continent Instance
     *
     * @Param name country name
     * @Param X country's coordinate X
     * @Param Y country's coordinate Y
     * @Param continent continent Instance
     */
    public void addCountryWithContinentInstance(String name, int x, int y, Continent continent, int id) {
        Country country = new Country(name, x, y, continent, id); //create a new country
        map.countries.put(name, country);
        continent.addCountry(country); //add a new country to this continent
    }


    /**
     * add a country with country name, country's coordinate, continent name, and country id
     *
     * @param name          country name
     * @param x             country coordinate X
     * @param y             country coordinate Y
     * @param continentName continent name
     * @param id            country id
     */
    public void addCountryWithContinentName(String name, int x, int y, String continentName, int id) {
        if (!continents.containsKey(continentName)) {
            addContinent(continentName);
        }
        Continent continent = continents.get(continentName);
        Country country = new Country(name, x, y, continent, id); //create a new country
        map.countries.put(name, country);
        continent.addCountry(country); //add a new country to this continent
    }


    /**
     * add a country with country name, country's coordinate,
     *
     * @param name country name
     * @param x    country coordinate X
     * @param y    country coordinate Y
     * @param id   country id
     */
    public void addCountryWithXY(String name, int x, int y, int id) {
        System.out.println("Country: " + name);
        Country country = new Country(name, x, y, id); //create a new country
        map.countries.put(name, country);
    }


    /**
     * add contiguousCountry list for a country
     *
     * @param countryName country name
     * @param connection  contiguous countries list
     */
    public void addConnectionList(String countryName, List<String> connection) {
        System.out.println("addConnectionList - countryName : " + countryName);
        connectionMap.put(countryName, connection);
    }


    /**
     * return all continents' name
     *
     * @return a arrayList of all continents' name
     */
    public ArrayList<String> getContinentName() {
        return (ArrayList<String>) continents.keySet();
    }

    /**
     * return all default continents' name, for map editor to choose continents
     *
     * @return a arrayList of default continents' name
     */
    public ArrayList<String> getAllDefaultContinentName() {
        return (ArrayList) this.continentsDefaultNameList;
    }

    /**
     * clear map content
     */
    public void reset() {
        getMapInstance();
        name = "";
        this.continents.clear();
        map.countries.clear();
        map.connectionMap.clear();
        initialMatrix();
    }

    //reset countries infomation
    public void resetCountries() {
        for(Country country : getAllCountries()){
            country.resetCountry();
        }
    }

    /**
     * load map
     *
     * @param nameOfMapFile Name of map file
     */
    public String loadMap(String nameOfMapFile) {
        reset(); //before loading the map, clear the map first
        String rtnMessage = "Load map succussfully";
        BufferedReader br = null;
        String inputLine = null;
        String dataArea = "";
        int index;
        int rowNum = 0;
        int totalContryNum = 0;

        try {
            br = new BufferedReader(new FileReader(nameOfMapFile));
            int countryCount = 0;
            while ((inputLine = br.readLine()) != null) {
                rowNum++;
                inputLine = inputLine.trim();
                if (!inputLine.isEmpty()) {
                    switch (inputLine) {
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
                            switch (dataArea) {
                                case "Map":
                                    index = inputLine.indexOf("=");
                                    if (index != -1) {
                                        String key = inputLine.split("=")[0].trim().toLowerCase();
                                        String value = inputLine.split("=")[1].trim().toLowerCase();
                                        if (!key.isEmpty()) {
                                            switch (key) {
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
                                                    rtnMessage = "Error in " + rowNum + "row";
                                                    break;
                                            }
                                        }
                                    }
                                    break;

                                case "Continents":
                                    index = inputLine.indexOf("=");
                                    if (index != -1) {
                                        String continentName = inputLine.split("=")[0].trim();
                                        String value = inputLine.split("=")[1].trim().toLowerCase();
                                        totalContryNum = totalContryNum + Integer.parseInt(value);
                                        if (!continentName.isEmpty()) {
                                            addContinent(continentName);
                                        }
                                    }
                                    break;
                                case "Territories":
                                    String[] countryInfo = inputLine.split(",");
                                    if (countryInfo.length >= 4) {
                                        String countryName = countryInfo[0];
                                        int x = Integer.parseInt(countryInfo[1]);
                                        int y = Integer.parseInt(countryInfo[2]);

                                        Continent continent = map.continents.get(countryInfo[3].trim());
                                        System.out.println("continet:  " + continent);
                                        String[] connectionCountries = new String[countryInfo.length - 4];
                                        System.arraycopy(countryInfo, 4, connectionCountries, 0, countryInfo.length - 4);

                                        List<String> connection = new ArrayList<>(50);
                                        connection.addAll(Arrays.asList(connectionCountries));

                                        addCountry(countryName, x, y, continent, connection, countryCount);
                                        countryCount++;

                                    } else {
                                        return "Error in " + rowNum + "row";

                                    }
                                    break;
                                default:
                                    return "Error in " + rowNum + "row";
                            }
                    }
                }
            }
            //check if countries number is correct
            if (totalContryNum != getCountryNum()) {
                return "Loading map error:  countries number is not correct";
            }

            rtnMessage = checkMapValidation("load");

        } catch (IOException e) {
            System.out.println("Loading error" + e);
            return "Loading error";
        } finally {
            try {
                if (br != null) br.close();
            } catch (Exception e) {
                System.out.println("Loading error" + e);
                return "Loading error";
            }
        }
        //cover connectionMap  to matrix
        coverConnectionMapToMatrix();
        System.out.println("Load map : " + map.getConnectionMap());
        return rtnMessage;
    }


    /**
     * save map
     *
     * @param fileName Name of map file
     */
    public String saveMapToFile(String fileName) {
        //cover Link matrix to connectionMap
        //coverMatrixToConnectionMap();// todo
        String resultMsg = "";
        try {
            File outFile = new File(fileName);
            if (outFile.exists()) {
                outFile.delete();
            }
            outFile.createNewFile();
            FileOutputStream outStream = new FileOutputStream(fileName, true);
            outStream.write(("[Map]\r\n").getBytes());
            outStream.write(("author=" + getAuthor() + "\r\n").getBytes());
            outStream.write(("image=" + getImage() + "\r\n").getBytes());
            outStream.write(("warn=" + getWarn() + "\r\n").getBytes());
            outStream.write(("scroll=" + getScroll() + "\r\n").getBytes());
            outStream.write("\r\n".getBytes());
            outStream.write("[Continents]\r\n".getBytes());
            for (Continent continent : getAllContinent()) {
                outStream.write((continent.getName() + "=" + continent.getCountryList().size()).getBytes());
                outStream.write("\r\n".getBytes());
            }
            outStream.write("\r\n".getBytes());
            outStream.write("[Territories]\r\n".getBytes());
            for (Continent continent : getAllContinent()) {
                for (Country country : continent.getCountryList()) {

                    String info = country.getName() + "," + country.getX() + "," + country.getY() + "," + country.getContinent().getName();
                    Collection<String> countryList = connectionMap.get(country.getName());

                    for (String contiguousCountry : countryList) {
                        info = info + "," + contiguousCountry.toString();
                    }
                    outStream.write(info.getBytes());
                    outStream.write("\r\n".getBytes());
                }
                outStream.write("\r\n".getBytes());
            }

            outStream.write("\r\n".getBytes());
            outStream.close();

        } catch (Exception e) {
            resultMsg = "saved map fail";
            System.out.println(e);
        }
        return resultMsg;
    }


    public String mapInfo(){
        String info = "";
        String ContinentsInfo = "";
        for (Continent continent : getAllContinent()) {
            ContinentsInfo = ContinentsInfo + continent.getName() + "=" + continent.getValue() + "\r\n";
        }

        String countryInfo = "";
        for (Continent continent : getAllContinent()) {
            //System.out.println("map save info - all continent:  " + getAllContinent());
            String cinfo = "";
            for (Country country : continent.getCountryList()) {
               // System.out.println("map save info - continent.getCountryList():  " + continent.getCountryList());

               String cntryInfo = country.getName() + "," + country.getX() + "," + country.getY() + "," + country.getContinent().getName();
                System.out.println(cinfo);
                Collection<String> countryList = connectionMap.get(country.getName());

                for (String contiguousCountry : countryList) {
                    cntryInfo = cntryInfo + "," + contiguousCountry.toString();
                }

                cinfo = cinfo + cntryInfo + "\n";
            }
            countryInfo = countryInfo +"\n"+ cinfo + "\r\n";
        }


        info = "[Map]\r\n"
                +"author=" + getAuthor() + "\r\n"
                +"image=" + getImage() + "\r\n"
                +"warn=" + getWarn() + "\r\n"
                +"scroll=" + getScroll() + "\r\n"
                +"\r\n"
                +"[Continents]\r\n"
                +ContinentsInfo
                +"\r\n"
                +"[Territories]\r\n"
                + countryInfo
                +"\r\n";

         System.out.println("mapInfo:"+info);
         return info;
    }


    public String getImage() {
        return !image.isEmpty() ? image : "Default";
    }

    public String getWarn() {
        return !warn.isEmpty() ? warn : "Default";
    }

    public String getScroll() {
        return !scroll.isEmpty() ? scroll : "Default";
    }

    public String getAuthor() {
        return !author.isEmpty() ? author : "Default";
    }


    /**
     * This is the function of Map Validation
     * The methods below are described fully in the documentation
     *
     * @author QingLi
     */


    public String checkMapValidation(String signal2) {
        String message = "success";
        if (signal2.equals("save")) {
            coverMatrixToConnectionMap();
        }

        if (map.getAllCountries().size() == 0) {
            System.out.println("Error1: Empty Map");
            message = "Error1: Empty Map";
            return message;
        } else if (map.getAllCountries().size() == 1) {
            System.out.println("Error2: Containing only one country is invalid, please add more countries!");
            message = "Error2: Containing only one country is invalid, please add more countries!";
            return message;
        } else {
            String signal = isContinentInternallyConnected();
            if (signal == "ALL PASSED") {
                if (isWholeMapConnected()) {
                    System.out.println("Map is successfully connected!");
                    message = "Map is successfully connected!";
                    return message;
                } else {
                    System.out.println("Error3: The whole map is not connected");
                    message = "Error3: The whole map is not connected";
                    return message;
                }
            } else {
                System.out.println("Error4: " + signal + " continent is not connected");
                message = "Error4: " + signal + " continent is not connected";
                return message;
            }
        }
    }


    /**
     * To check if the continent contains 0 links
     *
     * @param ctn is the current continent
     * @return true if continent contains at least one link; false if there is no link in the continent
     */
    public boolean isContinentEmptyLink(Continent ctn) {
        int counter = 0;
        for (Country ctry : ctn.getCountryList()) {
            if (connectionMap.keySet().contains(ctry)) {
                counter++;
            }
        }
        if (counter == 0) {
            return false;
        } else {
            return true;
        }
    }


    public String isContinentInternallyConnected() {

        for (String continentname : continents.keySet()) {
            //currentctn is the current continent
            Continent currentctn = continents.get(continentname);
            //To check the connection of countries of each continent
            if (currentctn.getCountryList().size() > 1) {
                for (Country ctry1 : currentctn.getCountryList()) {
                    //Use a integer to count the number of contiguous countries of each country
                    int counter = 0;
                    System.out.println(" currentctn.getCountryList()---- " + currentctn.getCountryList());
                    for (Country ctry2 : currentctn.getCountryList()) {

                        if (connectionMap.get(ctry1.getName()).contains(ctry2.getName())) {
                            counter++;

                        }
                    }
                    //Counter==0 means there is a isolate country, so continent is not connected internally,
                    //Return the name of this unconnected continent
                    if (counter == 0) {
                        return continentname;
                    }
                }
            }
        }
        return "ALL PASSED";
    }


    /**
     * This method is used to check the validation of the whole map
     *
     * @return true if the whole map is successfully connected; false if it is not connected
     */
    public boolean isWholeMapConnected() {
        if (connectionMap.size() != 0) {
            //The first country we used to start doing DFS
            Country sourceNode = null;

            //flag is the attribute of the country class, if one country has been searched, the flag is true, otherwise the flag is false
            //At first we set all flags of countries false
            for (String ctryname : connectionMap.keySet()) {
                Country ctry3 = countries.get(ctryname);
                System.out.println(ctry3);
                ctry3.setflag(false);
                if (sourceNode == null) {
                    sourceNode = ctry3;
                }
            }

            //Doing DFS to search all the accessible path of the whole map
            DFS(sourceNode);

            //To check if the whole map is connected
            //If there is a flag of a country is false, means this country is isolated, then reject the map
            //Otherwise the map is successfully connected
            for (Country ctry4 : countries.values()) {
                if (!ctry4.getflag()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * This method is used to apply DFS algorithm on map validation checking
     *
     * @param sourceNode is the key country we used to check its next connected country
     */
    public void DFS(Country sourceNode) {
        sourceNode.setflag(true);
        //currentctryname is current country name
        if (connectionMap.get(sourceNode.getName()) != null) {
            for (String currentctryname : connectionMap.get(sourceNode.getName())) {
                //targetNode is the current contiguous country of sourceNode
                Country targetNode = countries.get(currentctryname);
                if (!targetNode.getflag()) {
                    DFS(targetNode);
                }
            }
        }
    }


    /**
     * when add a node on map view, new a country
     *
     * @param x node's coordinate X
     * @param y node's coordinate Y
     */
    public void addNode(int x, int y) {
        if (nodeCount < MAX_NODE_COUNT) {
            // finding a free slot
            int freeIndex = 0;
            while (link[freeIndex][freeIndex] != 9)
                freeIndex++;
            if (freeIndex < MAX_NODE_COUNT) {
                String countryName = "Country" + nodeCount;
                addCountryWithXY(countryName, x, y, freeIndex);
                countries.get(countryName).deleted = false;

                for (int i = 0; i < MAX_NODE_COUNT; i++) {
                    link[freeIndex][i] = 0;
                    link[i][freeIndex] = 0;
                }
                nodeCount++;
            }
        }
//		Log();
        setChanged();
        notifyObservers();
    }

    /**
     * Deletes a node
     *
     * @param index the index of the node to be deleted
     */
    public void DeleteNode(int index) {
        if (nodeCount >= 0 && index > -1) {
            getCountry(index).deleted = true;
            for (int i = 0; i < MAX_NODE_COUNT; i++) {
                link[i][index] = 9;
                link[index][i] = 9;
            }
            nodeCount--;
        }
        //Log();
        setChanged();
        notifyObservers();
    }


    /**
     * Draws the links between nodes
     *
     * @param g Graphics
     * @author mengranli
     */
    public void DrawLinks(Graphics g) {
        g.setColor(Color.BLACK);
        Color color = Color.BLACK;

        for (int j = 0; j < MAX_NODE_COUNT; j++) {
            for (int i = 0; i < MAX_NODE_COUNT; i++) {
                g.setColor(color);
                if (link[i][j] == 1)
                    g.drawLine(getCountry(i).getX(), getCountry(i).getY(), getCountry(j).getX(), getCountry(j).getY());
            }
        }
    }

    /**
     * Draws nodes
     *
     * @param g
     * @author mengranli
     * Graphics
     */
    public void DrawNodes(Graphics2D g) {
        for (int i = 0; i < MAX_NODE_COUNT; i++) {
            if (getCountry(i).IsNotDeleted())
                getCountry(i).Draw(g);
            setChanged();
            notifyObservers();
        }
    }


    /**
     * Draws the line when right-click-dragged
     *
     * @param g             Graphicis
     * @param selectedIndex Index of the selected node
     * @param currMouseX    Current mouse coordinates
     * @param currMouseY    Current mouse coordinate * @author mengranli
     *                      //
     */
    public void DrawConnectingLine(Graphics g, int selectedIndex,
                                   int currMouseX, int currMouseY, int mode) {

        int x1 = getCountry(selectedIndex).getX();
        int y1 = getCountry(selectedIndex).getY();
        int x2 = currMouseX;
        int y2 = currMouseY;

        int x = (x1 + x2) / 2;
        int y = (y1 + y2) / 2;

        double theta = Math.atan2(y2 - y1, x2 - x1);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.translate(x, y);
        g2.rotate(theta);
        if (mode == 0) {
            g2.setColor(Color.black);
            g2.drawString("Connect", 0, 0);
        } else if (mode == 1)
            g2.drawString("Move", 0, 0);
        else if (mode == 2) {
            g2.setColor(Color.MAGENTA);
            g2.drawString("Attack", 0, 0);
        }
        g2.rotate(-theta);
        g2.translate(-x, -y);

        g2.drawLine(getCountry(selectedIndex).getX(), getCountry(selectedIndex).getY(), currMouseX,
                currMouseY);
        if (mode == 2) {
            int radius = (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
            g2.drawArc(x1 - radius, y1 - radius, radius * 2, radius * 2, 0, 360);
        }
    }

    /**
     * cover Link matrix to connectionMap
     */
    public void coverMatrixToConnectionMap() {
        System.out.println("coverMatrixToConnectionMap");
        for (int i = 0; i < MAX_NODE_COUNT; i++) {
            for (int j = 0; j < MAX_NODE_COUNT; j++)
                if (link[i][j] == 1) {
                    String countryX = getCountry(i).getName();
                    String countryY = getCountry(j).getName();
                    if (connectionMap.containsKey(countryX)) {
                        if (!connectionMap.get(countryX).contains(countryY))
                            connectionMap.get(countryX).add(countryY);
                    } else {
                        List<String> connection = new ArrayList<>(50);
                        connection.add(countryY);
                        connectionMap.put(countryX, connection);
                    }
                }
        }
    }


    /**
     * cover connectionMap  to
     */
    public void coverConnectionMapToMatrix() {
        nodeCount = countries.size();
        System.out.println("nodeCount :" + nodeCount);

        for (int i = 0; i < MAX_NODE_COUNT; i++) {
            for (int j = 0; j < MAX_NODE_COUNT; j++)
                link[i][j] = 9;
        }

        for (int i = 0; i < nodeCount; i++) {
            for (int j = 0; j < nodeCount; j++)
                link[i][j] = 0;
        }

        for (String countryI : connectionMap.keySet()) {
            int i = countries.get(countryI).getID();
            List<String> connection = (List<String>) connectionMap.get(countryI);
            if (!connection.isEmpty()) {
                for (String countryJ : connection) {
                    int j = countries.get(countryJ).getID();
                    link[i][j] = 1;
                }
            }
        }
    }


    /**
     * Returns the index of the node. if mouse is node over any node, It returns
     * -1
     *
     * @param x Current mouse coords
     * @param y Current mouse coords
     * @return Index of the node
     */
    public int GetMouseOverIndex(int x, int y) {
        for (int i = 0; i < MAX_NODE_COUNT; i++)
            if (getCountry(i).MouseOver(x, y)) {
                return i;
            }
        return -1;
    }


    /**
     * Moving a node to a new location
     *
     * @param index index of the node to be moved
     * @param x     new coordinates
     * @param y     new coordinates
     */
    public void MoveNode(int index, int x, int y) {
        if (index != -1) {
            getCountry(index).setX(x);
            getCountry(index).setY(y);
        }
        setChanged();
        notifyObservers();
    }

    /**
     * Connects nodes, by updating the adjacency matrix
     *
     * @param firstNode  First Node
     * @param secondNode Target Node
     */
    public void ConnectNodes(int firstNode, int secondNode) {
        if (firstNode != secondNode && firstNode != -1 && secondNode != -1) {
            link[firstNode][secondNode] = 1;
            link[secondNode][firstNode] = 1;
            //Log();
        }
    }

    /**
     * inspects a certain state
     *
     * @param mouseOverIndex index of the state to be inspected
     * @return returns a string containing all information about the state
     */


    public String Inspect(int mouseOverIndex) {
        // TODO Auto-generated method stub

        String str = "";
        int i = mouseOverIndex;
        if (getCountry(i).deleted == false) {


            str =
                    "Country = " + getCountry(i).getName()
                            + "\n------------\nContinent = ";
            if (getCountry(i).getContinent() != null) str += getCountry(i).getContinent().getName();
            str += "\nOwned By = ";
            if (getCountry(i).getPlayer() != null) {
                str += getCountry(i).getPlayer().getName();

            }
            str += "\nArmy Number = ";
            str += getCountry(i).getArmiesNum();

            str += "\nAdjacent Countries = ";

            for (int j = 0; j < MAX_NODE_COUNT; j++) {
                if (link[i][j] == 1) {
                    str += getCountry(j).getName() + ", ";
                }
            }
        }
        return str;
    }

    /**
     * this method implement save function when map is complete
     * check the map validation when save
     *
     * @return
     */


    public String save() {
        // TODO Auto-generated method stub
        String rtnMessage = "";
        String fileName;
        PrintWriter outputStream = null;
        JFrame frm = new JFrame("Save As");
        frm.setAlwaysOnTop(true);
        JPanel p = new JPanel();
        JFileChooser fc = new JFileChooser();
        Container c = frm.getContentPane();

        File f;
        int flag = 0;
        c.setLayout(new FlowLayout());
        c.add(p);
        fc.setDialogTitle("Save File");

        try {
            File filedir = new File("/Users/mengranli/eclipse-workspace/mapView/");
            fc.setCurrentDirectory(filedir);
            flag = fc.showSaveDialog(frm);
        } catch (HeadlessException he) {
            System.out.println("Save File Dialog ERROR!");
            rtnMessage = "Save File Dialog ERROR!";
        }
        if (flag == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            fileName = f.getAbsolutePath();
            System.out.println(fileName);
            //	outputStream = new PrintWriter(new FileOutputStream(fileName));
            rtnMessage = saveMapToFile(fileName);
        }

        return rtnMessage;
    }
    /**
     * this method implement load function when user select a map file and show the map on panel
     * check the map validation when load
     *
     * @return
     */

    public String Load() {
        // TODO Auto-generated method stub
        String rtnMessage = " Load map successfully";
        JFrame frm = new JFrame("Load Map");
        frm.setAlwaysOnTop(true);
        JPanel p = new JPanel();
        JFileChooser fc = new JFileChooser();
        Container c = frm.getContentPane();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("map file", "map");
        fc.setFileFilter(filter);
        File f;
        int flag = 0;

        c.setLayout(new FlowLayout());
        c.add(p);
        fc.setDialogTitle("Open File");

        try {
            File filedir = new File("/Users/mengranli/eclipse-workspace/mapView/");
            fc.setCurrentDirectory(filedir);
            flag = fc.showOpenDialog(frm);
        } catch (HeadlessException head) {
            System.out.println("Open File Dialog ERROR!");
        }

        if (flag == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            String fileName = fc.getCurrentDirectory() + "/" + fc.getName(f);


            rtnMessage = loadMap(fileName);
        }
        return rtnMessage;
    }


}
