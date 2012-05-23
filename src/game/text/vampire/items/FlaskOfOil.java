package game.text.vampire.items;

import game.text.ItemGeneric;
import game.text.TextGame;

public class FlaskOfOil extends ItemGeneric {
	private TextGame game;
	
	public FlaskOfOil(TextGame _game) {
		super("Flask of Oil", "");
		this.game = _game;
	}
}
