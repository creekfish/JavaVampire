package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceDarkWithoutItem;
import game.text.exceptions.ActionException;

public class VampiresTomb extends PlaceDarkWithoutItem {	
	private Game game;
	
	public VampiresTomb(Game game)  throws ActionException {
		super("Vampire's Tomb", "", game.getItem("torch"));
		this.game = game;
		this.moveItemHere(this.game.getItem("closed coffin"));
	}
}
