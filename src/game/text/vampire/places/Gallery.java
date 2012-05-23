package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class Gallery extends PlaceGeneric {	
	private Game game;
	
	public Gallery(Game game)  throws ActionException {
		super("Gallery", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("tapestry"));
	}
}
