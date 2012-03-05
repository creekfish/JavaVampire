package game.text.vampire.items;

import game.text.Container;
import game.text.Game;
import game.text.Item;
import game.text.ItemGeneric;
import game.text.Place;
import game.text.exceptions.ActionException;

public class Sign extends ItemGeneric {
	private Game game;
	
	public Sign(Game game) {
		super("Sign", "'The Vampire Wakes at Midnight!'");
		this.game = game;
	}
	
	@Override
	public void move(Container<Item> destination) throws ActionException {
		// disable player getting this item
		if (destination instanceof Place) {
			super.move(destination);
		} else {
			throw new ActionException("You can't get it");
		}
	}

	@Override
	public String look() {
		return game.getItem("sign").getDescription();
	}	
}
