package game.text.vampire.items;

import game.text.ItemFixedInPlace;
import game.text.TextGame;

public class FireInTheFireplace extends ItemFixedInPlace {
	private TextGame game;
	
	public FireInTheFireplace(TextGame _game) {
		super("Fire in the Fireplace", "");
		this.game = _game;
	}
}
