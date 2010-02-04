package game.text.vampire.items;

import game.text.ActionGeneric;
import game.text.Actor;
import game.text.Container;
import game.text.Item;
import game.text.ItemGeneric;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.TextGame;
import game.text.exceptions.ActionException;

public class CoilOfRope extends ItemGeneric {
	private TextGame game;
	
	public CoilOfRope(TextGame _game) {
		super("Coil of Rope", "");
		this.game = _game;
		this.addAction(new ActionGeneric("tie") {
				@Override
				public Result execute(Actor actor) {
					if (CoilOfRope.this.getData("tied") == null) {  // if rope is not already tied
						// tie the rope to the parapets if we're in the tower
						game.output("      -- To what? ");
						String input = game.input();
						// NOTE: it's a flaw in the original game that you can tie the rope to the parapets from anywhere!
						if (((Player) actor).getLocation() == game.getPlace("tower")) {
							if (game.matchItem("_"+input) == game.getItem("_parapets")) {
								game.getItem("_parapets").changeName("Rope tied to the Parapet");  // the parapets get a new "look"
								try {
									((Player) actor).drop(CoilOfRope.this);  // coil of rope is dropped to climb
									CoilOfRope.this.setData("tied", new Boolean(true));  // mark the rope as tied
									CoilOfRope.this.changeName(null);  // item will be skipped in item display lists, not visible in place
									return new ResultGeneric(true, "Rope tied to the Parapet!\n");
								} catch (ActionException e) {
									return new ResultGeneric(false, e.getMessage());												
								}
							}
						}
						return new ResultGeneric(false, "You can't do that\n");
					} else {
						return new ResultGeneric(false, "You can't do that\n");									
					}								
				}					
			});
		this.addAction(new ActionGeneric("climb") {
				@Override
				public Result execute(Actor actor) {
					if (CoilOfRope.this.getData("tied") != null) {  // if rope is tied to the parapet
						// rope leads to the lower tower...
						((Player) actor).setLocation(game.getPlace("lower tower"));
						return new ResultGeneric(true, "Climbed down rope\n\n"+((Player) actor).getLocation().getAction("look").execute(actor).getMessage());
					} else {
						return new ResultGeneric(false, "You can't do that\n");									
					}								
				}					
			});
	}

	@Override
	public void move(Container<Item> destination) throws ActionException {
		if (this.getData("tied") == null) {  // if the rope is not yet tied
			super.move(destination);
		} else {
			throw new ActionException("You can't get it");
		}
	}	

	@Override
	public String look() { 
		if (this.getData("tied") == null) {   // if the rope is not yet tied
			return this.getName();
		} else {
			return null;  // make the item invisible to the player
		}
	}	
}
