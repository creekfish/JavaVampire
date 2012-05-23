package game.text.vampire.items;

import game.text.ItemGeneric;
import game.text.TextGame;

public class Torch extends ItemGeneric {
	private TextGame game;
	
	public Torch(TextGame _game) {
		super("Torch", "");
		this.game = _game;
	}
}
