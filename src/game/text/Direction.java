package game.text;

public interface Direction extends Actionable, Thing {
	/**
	 * Get the name of this direction.
	 */
	public String getName();

	/**
	 * Get the abbreviation of this direction (e.g. North = N)
	 */
	public String getAbbreviation();

	/**
	 * Set the direction that is opposite this one. Does not
	 * attempt to set the other's opposite to this to ensure
	 * logical consistency!
	 * @param opposite the direction specified as opposite of this one
	 */
	public void setOpposite(Direction opposite);
	
	/**
	 * Set the direction that is opposite this one. Will automatically
	 * set this direction as the opposite of the other as well so that
	 * both reference each other consistently.
	 * @param opposite the direction specified as opposite of this one
	 */
	public void makeOpposites(Direction opposite);

	/**
	 * Set the direction that is opposite this one.
	 * If you travel in this direction and then in the this.getOpposite()
	 * you must logically end up where you started.
	 * @param opposite the direction specified as opposite of this one
	 */
	public Direction getOpposite();
}
