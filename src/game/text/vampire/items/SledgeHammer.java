package game.text.vampire.items;

import game.text.ItemGeneric;
import game.text.TextGame;

public class SledgeHammer extends ItemGeneric {
	private TextGame game;
	
	public SledgeHammer(TextGame _game) {
		super("Sledge Hammer", "");
		this.game = _game;
		game.addItem("hammer", this);  // "hammer" = alias for "sledge hammer"
	}
}
