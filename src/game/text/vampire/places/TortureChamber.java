package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class TortureChamber extends PlaceGeneric {	
	private Game game;
	
	public TortureChamber(Game game)  throws ActionException {
		super("Torture Chamber", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("rat"));
		// rat has the key to start, so key is not in any "place" and cannot be picked up by the player
	}
}
