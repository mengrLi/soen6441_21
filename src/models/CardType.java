package models;

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
	 * @param cardTypeNumber the card type number
	 */
	private setCardTypeNumber(int cardTypenumber) {
        this.CardTypeNumber = cardTypenumber;
    }
    
	/**
	 * Gets the card type number.
	 * @return the card type number
	 */
	public int getCardTypeNumber(){
    	return CardTypeNumber;

}

}
