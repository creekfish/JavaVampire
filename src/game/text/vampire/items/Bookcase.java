package game.text.vampire.items;

import game.text.ActionGeneric;
import game.text.Actor;
import game.text.ItemFixedInPlace;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGame;

public class Bookcase extends ItemFixedInPlace {
	private TextGame game;
	
	public Bookcase(TextGame _game) {
		super("Bookcase", "");
		this.game = _game;
		this.addAction(new ActionGeneric("push") {
				@Override
				public Result execute(Actor actor) {
					// open the doorway down to hidden corridor
					game.getPlace("library").connect(game.getPlace("hidden corridor"), game.getDirection("down"));
					return new ResultGeneric(true, "Aha! - You have revealed a Doorway\n\n" + 
							game.getPlace("library").look());
				}						
			});
	}	
}
