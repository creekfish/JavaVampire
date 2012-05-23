package game.text.vampire.items;

import game.text.ItemGeneric;
import game.text.TextGame;

public class Bucket extends ItemGeneric {
	private TextGame game;
	
	public Bucket(TextGame _game) {
		super("Bucket", "");
		this.game = _game;
	}
}
