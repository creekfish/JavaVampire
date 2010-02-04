package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class Study extends PlaceGeneric {	
	private Game game;
	
	public Study(Game game)  throws ActionException {
		super("Study", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("fireplace"));
		this.moveItemHere(this.game.getItem("_fire"));
		this.moveItemHere(this.game.getItem("cheese"));
		this.moveItemHere(this.game.getItem("wine"));
	}
}
