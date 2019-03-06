package model;

/**
 * This is the class used to define the card type
 * @author
 *
 */
public enum CardType {
	
	/** The infantry. */
	Infantry(0),
	
	/** The cavalry. */
	Cavalry(1),
	
	/** The artillery. */
	Artillery(2);
    
	/** The card type number. */
	private int CardTypeNumber;
    
	/**
	 * Instantiates a new card type.
	 * @param cardTypenumber the card type number.
	 */
	private CardType(int cardTypenumber) {
        this.CardTypeNumber = cardTypenumber;
    }
    
	/**
	 * Gets the card type number.
	 * @return the card type number.
	 */
	public int getCardTypeNumber(){
    	return CardTypeNumber;

}

}
