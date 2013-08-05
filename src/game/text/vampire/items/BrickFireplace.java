package game.text.vampire.items;

import game.text.ActionContinued;
import game.text.ActionInitial;
import game.text.Actor;
import game.text.ItemFixedInPlace;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.ResultPartial;
import game.text.TextGame;

public class BrickFireplace extends ItemFixedInPlace {
	private TextGame game;
	
	public BrickFireplace(TextGame _game) {
		super("Brick Fireplace", "");
		this.game = _game;
		this.addAction(new ActionInitial("hit") {
				@Override
				public Result execute(Actor actor) {
					if (((Player) actor).getLocation().hasOne("brick fireplace")) {
						return new ResultPartial(true, "      -- With what? ", (ActionContinued) BrickFireplace.this.getAction("hit_with"));
					} else {
						return new ResultGeneric(false, "I don't see any "+game.getItem("fireplace").getName());
					}
				}								
			});
		this.addAction(new ActionContinued("hit_with") {
			@Override
			public Result execute(Actor actor, String feedback) {
				if (((Player) actor).getLocation().hasOne("brick fireplace")) {
					if (game.matchItem(feedback) == game.getItem("sledge hammer") && 
							((Player) actor).hasOne("sledge hammer") && 
							game.getItem("fireplace").getData("broken") == null) {
						game.getItem("fireplace").changeName("Broken Fireplace");  // the fireplace gets a new "look"
						game.getPlace("_fireplace").changeName("Broken Fireplace");  // the fireplace place name must be changed
						game.getItem("fireplace").setData("broken", new Boolean(true));  // mark the fireplace as broken
						game.getPlace("_fireplace").setConnection(game.getDirection("north"), game.getPlace("secret passage")); // new hole leads to secret passage!
						return new ResultGeneric(true, ((Player) actor).getLocation().getInitialAction("look").execute(actor).getMessage());
					}
					return new ResultGeneric(false, "Nothing happened\n");
				} else {
					return new ResultGeneric(false, "I don't see any "+game.getItem("fireplace").getName());
				}
			}								
		});
		this.addAction(new ActionInitial("go") {
				@Override
				public Result execute(Actor actor) {
					if (((Player) actor).getLocation().hasOne("brick fireplace")) {
						if (game.getItem("_fire").getData("out") != null) {
							// go fireplace leads to broken fireplace place...
							((Player) actor).setLocation(game.getPlace("_fireplace"));
							return new ResultGeneric(true, ((Player) actor).getLocation().getInitialAction("look").execute(actor).getMessage());
						} else {
							// game over
							game.end(false);
							return new ResultGeneric(false, " You have Burned to Death");
						}
					} else {
						return new ResultGeneric(false, "You can't go there");
					}
				}								
			});
	}	
}
