package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class AlchemistsLab extends PlaceGeneric {	
	private Game game;
	
	public AlchemistsLab(Game game)  throws ActionException {
		super("Alchemist's Lab", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("flask of oil"));
	}
}
