package game.text.vampire.items;

import game.text.ItemGeneric;
import game.text.TextGame;

public class Wine extends ItemGeneric {
	private TextGame game;
	
	public Wine(TextGame _game) {
		super("Wine", "");
		this.game = _game;
	}
}
