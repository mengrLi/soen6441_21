package GameModel;

import GameModel.StrategyPlayer.Strategy;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the class of player
 */
public class Player {
    private String name;
    private int ID;
    public Color color;
    private HashMap<Card, Integer> cards = new HashMap<>();
    private ArrayList<Country> countriesOwned = new ArrayList<>();
    private ArrayList<Card> cardList = new ArrayList<Card>();
    private ArrayList<Army> armyList = new ArrayList<>();
    private int armyForCard = 0;
    private Strategy strategy;
    public boolean isAlive = true;

    /**
     * Gets the card list.
     *
     * @return the card list
     */
    public ArrayList<Card> getCardList() {
        return cardList;
    }


    /**
     * number of times player is given army for cards
     */
    private int timesArmyforCards = 0;

    /**
     * number of card type
     */
    private int[] cardTypeNumber = {0, 0, 0};

    /**
     * This is the constructor of player
     *
     * @param ID is the ID of each player defined by user
     */
    public Player(int ID) {
        this.ID = ID;
    }

    /**
     * Set the name of a player
     *
     * @param name is the name of a player
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of a player
     *
     * @return string name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the ID of a player
     *
     * @return int ID
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Get the color of a player
     *
     * @return color
     */
    public Color getColor() {
        return this.color;
    }


    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Set the countryList owned by a player
     *
     * @param countryList is a list of countries owned by a player
     */
    public void setCountriesOwned(ArrayList<Country> countryList) {
        this.countriesOwned = countryList;
    }

    /**
     * Get the list of countries owned by a player
     *
     * @return ArrayLis<Country>
     */
    public ArrayList<Country> getCountriesOwned() {
        return this.countriesOwned;
    }

    /**
     * Get the list of army owned by a player
     *
     * @return array list of army
     */
    public ArrayList<Army> getArmyList() {
        return this.armyList;
    }

