package game.text.vampire.items;

import game.text.ActionContinued;
import game.text.ActionInitial;
import game.text.Actor;
import game.text.ItemGeneric;
import game.text.Player;
import game.text.Result;
import game.text.ResultGeneric;
import game.text.ResultPartial;
import game.text.TextGame;

public class Crate extends ItemGeneric {
	private TextGame game;
	
	public Crate(TextGame _game) {
		super("Crate", "");
		this.game = _game;
		this.addAction(new ActionInitial("hit") {
			@Override
			public Result execute(Actor actor) {
				if (((Player) actor).getLocation().hasOne("crate")) {
					return new ResultPartial(true, "      -- With what? ", (ActionContinued) Crate.this.getAction("hit_with"));
				} else {
					return new ResultGeneric(false, "I don't see any Crate");
				}
			}								
		});
		this.addAction(new ActionContinued("hit_with") {
			@Override
			public Result execute(Actor actor, String feedback) {
				if (((Player) actor).getLocation().hasOne("crate")) {
					if (game.matchItem(feedback) == game.getItem("axe") && 
							((Player) actor).hasOne("axe") && 
							Crate.this.getData("stakes") == null) {
						// transform the crate into wooden stakes
						Crate.this.changeName("Wooden Stakes");  
						// change crate to stakes in the game items map
						game.addItem("wooden stakes", Crate.this);  
						game.addItem("stakes", Crate.this);    // alias for wooden stakes
						game.removeItem("crate"); 
						// change crate in room to stakes
						((Player) actor).getLocation().addOne("wooden stakes", Crate.this); 
						((Player) actor).getLocation().removeOne("crate");
						// mark crate as being wooden stakes now
						Crate.this.setData("stakes", new Boolean(true));  
						return new ResultGeneric(true, ((Player) actor).getLocation().look());
					}
					return new ResultGeneric(false, "Nothing happened\n");
				} else {
					return new ResultGeneric(false, "I don't see any Crate");
				}
			}								
		});
	}
}
