package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class Tower extends PlaceGeneric {	
	private Game game;
	
	public Tower(Game game)  throws ActionException {
		super("Tower", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("_parapets"));
		this.moveItemHere(this.game.getItem("hammer"));
	}
}
