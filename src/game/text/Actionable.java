package game.text;

public interface Actionable {
	public void addAction(Action action);
	public void addAction(String key, Action action);
	public Action getAction(String actionKey);
	public ActionInitial getInitialAction(String actionKey);
	public ActionInitial matchInitialAction(String actionKeyStartsWith);
	public void removeAction(Action action); 
	public void removeAction(String actionKey);
	public Result executeAction(String actionName, Actor actor);
	public void setDefaultActions();
}
