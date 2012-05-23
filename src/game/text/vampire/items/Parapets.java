package game.text.vampire.items;

import game.text.ActionGeneric;
import game.text.Actor;
import game.text.ItemFixedInPlace;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGame;

public class Parapets extends ItemFixedInPlace {
	private TextGame game;
	
	public Parapets(TextGame _game) {
		super("Parapets", "");
		this.game = _game;
		this.addAction(new ActionGeneric("go") {
			@Override
			public Result execute(Actor actor) {
				if (game.getItem("coil of rope").getData("tied") != null) {  // if rope is tied to the parapet
					// player can climb rope to lower tower
					((Player) actor).setLocation(game.getPlace("lower tower"));
					return new ResultGeneric(true, "Climbed down rope\n\n"+((Player) actor).getLocation().getAction("look").execute(actor).getMessage());
				} else {
					// trying to go to parapets without rope tied is a very bad thing...
					game.end(false, " You fell and Died\n");
					return null;
				}
			}					
		});
	}	
}
