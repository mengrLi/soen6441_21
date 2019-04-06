package GameModel;

import GameModel.StrategyPlayer.*;
import MapModel.Map;
//import com.sun.java.util.jar.pack.Instruction;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by liaoxiaoyun on 2019-04-03.
 */
public class GameLoad {
    public PlayerEngine game;
    public Map map;

    public GameLoad() {
        game = new PlayerEngine();
        map = Map.getMapInstance();
    }

    public String Loading(String fileName) {
        String result = "";
        game.restart();//before loading the game, clear the game first
        map.reset();
        String rtnMessage = "Load map succussfully";
        BufferedReader br = null;
        String inputLine = null;
        String dataArea = "";
        int index;
        int rowNum = 0;
        int totalContryNum = 0;

        try {
            br = new BufferedReader(new FileReader(fileName));
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
                        case "[Players]":
                            dataArea = "Players";
                            break;
                        case "[GameSate]":
                            dataArea = "GameSate";
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
                                                    map.author = value;
                                                    break;
                                                case "warn":
                                                    map.warn = value;
                                                    break;
                                                case "image":
                                                    map.warn = value;
                                                    break;
                                                case "wrap":
                                                    map.warn = value;
                                                    break;
                                                case "scroll":
                                                    map.warn = value;
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
                                            map.addContinent(continentName);
                                        }
                                    }
                                    break;
                                case "Territories":
                                    String[] countryInfo = inputLine.split(",");
                                    if (countryInfo.length >= 4) {
                                        //System.out.println("load country :" + countryInfo[0]);
                                        String countryName = countryInfo[0];
                                        int x = Integer.parseInt(countryInfo[1]);
                                        int y = Integer.parseInt(countryInfo[2]);

                                        Continent continent = map.continents.get(countryInfo[3].trim());
                                        //System.out.println("continet:  " + continent);
                                        String[] connectionCountries = new String[countryInfo.length - 4];
                                        System.arraycopy(countryInfo, 4, connectionCountries, 0, countryInfo.length - 4);

                                        List<String> connection = new ArrayList<>(50);
                                        connection.addAll(Arrays.asList(connectionCountries));

                                        map.addCountry(countryName, x, y, continent, connection, countryCount);
                                        countryCount++;

                                    }
                                    break;
                                case "Players":
                                    //"0,Cheater0,,Country5 Country2,true,Cheater"
                                    String[] playInfo = inputLine.split(",");
                                    int ID = Integer.parseInt(playInfo[0]);
                                    Player player = new Player(ID);
                                    player.setColor(game.playercolors[ID]);
                                    player.setName(playInfo[1]);
                                    game.getPlayerList().add(player);
                                    //cards
                                    if(!playInfo[2].isEmpty()){
                                        String[] cardInfo = playInfo[2].split(" ");
                                        for(String cardName : cardInfo ){
                                            Card card = new Card(player);
                                            player.getCardList().add(card);
                                            switch (cardName){
                                                case "Infantry": card.setCardType(CardType.Infantry);
                                                    break;
                                                case "Cavalry": card.setCardType(CardType.Cavalry);
                                                    break;
                                                case "Artillery": card.setCardType(CardType.Artillery);
                                                    break;
                                            }
                                        }
                                    }
                                    //ownCountries
                                    if(!playInfo[3].isEmpty()){
                                        String[] countriesInfo = playInfo[3].split(" ");
                                        for(String countryNameAndArmy : countriesInfo){
                                            String[] cinfo = countryNameAndArmy.split("-");
                                            Country country = map.getCountry(cinfo[0]);
                                            country.setPlayer(player);
                                            System.out.println("player country: " + country);
                                            player.getCountriesOwned().add(country);
                                            int armyNum = Integer.parseInt(cinfo[1]);
                                            for(int i = 0; i < armyNum ; i++){
                                                Army army = new Army(player);
                                                player.getArmyList().add(army);
                                                country.getArmyList().add(army);
                                            }
                                        }
                                    }
                                    //isAlive
                                    if(!playInfo[4].isEmpty()){
                                        player.isAlive = playInfo[4].equals("true") ? true : false;
                                    }
                                    //strategy
                                    if(!playInfo[5].isEmpty()){
                                        setStrategy(player,playInfo[5]);
                                    }
                                    break;
                                case "GameSate":
                                    index = inputLine.indexOf("=");
                                    if (index != -1) {
                                        String key = inputLine.split("=")[0].trim();
                                        String value = inputLine.split("=")[1].trim();
                                        if (!key.isEmpty()) {
                                            switch (key) {
                                                case "gameState":
                                                    game.state = setGameState(value);
                                                    break;
                                                case "warn":
                                                    game.currentPlayer = Integer.parseInt(value);
                                                    break;
                                                case "initialArmyNum":
                                                    game.initialArmyNum = Integer.parseInt(value);
                                                    break;
                                                case "round":
                                                    game.round = Integer.parseInt(value);
                                                    break;
                                                case "cardChangeFlage":
                                                    game.cardChangeFlage= Boolean.parseBoolean(value);
                                                    break;
                                                case "reinforceFlag":
                                                    game.reinforceFlag= Boolean.parseBoolean(value);
                                                    break;
                                                case "getCardFlag":
                                                    game.getCardFlag= Boolean.parseBoolean(value);
                                                    break;
                                                default:
                                                    rtnMessage = "Error in " + rowNum + "row";
                                                    break;
                                            }
                                        }
                                    }
                                    break;
                                default:
                                    return "Error in " + rowNum + "row";
                            }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            System.out.println("Error in " + rowNum + "row");
            e.printStackTrace();
        }
        map.coverConnectionMapToMatrix();
        System.out.println("playinfo: " + game.getPlayerList());
        return result;
    }

    public GameState setGameState(String stateName){
        GameState state = null;
        switch (stateName){
            case "CHOOSEMODEL" : state = GameState.CHOOSEMODEL;
                 break;
            case "EDITMAP" : state = GameState.EDITMAP;
                break;
            case "CHOOSEPLAYER" :state = GameState.CHOOSEPLAYER;
                break;
            case "ASSIGNROLES" : state = GameState.ASSIGNROLES;
                break;
            case "STARTUP" : state = GameState.STARTUP;
                break;
            case "CHOOSECARD" : state = GameState.CHOOSECARD;
                break;
            case "REINFORCE" : state = GameState.REINFORCE;
                break;
            case "ATTACK" : state = GameState.ATTACK;
                break;
            case "FORTIFY" : state = GameState.FORTIFY;
                break;
            case "END" : state = GameState.END;
                break;
        }
        return state;
    }

    public void setStrategy(Player player, String strategyName) {
        Strategy strategy;
        switch (strategyName) {
            case "Cheater":
                strategy = new Cheater();
                player.setStrategy(strategy);
                break;
            case "Aggressive":
                strategy = new Aggressive();
                player.setStrategy(strategy);
                break;
            case "Benevolent":
                strategy = new Benevolent();
                player.setStrategy(strategy);
                break;
            case "RandomP":
                strategy = new RandomP();
                player.setStrategy(strategy);
                break;
        }
    }


    public String LoadGame() {
        // TODO Auto-generated method stub
        String rtnMessage = " Load game successfully";
        JFrame frm = new JFrame("Load game");
        frm.setAlwaysOnTop(true);
        JPanel p = new JPanel();
        JFileChooser fc = new JFileChooser();
        Container c = frm.getContentPane();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("game file", "game");
        fc.setFileFilter(filter);
        File f;
        int flag = 0;

        c.setLayout(new FlowLayout());
        c.add(p);
        fc.setDialogTitle("Open File");

        try {
            File filedir = new File("/Users/mengranli/Desktop/6441-build3_2/");
            fc.setCurrentDirectory(filedir);
            flag = fc.showOpenDialog(frm);
        } catch (HeadlessException head) {
            System.out.println("Open File Dialog ERROR!");
        }

        if (flag == JFileChooser.APPROVE_OPTION) {
            f = fc.getSelectedFile();
            String fileName = fc.getCurrentDirectory() + "/" + fc.getName(f);


            rtnMessage = Loading(fileName);
        }
        return rtnMessage;
    }
}
