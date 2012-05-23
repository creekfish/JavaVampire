package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class Chapel extends PlaceGeneric {	
	private Game game;
	
	public Chapel(Game game)  throws ActionException {
		super("Chapel", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("holywater"));
	}
}
