package game.text.vampire.items;

import game.text.Container;
import game.text.Item;
import game.text.ItemGeneric;
import game.text.Place;
import game.text.TextGame;
import game.text.exceptions.ActionException;

public class Key extends ItemGeneric {
	private TextGame game;
	
	public Key(TextGame _game) {
		super("Key", "");
		this.game = _game;
	}

	@Override
	public void move(Container<Item> destination) throws ActionException {
		if (destination instanceof Place) {
			super.move(destination);  // always allow key to be dropped any place
		} else {
			// player tries to pick up the key
			if (this.getData("can_see") != null) {  // player must "look" at the rat first to see the key 
				Place keyPlace = (Place) this.getLocation();
				if (game.getItem("rat").getLocation() != keyPlace) {
					// if the key is in a room without the rat and the player tries to pick it up,
					// the rat moves to that room and has the key again!
					game.getItem("rat").move(keyPlace);
					throw new ActionException("The Rat has it");
				}
				if (((game.getItem("rat").getLocation() == keyPlace) && (game.getItem("cheese").getLocation() == keyPlace))) {
					// key, rat, and cheese must all be in the room together to pick up the key!
					super.move(destination);
				} else {
					throw new ActionException("The Rat has it");
				}
			} else {
				throw new ActionException("You can't get it");
			}
		}
	}	

	@Override
	public String look() {
		if (this.getData("can_see") == null) {   // if the player hasn't looked at the key yet
			return null;     // not visible in the room 
		} else {
			return this.getName();
		}
	}	
}
