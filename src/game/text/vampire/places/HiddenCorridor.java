package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class HiddenCorridor extends PlaceGeneric {	
	private Game game;
	
	public HiddenCorridor(Game game)  throws ActionException {
		super("Hidden Corridor", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("rope"));
	}
}
