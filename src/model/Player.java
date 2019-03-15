package model;

import java.awt.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This is the class of player
 *
 */
public class Player {
    private String name;
    private int ID;
    public Color color;
    private Country capital;
    private HashMap<Card,Integer> cards = new HashMap<>();
    private ArrayList<Country> countriesOwned = new ArrayList<>();
    private ArrayList<Card> cardList = new ArrayList<Card>();
    private ArrayList<Army> armyList = new ArrayList<>();

    /**
     * Gets the card list.
     * @return the card list
     */
    public ArrayList<Card> getCardList() {
        return cardList;
    }


    /**number of times player is given army for cards */
    private int timesArmyforCards = 0;

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
//
//    /**
//     * Set the color of a player
//     * @param color is the color randomly assigned to players at the start up phase
//     */
////    public void setColor(java.awt.Color color) {
////    	int pick = new Random().nextInt(java.awt.Color.values().length);
////    	this.color = java.awt.Color.values()[pick];
////    }
//    public Country getCapital(){
//        return capital;
//    }
//
//    public void setCapital(Country capital){
//         this.capital = capital;
//    }


    /**
     * Get the color of a player
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
       return  this.armyList;
    }

    public void setArmyList(int armyNum) {
        for(int i=0;i<armyNum;i++) {
            Army army = new Army(this);
            armyList.add(army);
        }
    }

    /**
     * Sets the times army for cards.
     * @param i the new army for cards
     */
    public void settimesArmyforCards(int i) {
        timesArmyforCards = i;
    }

    /**
     * This method gets a random new card for the player and add to the cardList.
     */
    public void getNewCard()
    {
        Card c=new Card(this);
        this.cardList.add(c);
    }


    /**
     * This method determine whether cards must be exchanged.
     * @return if true the player has to exchange card for army and the conditions must be met.
     */
    public boolean mustExchangeCard() {
        return (cardList.size()>=5);
    }

    /**
     * This method can directly exchange 3 cards for army and remove 3 cards from the cardList.
     */
    public int autoExchangeCardforArmy() {

        if(this.getCardList().size()<=4){
            return 0;
        }

        System.out.println("has sucessfully exchanged cards");

        int armyForCard = (timesArmyforCards += 1) * 5;

        for(int i=0; i<armyForCard; i++) {
            Army army = new Army(this);
            armyList.add(army);
        }
        //after exchange army then remove the cards.
        int[] cardTypeNumber = cardTypeNumber();
        //remove 3 of them with the same type
        for (int count=0; count<3; count++) {
            if (cardTypeNumber[count]>=3){
                for (int i=0; i<3; i++){
                    removeCard(count);
                }

            }
            else{

                //else remove one of each type.
                removeCard(0);
                removeCard(1);
                removeCard(2);

            }
        }
        return armyForCard;

    }
    
    /**
     * This method can  exchange 3 cards for army and remove 3 cards from the cardList according to the exchange way.
     * return the number of army for card.
     */
    public int ExchangeCardforArmy(String exchangeway) {

        System.out.println("has sucessfully exchanged cards");
        String way=exchangeway;
        int armyForCard = (timesArmyforCards += 1) * 5;
        for(int i=0; i<armyForCard; i++) {
            Army army = new Army(this);
            armyList.add(army);
        }
        //after exchange army then remove the cards.
        //remove 3 of them with the same type
        if(way=="Exchange 3 Type0 Cards for Armies"){
        	for (int i=0; i<3; i++){
                removeCard(0);
            }
        }
        if(way=="Exchange 3 Type1 Cards for Armies"){
        	for (int i=0; i<3; i++){
                removeCard(1);
            }
        }
        if(way=="Exchange 3 Type2 Cards for Armies"){
        	for (int i=0; i<3; i++){
                removeCard(2);
            }
        }
        if(way=="Exchange 1 Type0 1 Type1 and 1 Type2 Card for Armies"){
        	//else remove one of each type.
            removeCard(0);
            removeCard(1);
            removeCard(2);

        }
        
        return armyForCard;

    }
    /**
     * This method remove a card from cardList of player.
     * @param cardTypeCode
     */
    public void removeCard(int cardTypeCode) {
        for(Card card: cardList){
            if (card.getCardType().getCardTypeNumber() == cardTypeCode) {
                cardList.remove(card);

                break;
            }
        }
    }


        /**
         * This method get the number of each card type.
         * @return number of each card type
         */
    public int[] cardTypeNumber() {
        int[] cardTypeNumber = {0,0,0};
        for(Card card: cardList){
            cardTypeNumber[card.getCardType().getCardTypeNumber()] += 1;
        }
        return cardTypeNumber;
    }
    
    
    
