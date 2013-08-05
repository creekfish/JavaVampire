package game.text.vampire.places;

import game.text.ActionInitial;
import game.text.Actor;
import game.text.PlaceDarkWithoutItem;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGame;
import game.text.exceptions.ActionException;

public class UndergroundLakeChamber extends PlaceDarkWithoutItem {	
	private TextGame game;
	
	public UndergroundLakeChamber(TextGame _game)  throws ActionException {
		super("Underground Lake Chamber", "", _game.getItem("torch"));
		this.game = _game;
		this.moveItemHere(this.game.getItem("_boat"));
		this.addAction(new ActionInitial("swim") {
			@Override
			public Result execute(Actor actor) {
				if (game.getPlayer().getLocation() == game.getPlace("lake")) {
					// game over
					game.end(false);
					return new ResultGeneric(false, " You have Drowned in the ice cold water");
				} else {
					return new ResultGeneric(false, "I don't know how to do that");
				}								
			}					
		}
	);
		
	}
}
