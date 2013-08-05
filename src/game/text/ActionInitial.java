package game.text;


public abstract class ActionInitial extends ActionGeneric {	

	public ActionInitial(String name) {
		super(name);
	}

	/**
	 * Execute this action on the specified item in the given
	 * context and return the result (for that context).
	 * This should be atomic - either it all works or no
	 * state is affected.	
	 * @param actor the actor that is taking the action
	 * @return the action result that was applied in the context
	 */
	abstract public Result execute(Actor actor);	
}
