package models;

public class Army {
	
	private Player player;
	
	private Country country;
	
	/**Creates a new army.*/
	public Army(Player player) {
		
		this.player = player;
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
	 * Gets the country.
	 */
	public Country getCountry() {
		return country;
	}
	
	/**
	 * Sets the country.
	 */
	public void setCountry(Country country) {
		this.country = country;
	}
	
	
}
