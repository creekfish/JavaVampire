package game.text;


public abstract class ActionGeneric implements Action {
	private String name;
	private int doneCount = 0;
	
	public ActionGeneric(String name) {
		this.name = name;
	}

	protected void incrementCount() {
		this.doneCount++;
	}
	
	protected void decrementCount() {
		this.doneCount--;
	}

	public String getName() {
		return name;
	}

	public int getCount() {
		return this.doneCount;
	}
	
	/**
	 * Extremely basic undo that just decrements
	 * the count for this action.
	 * Must override to reset specific state.
	 */
	public boolean undo() {
		doneCount--;
		return true;
	}
}
