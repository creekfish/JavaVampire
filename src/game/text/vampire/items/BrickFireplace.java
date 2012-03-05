package game.text.vampire.items;

import game.text.ActionGeneric;
import game.text.Actor;
import game.text.Container;
import game.text.Item;
import game.text.ItemGeneric;
import game.text.Place;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGame;
import game.text.exceptions.ActionException;

public class BrickFireplace extends ItemGeneric {
	private TextGame game;
	
	public BrickFireplace(TextGame _game) {
		super("Brick Fireplace", "");
		this.game = _game;
		this.addAction(new ActionGeneric("hit") {
				@Override
				public Result execute(Actor actor) {
					if (((Player) actor).getLocation().hasOne("brick fireplace")) {
						game.output("      -- With what? ");
						String input = game.input();
						if (game.matchItem(input) == game.getItem("sledge hammer") && 
								((Player) actor).hasOne("sledge hammer") && 
								game.getItem("fireplace").getData("broken") == null) {
							game.getItem("fireplace").changeName("Broken Fireplace");  // the fireplace gets a new "look"
							game.getPlace("_fireplace").changeName("Broken Fireplace");  // the fireplace place name must be changed
							game.getItem("fireplace").setData("broken", new Boolean(true));  // mark the fireplace as broken
							game.getPlace("_fireplace").setConnection(game.getDirection("north"), game.getPlace("secret passage")); // new hole leads to secret passage!
							return new ResultGeneric(true, ((Player) actor).getLocation().getAction("look").execute(actor).getMessage());
						}
						return new ResultGeneric(false, "Nothing happened\n");
					} else {
						return new ResultGeneric(false, "I don't see any "+game.getItem("fireplace").getName());
					}
				}								
			});
		this.addAction(new ActionGeneric("go") {
				@Override
				public Result execute(Actor actor) {
					if (((Player) actor).getLocation().hasOne("brick fireplace")) {
						if (game.getItem("_fire").getData("out") != null) {
							// go fireplace leads to broken fireplace place...
							((Player) actor).setLocation(game.getPlace("_fireplace"));
							return new ResultGeneric(true, ((Player) actor).getLocation().getAction("look").execute(actor).getMessage());
						} else {
							// game over
							game.end(false, " You have Burned to Death");
							return null;
						}
					} else {
						return new ResultGeneric(false, "You can't go there");
					}
				}								
			});
	}
	
	@Override
	public void move(Container<Item> destination) throws ActionException {
		// disable player moving this item
		if (destination instanceof Place) {  
			super.move(destination);
		} else {
			throw new ActionException("You can't get it");
		}
	}	
	
}
