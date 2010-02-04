package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class Library extends PlaceGeneric {	
	private Game game;
	
	public Library(Game game)  throws ActionException {
		super("Library", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("scroll"));
		this.moveItemHere(this.game.getItem("bookcase"));
	}
}
