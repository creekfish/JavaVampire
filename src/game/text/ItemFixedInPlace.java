package game.text;

import java.util.Collection;

import game.text.exceptions.ActionException;

/**
 * An item that cannot be picked up and moved by the player.
 * @author bherring
 */
public class ItemFixedInPlace extends ItemGeneric {

	public ItemFixedInPlace(String name, String description, Collection<Action> actions) {
		super(name, description, actions);
	}

	public ItemFixedInPlace(String name, String description) {
		super(name, description);
	}		

	@Override
	public void move(Container<Item> destination) throws ActionException {
		// disable player moving this item
		if (destination instanceof Place) {  
			super.move(destination);
		} else {
			throw new ActionException("You can't get it");
		}
	}
}
