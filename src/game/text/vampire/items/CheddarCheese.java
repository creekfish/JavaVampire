package game.text.vampire.items;

import game.text.ItemGeneric;
import game.text.TextGame;

public class CheddarCheese extends ItemGeneric {
	private TextGame game;
	
	public CheddarCheese(TextGame _game) {
		super("Cheddar Cheese", "");
		this.game = _game;
	}
}
