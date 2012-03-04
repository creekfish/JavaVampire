package game.text.vampire.items;

import java.util.Collection;

import game.text.Action;
import game.text.ItemGeneric;

public class Timepiece extends ItemGeneric {

	public Timepiece(String name, String description) {
		super(name, description);
	}

	public Timepiece(String name, String description, Collection<Action> actions) {
		super(name, description, actions);
	}

	@Override
	public String getDescription() {
NEED TO RETURN GAME TIME HERE... BUT THERE IS NO ACCESS TO GAME... WOULD HAVE TO BE AN INNER CLASS ANON
OR... WOULD HAVE TO PASS game OBEJCT IN CONSTRUCTORS...
		return 
	}
}
