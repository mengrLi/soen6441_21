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
	 * @param cardTypeCode the card type code
	 */
	private CardType(int cardTypenumber) {
        this.CardTypeNumber = cardTypenumber;
    }
    
	/**
	 * Gets the card type code.
	 * @return the card type code
	 */
	public int getCardTypeCode(){
    	return CardTypeNumber;

}

}
