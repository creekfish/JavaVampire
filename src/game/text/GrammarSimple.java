package game.text;

public class GrammarSimple implements Grammar {
	private TextGame game;
//	private Player player;
	private Action verb;
	private Actionable object;
	
	public GrammarSimple(TextGame game) {
		this.game = game;
//		this.player = game.getPlayer();
		verb = null;
		object = null;
	}
	
	public GrammarSimple(TextGame game, final String input) {
		this.game = game;
//		this.player = game.getPlayer();
		verb = null;
		object = null;
		parse(input);
	}

	public boolean isParsed() {
		return (verb != null);
	}

	public boolean parse(final String input) {
		object = null;
		verb = null;
		String lower = input.toLowerCase();
		String part[] = lower.split("\\s");
		if (part.length > 0) {
			if (part.length > 1) {
				// find the object and get the action for that object
				Item item = game.matchItem(part[part.length-1]);
				if (item != null) {
					object = item;
					verb = object.matchAction(part[0]);
					if (verb != null) {
						return true;   // verb and object parsed
					}
					return false;  // verb not found for this object
				}
				// find the object and get the action for that object
				Direction dir = game.matchDirection(part[part.length-1]);
				if (dir != null) {
					object = dir;
					verb = object.matchAction(part[0]);
					if (verb != null) {
						return true;   // verb and object parsed
					}
					return false;  // verb not found for this object
				}
				// find the object and get the action for that object
				Place place = game.matchPlace(part[part.length-1]);
				if (place != null) {
					object = place;
					verb = object.matchAction(part[0]);
					if (verb != null) {
						return true;   // verb and object parsed
					}
					return false;  // verb not found for this object
				}
			}
			// apply verb with no object
			String action = part[0];
			// game actions
			verb = game.matchAction(action);
			if (verb != null) {
				return true;
			} else {
				// "go" direction shortcuts
				for (Direction dir : game.getDirections()) {
					if (dir.getName().toLowerCase().startsWith(action)) {
						verb = dir.matchAction("go");
						return true;
					}
				}
				return false;  // verb not found at all				
			}
		}
		
		return false;
	}	
	
	public Actionable getObject() {
		return object;
	}

	public Action getVerb() {
		return verb;
	}
}
