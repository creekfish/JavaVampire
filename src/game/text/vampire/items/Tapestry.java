package game.text.vampire.items;

import game.text.Container;
import game.text.Item;
import game.text.ItemGeneric;
import game.text.Place;
import game.text.TextGame;
import game.text.exceptions.ActionException;

public class Tapestry extends ItemGeneric {
	private TextGame game;
	private boolean nailsRemoved = false;
	
	public Tapestry(TextGame _game) {
		super("Tapestry", "");
		this.game = _game;
	}

	@Override
	public void move(Container<Item> destination) throws ActionException {
		if (destination instanceof Place) {  
			super.move(destination);
		} else {
			if (!nailsRemoved && game.getItem("nails").getLocation() == game.getPlace("overhang")) {  // if the nails are still in place 
				throw new ActionException("It's nailed to an overhang");
			} else {
				if (!game.getPlace("gallery").isExit(game.getDirection("north"))) {
					game.getPlace("gallery").connect(game.getPlace("antechamber"), game.getDirection("north"));			
					super.move(destination);
					throw new ActionException("AHA! - A hole in the wall\n\nYou got the Tapestry");
				} else {
					// just move the tapestry after the hole has been exposed
					super.move(destination);
				}
			}
		}
	}

	protected void tapestryLoose() {
		nailsRemoved = true;  // once nails are out, they're always out
	}

	public boolean isLoose() {
		return nailsRemoved;
	}
}
