package model;
import java.util.Random;

/**
 * This is the card class We use the class to define player's card
 * 
 * @author
 *
 */

public class Card {
	
	/** The player. */
	private Player player;
	
	/** The card type. */
	private CardType cardType;
	
	/**
	 * Gets the player.
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Sets the player.
	 * @param player the new player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * Gets the card type.
	 * @return the card type
	 */
	public CardType getCardType() {
		return cardType;
	}
	public String getCardTypeName() {
		return cardType.name();
	}
	
	/**
	 * Sets the card type.
	 * @param cardType the new card type
	 */
	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}
	
	/**
	 * Instantiates a new card.
	 * @param player the player
	 */
	public Card(Player player) {
		this.player = player;
		Random random = new Random();
		int rand = random.nextInt(3);
		switch(rand) {
		case 0: 
			cardType = CardType.Infantry;
			break;
		case 1:
			cardType = CardType.Cavalry;
			break;
		case 2:
			cardType = CardType.Artillery;
			break;
		default:
			break;
		}
	}

	@Override
	public String toString() {
		return cardType.name();
	}
}
