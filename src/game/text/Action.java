package game.text;

public interface Action {
	public String getName();
		
	/**
	 * Execute this action on the specified item in the given
	 * context and return the result (for that context).
	 * This should be atomic - either it all works or no
	 * state is affected.	
	 * @param object the "object of the action" - the object on which the
	 * action is to be performed
	 * @return the action result that was applied in the context
	 */
	public Result execute(Actor actor);

	/**
	 * Undo the last execute of this action.
	 */
	public boolean undo();
	
	/**
	 * Return the number of times this action has been performed.
	 */
	public int getCount();
}
