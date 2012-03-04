package game.text;

import java.util.HashMap;
import java.util.Map;

public abstract class ActionableGeneric implements Actionable {
	/** actions that can be performed on this type of object */
	private Map<String,Action> actions = new HashMap<String,Action>();
	
	public ActionableGeneric() {
		setDefaultActions();
	}
	
	public void addAction(Action action) {
		this.actions.put(action.getName().toLowerCase(), action);
	}

	public void addAction(String key, Action action) {
		this.actions.put(key, action);
	}

	public Action getAction(String actionKey) {
		return this.actions.get(actionKey.toLowerCase());
	}

	public Action matchAction(String actionKeyStartsWith) {
		for (String actionName : actions.keySet()) {
			if (actionName.startsWith(actionKeyStartsWith)) {
				return actions.get(actionName);
			}
		}
		return null;
	}

	@Override
	abstract public void setDefaultActions();
	
	public void removeAction(String actionKey) {
		this.actions.remove(actionKey);
	}

	public void removeAction(Action action) {
		this.actions.remove(action.getName().toLowerCase());
	}

	public Result executeAction(String actionKey, Actor actor) {
		if (actions.containsKey(actionKey)) {
			Action action = actions.get(actionKey);
			return action.execute(actor);
		}
		return null;
	}
}
