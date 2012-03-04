package game.text;

public interface Result {
	/**
	 * Return true if the result was successful, false if it failed 
	 */
	public boolean isSuccess();

	/**
	 * Set the message for this result that needs to be
	 * communicated to the player.
	 */
	public void setMessage(String message);
	
	/**
	 * Return the message for this result.
	 */
	public String getMessage();
}
