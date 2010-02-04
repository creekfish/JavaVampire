package game.text.vampire.items;

import game.text.Game;
import game.text.ItemFixedInPlace;

public class Sign extends ItemFixedInPlace {
	private Game game;
	
	public Sign(Game game) {
		super("Sign", "'The Vampire Wakes at Midnight!'");
		this.game = game;
	}
	
	@Override
	public String look() {
		return game.getItem("sign").getDescription();
	}	
}
