package game.text.vampire.items;

import game.text.ItemGeneric;
import game.text.TextGame;

public class Axe extends ItemGeneric {
	private TextGame game;
	
	public Axe(TextGame _game) {
		super("Axe", "");
		this.game = _game;
	}
}
