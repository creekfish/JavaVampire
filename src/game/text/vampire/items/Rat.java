package game.text.vampire.items;

import game.text.Container;
import game.text.Item;
import game.text.ItemFixedInPlace;
import game.text.Place;
import game.text.TextGame;
import game.text.exceptions.ActionException;

public class Rat extends ItemFixedInPlace {
	private TextGame game;
	
	public Rat(TextGame _game) {
		super("Rat", "");
		this.game = _game;
		this.setData("has_key", new Boolean(true));   // the rat has the key in it's mouth to start the game
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

	@Override
	public String look() {
		if (this.getLocation() != game.getPlayer().getLocation()) {
			return "I don't see any "+this.getName();
		}

		if (this.getData("has_key") != null) {   // if the rat still has the key
			game.getItem("key").setData("can_see", new Boolean(true));  // looking at it makes the key visible in the room
		}
		// looking at the rat when it does or doesn't have the key
		// causes the key to jump to the same room as the rat (bug in the old game!)
		try {
			game.getItem("key").move(game.getItem("rat").getLocation());
		} catch (ActionException e) {
			// if this doesn't work, ignore it - it's a bug anyway!
		}
		return "A Key in it's mouth!";
	}	
}
