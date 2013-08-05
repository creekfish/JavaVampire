package game.text;

public abstract class ActionContinued extends ActionGeneric {

	public ActionContinued(String name) {
		super(name);
	}

	abstract public Result execute(Actor actor, String feedback);	
}
