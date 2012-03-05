package game.text.vampire.items;

import game.text.Game;
import game.text.ItemGeneric;

public class Timepiece extends ItemGeneric {
	private Game game;
	
	public Timepiece(Game game) {
		super("Timepiece", "");
		this.game = game;
	}

	@Override
	public String getDescription() {
		if (this.getLocation() == game.getPlayer()) {
			return "The time is " + game.getTime();
		} else {
			return "You don't have it";
		}
	}

	@Override
	public String look() {
		return game.getItem("timepiece").getDescription();
	}	
	
}