    /**
     * This method provide the way to exchange Cards for Armies.
     * @return ArrayList.
     */
    public ArrayList<String> chooseExchangeWay(){
    	
    	ArrayList<String> exchangeway=new ArrayList<String>();
    	int[] cardTypeNumber = cardTypeNumber();
    	if(ExchangeCard()){
    		if(cardTypeNumber[0]>=3 && Math.max(cardTypeNumber[1], cardTypeNumber[2])<3){
    			exchangeway.add("Exchange 3 Type0 Cards for Armies");
    		}
    		if(cardTypeNumber[1]>=3 && Math.max(cardTypeNumber[0], cardTypeNumber[2])<3){
    			exchangeway.add("Exchange 3 Type1 Cards for Armies");
    		}
    		if(cardTypeNumber[2]>=3 && Math.max(cardTypeNumber[1], cardTypeNumber[0])<3){
    			exchangeway.add("Exchange 3 Type2 Cards for Armies");
    		}
    		if(Math.min(cardTypeNumber[0], cardTypeNumber[1])>=3 && cardTypeNumber[2]<3){
    			exchangeway.add("Exchange 3 Type0 Cards for Armies");
    			exchangeway.add("Exchange 3 Type1 Cards for Armies");
    		}
    		if(Math.min(cardTypeNumber[0], cardTypeNumber[2])>=3 && cardTypeNumber[1]<3){
    			exchangeway.add("Exchange 3 Type0 Cards for Armies");
    			exchangeway.add("Exchange 3 Type2 Cards for Armies");
    		}
    		if(Math.min(cardTypeNumber[1], cardTypeNumber[2])>=3 && cardTypeNumber[0]<3){
    			exchangeway.add("Exchange 3 Type1 Cards for Armies");
    			exchangeway.add("Exchange 3 Type2 Cards for Armies");
    		}
    		if(Math.min(cardTypeNumber[0], Math.min(cardTypeNumber[1], cardTypeNumber[2])) >= 1){
    			exchangeway.add("Exchange 1 Type0 1 Type1 and 1 Type2 Card for Armies");
    			if(Math.min(cardTypeNumber[0], Math.min(cardTypeNumber[1], cardTypeNumber[2])) >= 3){
    				exchangeway.add("Exchange 3 Type0 Cards for Armies");
    				exchangeway.add("Exchange 3 Type1 Cards for Armies");
    				exchangeway.add("Exchange 3 Type2 Cards for Armies");
    			}
    		}
    	}
    	else{
    		System.out.println("You don't meet the exchange condition");
    	}
    	
    	return exchangeway;
    			
    }

    /**
     * This method determine the conditions for card type exchange.
     * @return true if player can exchange card for army
     */
    public boolean ExchangeCard() {
        int[] cardTypeNumber = cardTypeNumber();
        return (Math.max(cardTypeNumber[0], Math.max(cardTypeNumber[1], cardTypeNumber[2])) >= 3)
                || (Math.min(cardTypeNumber[0], Math.min(cardTypeNumber[1], cardTypeNumber[2])) >= 1);
    }



   /* /**
     * This method can get the number of armies.
     * @param removecards is the list of cards to be removed.
     *@return the number of armies
     */
   /* public int totalExchangeArmy(List<Card> removecards) {
        removeCards(removecards);
        int armyForCard = (this.timesArmyforCards += 1) * 5;

        for(int i=0; i<armyForCard; i++) {
            Army army = new Army(this);
            armyList.add(army);
        }
        return armyForCard;
    }*/

   
   /* /**
     * This method remove multiple cards from cardList of player.
     * @param removecards is the list of cards to be removed.
     */
   /* public void removeCards(List<Card> removecards) {
        cardList.removeAll(removecards);

    }*/

    /**
     * Get How many army player can get at startup phase
     * @return a number of army
     * */
    public int NumberOfArmy(){
        int numOfArmy = 0;
        numOfArmy = countriesOwned.size()/3;
        return numOfArmy;
    }

    /**
     * Get How many army player can get at Reinforcements phase
     * @return a number of army
     * */
    public int getNumberOfArmy(){
        int numOfArmy = 0;
        System.out.println("countriesOwned.size()/3 :" +countriesOwned.size()/3);
        numOfArmy = countriesOwned.size()/3
                + getBonusofContinents()
                + exchangeCard();

        return numOfArmy;
    }

    /**
     * Get How many bonus of continents can get
     * @return a number of army
     * */
    public int getBonusofContinents(){
        int bonus = 0;
        HashMap<Continent,Integer> groupByContinent =new HashMap<Continent,Integer>();
        for(Country country : countriesOwned){
           Continent continent = country.getContinent();
            if(groupByContinent.containsKey(continent)){
                groupByContinent.put(continent,groupByContinent.get(continent)+1);
            }else{
                groupByContinent.put(continent,1);
            }

        }
        for(Continent continent : groupByContinent.keySet()){
            if(continent.getCountryList().size() == groupByContinent.get(continent)){
                bonus = + continent.getValue();
            }
        }
        System.out.println("bonus :" + bonus);
        return bonus;
    }


    public int exchangeCard(){
        int numberOfArmy= 0;
        numberOfArmy=this.autoExchangeCardforArmy();
        return numberOfArmy;
    }

    @Override
    public String toString() {
        System.out.println(ID + "-" + name);
        return ID + "-" + name;
    }
}

