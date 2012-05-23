package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class LowerTower extends PlaceGeneric {	
	private Game game;
	
	public LowerTower(Game game)  throws ActionException {
		super("Lower Tower", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("oar"));
	}
}