    public void setArmyList(int armyNum) {
        for (int i = 0; i < armyNum; i++) {
            Army army = new Army(this);
            armyList.add(army);
        }
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Sets the times army for cards.
     *
     * @param i the new army for cards
     */
    public void settimesArmyforCards(int i) {
        timesArmyforCards = i;
    }

    /**
     * Gets the number of card type.
     *
     * @return i the new army for cards
     */
    public HashMap<String, Integer> getcardTypeNumber() {
        HashMap<String, Integer> cardMap = new HashMap<String, Integer>();
        for (Card card : cardList) {
            String cardType = card.getCardTypeName();
            if (cardMap.containsKey(cardType)) {
                cardMap.put(cardType, cardMap.get(cardType) + 1);
            } else {
                cardMap.put(cardType, 1);
            }
        }
        System.out.println("getcardTypeNumber :" + cardMap);
        return cardMap;
    }

    /**
     * This method gets a random new card for the player and add to the cardList.
     */
    public void getNewCard() {
        Card c = new Card(this);
        this.cardList.add(c);
    }

    public void getListNewCard() {
        for (int i = 0; i < 10; i++) {

            Card c = new Card(this);
            this.cardList.add(c);
        }

    }


    /**
     * This method can  exchange 3 cards for army and remove 3 cards from the cardList according to the exchange way.
     * return the number of army for card.
     */
    public int ExchangeCardforArmy(String exchangeway) {
        System.out.println("has sucessfully exchanged cards");
        String way = exchangeway;
        int armyForCard = (this.timesArmyforCards += 1) * 5;
        //after exchange army then remove the cards.
        //remove 3 of them with the same type
        if (way == "Infantry (3)") {
            for (int i = 0; i < 3; i++) {
                removeCard("Infantry");
            }

        }
        if (way == "Cavalry (3)") {
            for (int i = 0; i < 3; i++) {
                removeCard("Cavalry");
            }

        }
        if (way == "Artillery (3)") {
            for (int i = 0; i < 3; i++) {
                removeCard("Artillery");
            }

        }
        if (way == "Infantry (1),  Cavalry (1), Artillery (1)") {
            //else remove one of each type.
            removeCard("Infantry");
            removeCard("Cavalry");
            removeCard("Artillery");

        }
        ;
        //当轮可累计换牌
        this.armyForCard = this.armyForCard + armyForCard;
        return armyForCard;
    }

    /**
     * This method remove a card from cardList of player.
     *
     * @param cardType
     */
    public void removeCard(String cardType) {

        for (Card card : cardList) {
            if (card.getCardTypeName() == cardType) {
                cardList.remove(card);
                break;
            } else {

                System.out.println("You can't remove cards");
            }
        }
    }


    /**
     * This method provide the way to exchange Cards for Armies.
     *
     * @return ArrayList.
     */
    public ArrayList<String> chooseExchangeWay() {
        int InfantryCardNum = 0;
        int CavalryCardNum = 0;
        int ArtilleryCardNum = 0;

        ArrayList<String> exchangeway = new ArrayList<String>();
        if (getcardTypeNumber().containsKey("Infantry")) {
            InfantryCardNum = getcardTypeNumber().get("Infantry");
        }
        if (getcardTypeNumber().containsKey("Cavalry")) {
            CavalryCardNum = getcardTypeNumber().get("Cavalry");
        }
        if (getcardTypeNumber().containsKey("Artillery")) {
            ArtilleryCardNum = getcardTypeNumber().get("Artillery");
        }

        if (InfantryCardNum >= 3) {
            exchangeway.add("Infantry (3)");
        }
        if (CavalryCardNum >= 3) {
            exchangeway.add("Cavalry (3)");
        }
        if (ArtilleryCardNum >= 3) {
            exchangeway.add("Artillery (3)");
        }
        if (InfantryCardNum >= 1 && CavalryCardNum >= 1 && ArtilleryCardNum >= 1) {
            exchangeway.add("Infantry (1),  Cavalry (1), Artillery (1)");
        }

        return exchangeway;

    }


    /**
     * Get How many army player can get at Reinforcements phase
     *
     * @return a number of army
     */
    public int getNumberOfArmy() {
        int numOfArmy = 0;
        System.out.println("countriesOwned.size()/3 :" + countriesOwned.size() / 3);
        numOfArmy = countriesOwned.size() / 3
                + getBonusofContinents()
                + this.armyForCard;

        //reset the number of armyForCard
        this.armyForCard = 0;
        return numOfArmy;
    }

    /**
     * Get How many bonus of continents can get
     *
     * @return a number of army
     */
    public int getBonusofContinents() {
        int bonus = 0;
        HashMap<Continent, Integer> groupByContinent = new HashMap<Continent, Integer>();
        for (Country country : countriesOwned) {
            Continent continent = country.getContinent();
            if (groupByContinent.containsKey(continent)) {
                groupByContinent.put(continent, groupByContinent.get(continent) + 1);
            } else {
                groupByContinent.put(continent, 1);
            }

        }
        for (Continent continent : groupByContinent.keySet()) {
            if (continent.getCountryList().size() == groupByContinent.get(continent)) {
                bonus = +continent.getValue();
            }
        }
        System.out.println("bonus :" + bonus);
        return bonus;
    }


//    public int exchangeCard(){
//        int numberOfArmy= 0;
//        numberOfArmy=this.autoExchangeCardforArmy();
//        return numberOfArmy;
//    }
    public void takeOverCards(Player defender) {
        this.cardList.addAll(defender.getCardList());
        System.out.println(name + "cardList: " + cardList);
    }

    @Override
    public String toString() {
        System.out.println(ID + "-" + name);
        return ID + "-" + name;
    }

    public void autoReinforce(){
        this.strategy.autoReinforce(this);
    }

    public boolean autoAttack(){
        boolean ifWinned = false;
        ifWinned = this.strategy.autoAttack(this);
        return  ifWinned;
    }

    public void autoFortify(){
        this.strategy.autoFortify(this);
    }

    public String playerSave(){
        String info = "";
        String cardInfo  = "";
        String countryInfo = "";

        for(Card card : cardList){
            cardInfo  = cardInfo + card.getCardType().name() + " ";
        }
        cardInfo = cardInfo.trim();
        System.out.println("cardInfo : "+ cardInfo);

        for(Country country : countriesOwned){
            countryInfo  = countryInfo + country.getName() + "-" + country.getArmiesNum() + " ";
        }
        countryInfo = countryInfo.trim();
        System.out.println("countryInfo : "+ countryInfo);

        info =    ID + ","
                + name + ","
                + cardInfo + ","
                + countryInfo + ","
                + getArmyList().size() + ","
                + getStrategy() != null ? getStrategy().getClass().getSimpleName() : "Human";
        return info;
    }

    public void resetPlayer(){
        countriesOwned = new ArrayList<>();
        cardList = new ArrayList<Card>();
        armyList = new ArrayList<>();
        armyForCard = 0;
        isAlive = true;
    }


}
