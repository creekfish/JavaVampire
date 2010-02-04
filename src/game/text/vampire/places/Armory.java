package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class Armory extends PlaceGeneric {	
	private Game game;
	
	public Armory(Game game)  throws ActionException {
		super("Armory", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("axe"));
	}
}
