package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceGeneric;
import game.text.exceptions.ActionException;

public class EntranceHall extends PlaceGeneric {	
	private Game game;
	
	public EntranceHall(Game game)  throws ActionException {
		super("Entrance Hall", "");
		this.game = game;
		this.moveItemHere(this.game.getItem("timepiece"));
		this.moveItemHere(this.game.getItem("sign"));
	}
}
