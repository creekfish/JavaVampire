package game.text;

public class ResultPartial extends ResultGeneric {
	private ActionContinued action;
	
	public ResultPartial(boolean isSuccessful, String message, ActionContinued action) {
		super(isSuccessful, message);
		this.action = action;
	}

	public ActionContinued getContinuedAction() {
		return action;
	}	
}
