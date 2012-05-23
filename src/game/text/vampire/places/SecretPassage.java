package game.text.vampire.places;

import game.text.Game;
import game.text.PlaceDarkWithoutItem;
import game.text.exceptions.ActionException;

public class SecretPassage extends PlaceDarkWithoutItem {	
	private Game game;
	
	public SecretPassage(Game game)  throws ActionException {
		super("Secret Passage", "", game.getItem("torch"));
		this.game = game;
	}
}
