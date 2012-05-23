package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceDarkWithoutItem;
import game.text.exceptions.ActionException;

public class Antechamber extends PlaceDarkWithoutItem {	
	private Game game;
	
	public Antechamber(Game game)  throws ActionException {
		super("Antechamber", "", game.getItem("torch"));
		this.game = game;
		this.moveItemHere(this.game.getItem("rusty door"));
	}
}
