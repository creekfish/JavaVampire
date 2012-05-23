package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class Fireplace extends PlaceGeneric {	
	private Game game;
	
	public Fireplace(Game game)  throws ActionException {
		super("Brick Fireplace", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("torch"));
	}
}
