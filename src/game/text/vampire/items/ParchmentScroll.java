package game.text.vampire.items;

import game.text.Game;
import game.text.ItemGeneric;

public class ParchmentScroll extends ItemGeneric {
	private Game game;
	
	public ParchmentScroll(Game game) {
		super("Parchment Scroll", "");
		this.game = game;
	}

	@Override
	public String getDescription() {
		if (this.getLocation() == game.getPlayer()) {
			return "The Scroll reads: 'Not all exits are obvious.'";
		} else {
			if (this.getLocation() == game.getPlayer().getLocation()) {
				return "You don't have it";
			} else {
				return "I don't see any "+this.getName();				
			}
		}
	}			

	@Override
	public String look() {
		return this.getDescription();
	}	
	
}
