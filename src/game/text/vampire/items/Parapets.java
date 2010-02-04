package game.text.vampire.items;

import game.text.ItemFixedInPlace;
import game.text.TextGame;

public class Parapets extends ItemFixedInPlace {
	private TextGame game;
	
	public Parapets(TextGame _game) {
		super("Parapets", "");
		this.game = _game;
	}
}
