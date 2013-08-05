package game.text;

public interface Action {
	public String getName();

	/**
	 * Undo the last execute of this action.
	 */
	public boolean undo();
	
	/**
	 * Return the number of times this action has been performed.
	 */
	public int getCount();
}
