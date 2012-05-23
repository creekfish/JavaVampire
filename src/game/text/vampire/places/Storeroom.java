package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class Storeroom extends PlaceGeneric {	
	private Game game;
	
	public Storeroom(Game game)  throws ActionException {
		super("Storeroom", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("crate"));
		this.moveItemHere(this.game.getItem("bucket"));
	}
}
