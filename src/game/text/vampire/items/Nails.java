package game.text.vampire.items;

import game.text.Container;
import game.text.Game;
import game.text.Item;
import game.text.ItemGeneric;
import game.text.Place;
import game.text.exceptions.ActionException;

public class Nails extends ItemGeneric {
	private Game game;

	public Nails(Game game) {
		super("Nails", "");
		this.game = game;
	}

	@Override
	public void move(Container<Item> destination) throws ActionException {
		if (destination instanceof Place) {  
			super.move(destination);
		} else {
			if (!game.getPlayer().hasOne("sledge hammer")) {  // if the player doesn't have a hammer 
				throw new ActionException("You have no hammer");
			} else {
				if (!((Tapestry) game.getItem("tapestry")).isLoose() && 
						game.getPlace("overhang").hasOne(game.getItem("nails"))) {
					// it's a quirk in the old game that you can loosen the tapestry any time the nails are here
					((Tapestry) game.getItem("tapestry")).tapestryLoose();  // signal that tapestry is loose
					super.move(destination);
					throw new ActionException("The tapestry is loose\nYou got the Nails");
				} else {
					// just move the nails after the tapestry is loose 
					super.move(destination);
				}
			}
		}
	}
	
}
