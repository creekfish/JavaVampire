package game.text.vampire.items;

import game.text.ItemGeneric;
import game.text.TextGame;

public class Oar extends ItemGeneric {
	private TextGame game;
	
	public Oar(TextGame _game) {
		super("Oar", "");
		this.game = _game;
	}
}
