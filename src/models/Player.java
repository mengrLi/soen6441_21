package models;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Observable;

public class Player extends Observable{

	private String name;
    private int ID;
    private ArrayList<Country> countriesOwned = new ArrayList<>();
    private ArrayList<Card> cardList = new ArrayList<Card>();
    private ArrayList<Army> armyList = new ArrayList<Army>();
    
    /**number of times player is given army for cards銆� */
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
     * @return ID
     */
    public int getID() {
    	return this.ID;
    }
    
    /**
	 * Sets the id.
	 * @param id the new id
	 */
	public void setID(int id) {
		this.ID = id;
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
	 * Gets the card list.
	 * @return the card list
	 */
	public ArrayList<Card> getCardList() {
		return cardList;
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
		
		setChanged();
		notifyObservers(this);
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
	public void autoExchangeCardforArmy() {

		if(this.getCardList().size()<=4){
			return;
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
				return;
			}
		}
		//else remove one of each type.
		removeCard(0);removeCard(1);removeCard(2);
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
	 * This method determine the conditions for card type exchange.
	 * @return true if player can exchange card for army
	 */
	public boolean ExchangeCard() {
		int[] cardTypeNumber = cardTypeNumber();
		return (Math.max(cardTypeNumber[0], Math.max(cardTypeNumber[1], cardTypeNumber[2])) >= 3)
				|| (Math.min(cardTypeNumber[0], Math.min(cardTypeNumber[1], cardTypeNumber[2])) >= 1);
	}

	
	
	/**
	 * This method can get the number of armies.
	 * @param removecards is the list of cards to be removed.
	 *@return the number of armies
	 */
	public int totalExchangeArmy(List<Card> removecards) {
		removeCards(removecards);
		int armyForCard = (this.timesArmyforCards += 1) * 5;

		for(int i=0; i<armyForCard; i++) {
			Army army = new Army(this);
			armyList.add(army);
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
				
				setChanged();
				notifyObservers(this);
				break;
			}
		}
	}
	/**
	 * This method remove multiple cards from cardList of player.
	 * @param removecards is the list of cards to be removed.
	 */
	public void removeCards(List<Card> removecards) {
		cardList.removeAll(removecards);
		
		setChanged();
		notifyObservers(this);
	}
}
	

