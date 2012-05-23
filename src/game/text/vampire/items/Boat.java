package game.text.vampire.items;

import game.text.ItemFixedInPlace;
import game.text.TextGame;

public class Boat extends ItemFixedInPlace {
	private TextGame game;
	
	public Boat(TextGame _game) {
		super("Boat", "");
		this.game = _game;
	}	
}
